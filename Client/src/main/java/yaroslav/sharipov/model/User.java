package yaroslav.sharipov.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import yaroslav.sharipov.model.Role.Role;

import java.util.Collection;
import java.util.Set;

public class User implements UserDetails {

    private Long id;

    private String username;

    private int age;

    private String email;

    private String password;

    private String passwordConfirm;

    private Set<Role> rolesDAO;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRolesDAO();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordComfirm) {
        this.passwordConfirm = passwordComfirm;
    }

    public Set<Role> getRolesDAO() {
        return rolesDAO;
    }

    public void setRolesDAO(Set<Role> rolesDAO) {
        this.rolesDAO = rolesDAO;
    }
}