package gr.codehub.toDoAppWithLogin.repository;

import gr.codehub.toDoAppWithLogin.model.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
