package gr.codehub.toDoAppWithLogin.service;

import gr.codehub.toDoAppWithLogin.base.AbstractLogEntity;
import gr.codehub.toDoAppWithLogin.model.security.Role;
import gr.codehub.toDoAppWithLogin.model.security.User;
import gr.codehub.toDoAppWithLogin.repository.RoleRepository;
import gr.codehub.toDoAppWithLogin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InitiationService extends AbstractLogEntity {

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public void initiateDatabase() {
        logger.info("Checking if database has been initialiased already");
        if (!userRepository.findFirstByUsername(ADMIN_USERNAME).isPresent()) {
            logger.info("Database is empty, initiating setup.");
            User user = new User();
            user.setUsername(ADMIN_USERNAME);
            //encrypt password
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(4);
            String encodedPassword = bCryptPasswordEncoder.encode(ADMIN_PASSWORD);
            user.setPassword(encodedPassword);
            //create role for user
            List<Role> allRoles = new ArrayList<>();
            Role adminRole = new Role();
            adminRole.setId((long) 1);
            adminRole.setRole("ADMIN");
            allRoles.add(adminRole);
            Role userRole = new Role();
            userRole.setId((long) 2);
            userRole.setRole("USER");
            allRoles.add(userRole);
            roleRepository.saveAll(allRoles);
            user.setRoles(allRoles);
            userRepository.save(user);
        }
        logger.info("Database successfully initialised.");
    }
}
