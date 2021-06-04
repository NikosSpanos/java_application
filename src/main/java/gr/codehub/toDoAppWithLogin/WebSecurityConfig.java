package gr.codehub.toDoAppWithLogin;

import gr.codehub.toDoAppWithLogin.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Class that configures the way each user should be treated, depending on the URL they request
 */
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsServiceImpl;
    //Array instance that includes all the paths that need to be accessed by all users
    String[] resources = new String[]{
            "/include/**", "/css/**", "/icons/**", "/images/**", "/js/**", "/layer/**"
    };

    /**
     * configures how each user request should be treated, and manages the login of the system
     *
     * @param http: the http security instance that manages user requests (URLs)
     * @throws Exception: is thrown when a user for examples requests a non existent URL
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(resources).permitAll()
                .antMatchers("/login", "/error", "/actuator/**").permitAll()
                //.antMatchers("/**").permitAll() //remove this to make security work again !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                .antMatchers("/admin/*", "/**").access("hasAuthority('ADMIN')")
                .antMatchers("/*").access("hasAuthority('USER') or hasAuthority('ADMIN')")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .defaultSuccessUrl("/")
                .failureUrl("/login?error=true")
                .usernameParameter("username")
                .passwordParameter("password")
                .and()
                .logout().permitAll()
                //need to use this in order to perform GET action:
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .and()
                .httpBasic();

    }

    /**
     * This is the password encoder used for user authentication OR user creation with encoding strength set by
     * the developer
     *
     * @return the configured password encoder
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(4);
    }


    /**
     * Register the authentication service for the user and state the password encoder
     *
     * @param auth: the authentication manager builder used to configure user authentications
     * @throws Exception: throws an exception if any of the instances within the method also throw an exception
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // Setting service to find user in the database and setting password encoder
        auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder());
    }
}
