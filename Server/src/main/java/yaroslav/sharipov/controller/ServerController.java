package yaroslav.sharipov.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yaroslav.sharipov.model.UserDAO;
import yaroslav.sharipov.model.role.RoleDAO;
import yaroslav.sharipov.service.interfaces.ServerService;

import java.util.List;

@RestController("/server")
public class ServerController {

    private ServerService serverService;

    @Autowired
    public void setServerService(ServerService serverService) {
        this.serverService = serverService;
    }

    @GetMapping("/getUsers")
    public ResponseEntity<List<UserDAO>> getUsers() {
        return ResponseEntity.ok(serverService.getAllUsers());
    }

    @GetMapping("/getUser/id={id}")
    public ResponseEntity<UserDAO> getUserById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(serverService.getUserById(id));
    }

    @GetMapping("/getUser/name={username}")
    public ResponseEntity<UserDAO> getUserByUsername(@PathVariable("username") String username) {
        UserDAO userDAO = serverService.getUserByUsername(username);
        return ResponseEntity.ok(userDAO);
    }

    @PostMapping("/getUser/pass")
    public ResponseEntity<UserDAO> getUserByUsernameAndPassword(@ModelAttribute String username, @ModelAttribute String password) {
        return ResponseEntity.ok(serverService.getUserByUsernameAndPassword(username, password));
    }

    @GetMapping("/admin/getAllRoles")
    public ResponseEntity<List<RoleDAO>> getAllRoles() {
        return ResponseEntity.ok(serverService.getAllRoles());
    }

    @PostMapping("/admin/add")
    public ResponseEntity<UserDAO> addUser(@RequestBody UserDAO user) {

        if (!serverService.userIsExist(user)) {
            serverService.addUser(user);
            return ResponseEntity.ok(serverService.getUserByUsername(user.getUsername()));
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/edit")
    public ResponseEntity<Void> editUser(@RequestBody UserDAO userDAO) {

        if (serverService.userIsExist(userDAO)) {
            if (!serverService.getUserByUsername(userDAO.getUsername()).getId().equals(userDAO.getId())) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }

        serverService.editUser(userDAO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        serverService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
