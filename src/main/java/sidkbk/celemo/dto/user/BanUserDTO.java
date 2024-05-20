package sidkbk.celemo.dto.user;

import jakarta.validation.constraints.NotBlank;
import sidkbk.celemo.models.ERole;

import java.util.HashSet;
import java.util.Set;

public class BanUserDTO {
    @NotBlank
    private String userId;

    public Set<String> getUsersRoles() {
        return usersRoles;
    }

    private Set<String> usersRoles = new HashSet<>();
    public String getUserId() {
        return userId;
    }

}
