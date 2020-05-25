package yaroslav.sharipov.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import yaroslav.sharipov.model.Role.Role;
import yaroslav.sharipov.model.User;
import yaroslav.sharipov.service.interfaces.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private RestTemplate restTemplate;

    @Value(value = "${application.server.url}")
    private String serverUrl;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getUserByUsername(username);
    }

    @Override
    public List<User> getAllUsers() {
        return restTemplate.getForObject(serverUrl + "/getUsers", List.class);
    }

    @Override
    public User getUserById(Long id) {
        return restTemplate.getForObject(serverUrl + "/getUser/id=" + id, User.class);
    }

    @Override
    public User getUserByUsername(String username) {
        User user = restTemplate.getForObject(serverUrl + "/getUser/name=" + username, User.class);
        return user;
    }

    @Override
    public User getUserByUsernameAndPassword(String username, String password) {
        Map<String, String> strings = new HashMap<>();
        strings.put("username", username);
        strings.put("password", password);
        return restTemplate.postForObject(serverUrl + "/getUser/pass", strings, User.class);
    }

    @Override
    public boolean addUser(User user) {
        User userNew = restTemplate.postForObject(serverUrl + "/admin/add", user, User.class);
        return userNew != null;
    }

    @Override
    public void editUser(User user) {
        restTemplate.postForObject(serverUrl + "/edit", user, User.class);
    }

    @Override
    public void deleteUser(Long id) {
        restTemplate.delete(serverUrl + "/delete/" + id);
    }

    @Override
    public List<Role> getAllRoles() {
        List<Role> list = restTemplate.getForObject(serverUrl + "/admin/getAllRoles", List.class);
        return list;
    }

    @Override
    public boolean userIsExist(User user) {
        return getUserByUsername(user.getUsername()) != null;
    }
}
