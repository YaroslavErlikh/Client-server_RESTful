package yaroslav.sharipov.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import yaroslav.sharipov.db.dao.RoleDAORepository;
import yaroslav.sharipov.db.dao.UserDAORepository;
import yaroslav.sharipov.model.UserDAO;
import yaroslav.sharipov.model.role.RoleDAO;
import yaroslav.sharipov.service.interfaces.ServerService;

import java.util.Collections;
import java.util.List;

@Service
public class ServerServiceImpl implements ServerService {

    private UserDAORepository userRepository;

    private RoleDAORepository roleRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public void setUserRepository(UserDAORepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setRoleRepository(RoleDAORepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setbCryptPasswordEncoder(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public List<UserDAO> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserDAO getUserById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public UserDAO getUserByUsername(String username) {
        UserDAO userDAO = userRepository.findByUsername(username);
        return userDAO;
    }

    @Override
    public UserDAO getUserByUsernameAndPassword(String username, String password) {
        UserDAO user = userRepository.findByUsername(username);
        if (bCryptPasswordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    @Override
    public boolean addUser(UserDAO user) {
        if (userIsExist(user)) {
            return false;
        }

        user.setRolesDAO(Collections.singleton(new RoleDAO(1L, "ROLE_USER")));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    @Override
    public void editUser(UserDAO user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.saveAndFlush(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.delete(getUserById(id));
    }

    @Override
    public List<RoleDAO> getAllRoles() {
        List<RoleDAO> list = roleRepository.findAll();
        return list;
    }

    @Override
    public boolean userIsExist(UserDAO user) {

        ExampleMatcher exampleMatcher =
                ExampleMatcher.matching()
                        .withIgnorePaths("id")
                        .withIgnorePaths("password")
                        .withIgnorePaths("roles");

        return userRepository.exists(Example.of(user, exampleMatcher));
    }
}
