package yaroslav.sharipov.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import yaroslav.sharipov.model.User;
import yaroslav.sharipov.service.interfaces.UserService;

@Controller
public class RegistrationController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @PostMapping("/registration")
    public ModelAndView registerNewUser(@ModelAttribute("user") User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
            modelAndView.addObject("message", "Ошибка заполнения формы");
            return modelAndView;
        }

        if (!user.getPassword().equals(user.getPasswordConfirm())) {
            modelAndView.setViewName("registration");
            modelAndView.addObject("message", "Пароли не совпадают");
            return modelAndView;
        }

        if (!userService.addUser(user)) {
            modelAndView.setViewName("registration");
            modelAndView.addObject("message", "Пользователь с таким именем уже существует");
            return modelAndView;
        }

        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }
}
