package yaroslav.sharipov.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import yaroslav.sharipov.model.User;
import yaroslav.sharipov.service.interfaces.UserService;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/user/profile")
    public ModelAndView profile(@AuthenticationPrincipal User user) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/user/profile");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @GetMapping(value = "/user/getProfile/{id}")
    public ResponseEntity<List<User>> editProfileJSON(@AuthenticationPrincipal User user) {
        ArrayList<User> arr = new ArrayList<>();
        arr.add(userService.getUserByUsername(user.getUsername()));
        return ResponseEntity.ok(arr);
    }

    @PostMapping(value = "/user/editProfileFine")
    public ResponseEntity<Void> editProfileFine(@ModelAttribute("username") String username,
                                                @ModelAttribute("password") String password,
                                                @AuthenticationPrincipal User user) {
        user.setUsername(username);
        user.setPassword(password);

        if (userService.userIsExist(user)) {
            if (!userService.getUserByUsername(username).getId().equals(user.getId())) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        userService.editUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/user/deleteProfile/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
