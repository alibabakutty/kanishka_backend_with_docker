package kanishka.purchase_order.login.mapper;

import kanishka.purchase_order.login.dto.LoginDTO;
import kanishka.purchase_order.login.dto.UserProfileDTO;
import kanishka.purchase_order.login.module.LoginModule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LoginMapper {

    // map from dto to entity(for response)
    // converts the database user to a safe profile for the frontend
    UserProfileDTO toProfileDTO(LoginModule user);

    // converts the database user to a login response if needed
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "lastLoginAt", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "role", defaultValue = "USER")
    LoginModule toEntity(LoginDTO loginDTO);
}
