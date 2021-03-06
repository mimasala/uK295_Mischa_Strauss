package ch.noseryoung.sbdemo01.user;

import ch.noseryoung.sbdemo01.user.userRole.UserRole;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Entity
@Getter@Setter
public class User implements UserDetails {
    @Id
    @SequenceGenerator(
            name="user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long userId;
    private String username;
    private String lastName;
    private String email;
    private String password;
    @ManyToOne
    @JoinColumn(name = "user_role_id")
    private UserRole userRole;
    private Boolean locked = false;
    private Boolean authorized = false;

    public User(String username, String lastName, String email, String password, UserRole userRole) {
        this.username = username;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.userRole = userRole;
    }


    public User() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userRole.getName());
        return Collections.singletonList(authority);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return authorized;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return authorized;
    }
}
