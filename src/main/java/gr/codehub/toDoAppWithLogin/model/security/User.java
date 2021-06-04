package gr.codehub.toDoAppWithLogin.model.security;

import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * User that is saved in the repository
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;

    @Column
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    //user owns the association, role does not own the association
    @JoinTable(
            name = "role_user",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;
}
