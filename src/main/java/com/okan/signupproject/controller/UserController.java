package com.okan.signupproject.controller;

import com.okan.signupproject.model.User;
import com.okan.signupproject.service.UserService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;


@Controller
public class UserController {

    @Autowired
    private UserService userService;

    Logger log = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value= {"/", "/login"}, method=RequestMethod.GET)
    public ModelAndView login() {
        log.debug("Entering login page");

        ModelAndView model = new ModelAndView();
        model.setViewName("user/login");
        log.info("Login GET request is handled successfully");

        log.debug("Exiting login request");
        return model;

    }

    @RequestMapping(value= {"/signup"}, method=RequestMethod.GET)
    public ModelAndView signup() {
        log.debug("Entering signup page");

        ModelAndView model = new ModelAndView();
        User user = new User();
        model.addObject("user", user);
        model.setViewName("user/signup");
        log.info("Signup GET request is handled successfully");
        log.debug("Exiting signup page");
        return model;
    }

    @RequestMapping(value= {"/signup"}, method=RequestMethod.POST)
    public ModelAndView createUser(@Valid User user, BindingResult bindingResult) {
        log.debug("Entering post request to signup page");
        ModelAndView model = new ModelAndView();
        User userExists = userService.findUserByEmail(user.getEmail());

        if(userExists != null) {
            bindingResult.rejectValue("email", "error.user", "This email already exists!");
            log.warn("In signup page, user is already exist");
        }
        if(bindingResult.hasErrors()) {
            model.setViewName("user/signup");
            log.warn("There is ERROR on signup page");
        } else {
            userService.saveUser(user);
            model.addObject("msg", "User has been registered successfully!");
            model.addObject("user", new User());
            model.setViewName("user/signup");

            log.info("Signup GET request is handled successfully");
        }
        log.debug("Exiting post request to signup page");
        return model;
    }

    @RequestMapping(value= {"/home/home"}, method=RequestMethod.GET)
    public ModelAndView home() {
        log.debug("Entering home/home request to home page");
        ModelAndView model = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());

        model.addObject("userName", user.getFirstname() + " " + user.getLastname());
        model.setViewName("home/home");
        log.info("Home GET request is handled successfully");
        log.debug("Exiting home/home request to home page");
        return model;
    }

    @RequestMapping(value= {"/access_denied"}, method=RequestMethod.GET)
    public ModelAndView accessDenied() {
        log.debug("Entering access_denied request to access_denied page");
        ModelAndView model = new ModelAndView();
        model.setViewName("errors/access_denied");
        log.info("Access_denied GET request is handled successfully");
        log.debug("Entering access_denied request to access_denied page");
        return model;
    }
}