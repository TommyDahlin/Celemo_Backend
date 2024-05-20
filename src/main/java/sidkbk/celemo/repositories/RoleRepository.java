package sidkbk.celemo.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import sidkbk.celemo.models.ERole;
import sidkbk.celemo.models.Role;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(ERole name);


}
