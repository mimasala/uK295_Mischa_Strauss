package ch.noseryoung.sbdemo01.user.userRole;

import ch.noseryoung.sbdemo01.user.authority.Authority;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity@Getter@Setter@AllArgsConstructor@NoArgsConstructor
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false)
    private String name;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            joinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "authority_id", referencedColumnName = "id"))
    private List<Authority> authorities;

    public String toString() {
        return getName();
    }


}