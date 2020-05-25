package yaroslav.sharipov.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yaroslav.sharipov.model.Role.Role;
import yaroslav.sharipov.model.User;
import yaroslav.sharipov.service.interfaces.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class AdminController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin/getUsers")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/admin/getUserEdit/{id}")
    public ResponseEntity<User> getUsers(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/admin/getAllRoles")
    public ResponseEntity<List<Role>> getAllRoles() {
        return ResponseEntity.ok(userService.getAllRoles());
    }

    @PostMapping("/admin/add")
    public ResponseEntity<Void> addUser(@ModelAttribute("user") User user) {

        if (!userService.userIsExist(user)) {
            userService.addUser(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/admin/edit")
    public ResponseEntity<Void> editUser(
            @ModelAttribute("id") Long id,
            @ModelAttribute("username") String username,
            @ModelAttribute("password") String password,
            @RequestParam("rolesDAO") String[] rolesDAO
    ) {
        User user = userService.getUserById(id);
        user.setUsername(username);
        user.setPassword(password);

        Set<Role> rolesNew = new HashSet<>();

        for (String role : rolesDAO) {
            if (role.equals("2")) {
                rolesNew.add(new Role(2L, "ROLE_ADMIN"));
            }
            if (role.equals("1")) {
                rolesNew.add(new Role(1L, "ROLE_USER"));
            }
        }

        user.setRolesDAO(rolesNew);

        if (userService.userIsExist(user)) {
            if (!userService.getUserByUsername(username).getId().equals(id)) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }

        userService.editUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
