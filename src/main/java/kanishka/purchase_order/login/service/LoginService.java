package kanishka.purchase_order.login.service;

import kanishka.purchase_order.login.dto.LoginDTO;
import kanishka.purchase_order.login.dto.UserProfileDTO;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface LoginService {
    ResponseEntity<Map<String, Object>> login(LoginDTO loginDTO);

    ResponseEntity<Map<String, String>> register(LoginDTO loginDTO);

    ResponseEntity<UserProfileDTO> getCurrentUser(String username);

    ResponseEntity<Map<String, String>> logout();
}
