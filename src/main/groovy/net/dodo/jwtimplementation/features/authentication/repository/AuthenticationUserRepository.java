package net.dodo.jwtimplementation.features.authentication.repository;

import java.util.Optional;
import net.dodo.jwtimplementation.features.authentication.model.AuthenticationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenticationUserRepository extends JpaRepository<AuthenticationUser, Long> {

  Optional<AuthenticationUser> findByEmail(String email);
}
