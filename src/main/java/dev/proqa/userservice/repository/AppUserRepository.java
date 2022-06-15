package dev.proqa.userservice.repository;

import dev.proqa.userservice.domain.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    AppUser findByUsername(String username);
}
