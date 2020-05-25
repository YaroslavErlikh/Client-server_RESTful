package yaroslav.sharipov.service.interfaces;

import yaroslav.sharipov.model.Role.Role;
import yaroslav.sharipov.model.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User getUserById(Long id);

    User getUserByUsername(String username);

    User getUserByUsernameAndPassword(String username, String password);

    boolean addUser(User user);

    void editUser(User user);

    void deleteUser(Long id);

    List<Role> getAllRoles();

    boolean userIsExist(User user);
}
