package gr.codehub.toDoAppWithLogin.repository;

import gr.codehub.toDoAppWithLogin.model.security.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findFirstByUsername(String username);

    /**
     * custom query made to fetch all the users but the admin from the repository
     *
     * @param roleName: role of the required users, could be something like: "fetch me all the users with role ADMIN"
     *                  for example
     * @return : a set of all users fulfilling the requirements specified above
     */
    @Query("SELECT user FROM User user JOIN user.roles role WHERE role.role=:roleName")
    Set<User> findByUserRole(@Param("roleName") String roleName);
}