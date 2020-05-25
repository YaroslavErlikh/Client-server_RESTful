package yaroslav.sharipov.service.interfaces;

import yaroslav.sharipov.model.UserDAO;
import yaroslav.sharipov.model.role.RoleDAO;

import java.util.List;

public interface ServerService {

    List<UserDAO> getAllUsers();

    UserDAO getUserById(Long id);

    UserDAO getUserByUsername(String username);

    UserDAO getUserByUsernameAndPassword(String username, String password);

    boolean addUser(UserDAO user);

    void editUser(UserDAO user);

    void deleteUser(Long id);

    List<RoleDAO> getAllRoles();

    boolean userIsExist(UserDAO user);
}
