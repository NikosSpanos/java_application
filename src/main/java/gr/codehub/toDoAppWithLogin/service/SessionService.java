package gr.codehub.toDoAppWithLogin.service;

import gr.codehub.toDoAppWithLogin.base.AbstractLogEntity;
import gr.codehub.toDoAppWithLogin.model.security.SessionUser;
import gr.codehub.toDoAppWithLogin.model.security.User;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


/**
 * service that is associated with the management of the session
 */
@Service
public class SessionService extends AbstractLogEntity {

    /**
     * this method takes as input a user and a session, and sets user attributes to the existing session
     *
     * @param user:    the user that is associated with the current session
     * @param session: the session that the attributes will be set on
     */
    public void setSessionAttributes(SessionUser user, HttpSession session) {
        session.setAttribute("userId", user.getId());
        session.setAttribute("username", user.getUsername());
        session.setAttribute("role", this.findRole(user));
    }

    /**
     * this method instantly finds the repository id of an user
     *
     * @param session: the current session with a user
     * @return : returns a User that only contains the Id saved in an attribute of the session
     */
    public User getUserWithSessionId(HttpSession session) {
        return User.builder().id((long) session.getAttribute("userId")).build();
    }

    /**
     * this method takes as input a user and returns a role in the form of a String
     *
     * @param user: the user that is associated with the current session
     * @return : returns the highest privilege role that is associated with the user
     */
    private String findRole(SessionUser user) {
        logger.info("Finding user role.");
        //roles: user OR admin
        List<String> authorityList = new ArrayList<>(user.getAuthorities().size());
        //for each authority, get the String authority (role) and add it to the String list
        user.getAuthorities().forEach(authority -> authorityList.add(authority.getAuthority()));
        logger.info("User has the following roles: {}", authorityList);
        //since the role-user association is implemented as a ManyToMany relationship,
        // we need to return the highest rank
        if (authorityList.contains("ADMIN"))
            return "Admin";
        else
            return "User";
    }

    /**
     * this method checks if the user has logged in during the session or not
     *
     * @param authentication: received from current session of user
     * @return returns true if user is already logged in, false if not
     */
    public boolean isUserLoggedIn(Authentication authentication) {
        return !(authentication instanceof AnonymousAuthenticationToken);
    }


}