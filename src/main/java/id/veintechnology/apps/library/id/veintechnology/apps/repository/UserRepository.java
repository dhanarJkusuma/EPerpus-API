package id.veintechnology.apps.library.id.veintechnology.apps.repository;

import id.veintechnology.apps.library.id.veintechnology.apps.dao.AuthenticationUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AuthenticationUser, Long>{
    AuthenticationUser findOneByUsername(String username);
}
