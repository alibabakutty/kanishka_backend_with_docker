package kanishka.purchase_order.login.service.serviceImpl;

import kanishka.purchase_order.exception.duplicateUserException.DuplicateUserException;
import kanishka.purchase_order.exception.invalidCredentailsException.InvalidCredentialsException;
import kanishka.purchase_order.exception.userNotFoundException.UserNotFoundException;
import kanishka.purchase_order.login.dto.LoginDTO;
import kanishka.purchase_order.login.dto.UserProfileDTO;
import kanishka.purchase_order.login.mapper.LoginMapper;
import kanishka.purchase_order.login.module.LoginModule;
import kanishka.purchase_order.login.repository.LoginRepository;
import kanishka.purchase_order.login.service.LoginService;
import kanishka.purchase_order.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class LoginServiceImpl implements LoginService {

    private final JwtUtils jwtUtils;
    private final LoginRepository loginRepository;
    private final PasswordEncoder passwordEncoder;
    private final LoginMapper loginMapper;

    @Override
    public ResponseEntity<Map<String, Object>> login(LoginDTO loginDTO) {
        validateLoginInput(loginDTO);

        LoginModule user = loginRepository.findByUsername(loginDTO.getUsername())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid username "));
         if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid password");
        }

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(user.getUsername());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(Map.of(
                        "token", jwtCookie.getValue(),
                        "username", user.getUsername(),
                        "role", user.getRole(),
                        "message", "Login successful"
                ));
    }


    @Override
    public ResponseEntity<Map<String, String>> register(LoginDTO loginDTO) {
        validateLoginInput(loginDTO);

        if (loginRepository.existsByUsername(loginDTO.getUsername())) {
            throw new DuplicateUserException("Username '" + loginDTO.getUsername() + "' is already taken");
        }

        if (loginRepository.existsByEmail(loginDTO.getEmail())) {
            throw new DuplicateUserException("Email already registered");
        }

        // use mapper to create entity from dto
        LoginModule user = loginMapper.toEntity(loginDTO);

        // encode password before saving (security best practice)
        user.setPassword(passwordEncoder.encode(loginDTO.getPassword()));

        // default role if not provided
        if(user.getRole() == null) user.setRole("USER");

        loginRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message", "User registered successfully"));
    }

    @Override
    public ResponseEntity<UserProfileDTO> getCurrentUser(String username) {
        LoginModule user = loginRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + username));
        return ResponseEntity.ok(loginMapper.toProfileDTO(user));
    }


    @Override
    public ResponseEntity<Map<String, String>> logout() {
        ResponseCookie cleanCookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cleanCookie.toString())
                .body(Map.of("message", "Logout Successful"));
    }


    // private helpers
    private void validateLoginInput(LoginDTO dto) {
        if (dto == null) throw new IllegalArgumentException("Data cannot be null");
        if (isInvalid(dto.getUsername())) throw new IllegalArgumentException("Username is required");
        if (isInvalid(dto.getPassword())) throw new IllegalArgumentException("Password is required");
    }

    private void validateRegistrationInput(LoginDTO dto) {
        validateLoginInput(dto);
        if (isInvalid(dto.getEmail())) throw new IllegalArgumentException("Email is required");
        if (!dto.getEmail().contains("@")) throw new IllegalArgumentException("Invalid email format");
    }

    private boolean isInvalid(String str) {
        return str == null || str.isBlank();
    }
}
