package yaroslav.sharipov.model;

import yaroslav.sharipov.model.role.RoleDAO;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "tabl_users")
public class UserDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    @ManyToMany
    private Set<RoleDAO> rolesDAO;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Set<RoleDAO> getRolesDAO() {
        return rolesDAO;
    }

    public void setRolesDAO(Set<RoleDAO> rolesDAO) {
        this.rolesDAO = rolesDAO;
    }
}
