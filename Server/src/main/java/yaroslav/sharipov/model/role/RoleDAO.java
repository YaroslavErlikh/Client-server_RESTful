package yaroslav.sharipov.model.role;

import yaroslav.sharipov.model.UserDAO;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "tabl_roles")
public class RoleDAO {

    @Id
    private Long id;

    private String roleName;

    @Transient
    @ManyToMany(mappedBy = "roles")
    private Set<UserDAO> users;

    public RoleDAO() {
    }

    public RoleDAO(Long id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Set<UserDAO> getUsers() {
        return users;
    }

    public void setUsers(Set<UserDAO> users) {
        this.users = users;
    }
}
