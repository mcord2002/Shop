package backend.repository;

import backend.model.AdminUserModel;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminUserRepository extends JpaRepository<AdminUserModel, Long> {
    Optional<AdminUserModel> findByEmail (String email);
}
