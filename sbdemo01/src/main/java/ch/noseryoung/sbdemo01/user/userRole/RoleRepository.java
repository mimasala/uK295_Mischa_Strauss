package ch.noseryoung.sbdemo01.user.userRole;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<UserRole, UUID> {

    UserRole findByName(String admin);
}
