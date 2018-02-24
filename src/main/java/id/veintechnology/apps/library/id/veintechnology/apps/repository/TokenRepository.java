package id.veintechnology.apps.library.id.veintechnology.apps.repository;

import id.veintechnology.apps.library.id.veintechnology.apps.dao.AuthenticationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<AuthenticationToken, Long>{
    AuthenticationToken findOneByToken(String token);
}
