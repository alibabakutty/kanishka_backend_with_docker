package kanishka.purchase_order.login.controller;

import jakarta.validation.Valid;
import kanishka.purchase_order.login.dto.LoginDTO;
import kanishka.purchase_order.login.dto.UserProfileDTO;
import kanishka.purchase_order.login.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final LoginService loginService;


    @PostMapping("/login")
    public ResponseEntity<?> loginController(@Valid @RequestBody LoginDTO loginDTO){
        // No try-catch needed! GlobalExceptionHandler handles any failures.
        return loginService.login(loginDTO);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerController(@Valid @RequestBody LoginDTO loginDTO){
        return loginService.register(loginDTO);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutController(){
        return loginService.logout();
    }

    @GetMapping("/me")
    public ResponseEntity<UserProfileDTO> getMyProfile(java.security.Principal principal) {
        if (principal == null) {
            // Return 401 instead of crashing with 500
            return ResponseEntity.status(org.springframework.http.HttpStatus.UNAUTHORIZED).build();
        }

        // This calls your service method:
        return loginService.getCurrentUser(principal.getName());
    }
}
