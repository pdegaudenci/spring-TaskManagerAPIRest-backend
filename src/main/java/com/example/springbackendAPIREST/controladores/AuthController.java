package com.example.springbackendAPIREST.controladores;


import com.example.springbackendAPIREST.entidades.ROLES;
import com.example.springbackendAPIREST.entidades.User;
import com.example.springbackendAPIREST.respositorios.UsuarioRepository;
import com.example.springbackendAPIREST.respositorios.payload.JwtResponse;
import com.example.springbackendAPIREST.respositorios.payload.LoginRequest;
import com.example.springbackendAPIREST.respositorios.payload.MessageResponse;
import com.example.springbackendAPIREST.respositorios.payload.RegisterRequest;
import com.example.springbackendAPIREST.security.jwt.JwtTokenUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador para llevar a cabo la autenticación utilizando JWT
 *
 * Se utiliza AuthenticationManager para autenticar las credenciales que son el
 * usuario y password que llegan por POST en el cuerpo de la petición
 *
 * Si las credenciales son válidas se genera un token JWT como respuesta
 */
// @CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final UsuarioRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtTokenUtil jwtTokenUtil;

    public AuthController(AuthenticationManager authManager,
                          UsuarioRepository userRepository,
                          PasswordEncoder encoder,
                          JwtTokenUtil jwtTokenUtil){
        this.authManager = authManager;
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest loginRequest){

        System.out.println(loginRequest);
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenUtil.generateJwtToken(authentication);

        // UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<MessageResponse> register(@RequestBody RegisterRequest signUpRequest) {

        // VERIFICAR USUARIO
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username ya existe!"));
        }

        // VERIFICAR EMAIL
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Emailya existe!"));
        }

        // CREAR NUEVO USUARIO
        User user = new User(null,signUpRequest.getUsername()
               ,
                encoder.encode(signUpRequest.getPassword()), signUpRequest.getEmail(), ROLES.USER);

        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
