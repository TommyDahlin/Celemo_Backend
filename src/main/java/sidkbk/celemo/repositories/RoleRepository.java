package sidkbk.celemo.repositories;

import sidkbk.celemo.models.ERole;

import java.util.Optional;

public interface RoleRepository {
    Optional<ERole> findByName(ERole role);
}
