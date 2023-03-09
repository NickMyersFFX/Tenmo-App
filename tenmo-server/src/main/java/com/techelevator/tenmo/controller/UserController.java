package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@PreAuthorize("isAuthenticated()")
@RestController
public class UserController {

    private AccountDao accountDao;
    private UserDao userDao;

    public UserController(AccountDao accountDao, UserDao userDao) {
        this.accountDao = accountDao;
        this.userDao = userDao;
    }

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public List<User> displaysAllUsers(Principal principal) {
        List<User> usersWithoutPrincipal = new ArrayList<User>();
        int loggedInUser = userDao.findIdByUsername(principal.getName());

        List<User> users = userDao.findAll();

        for (User user : users) {
            if (loggedInUser != user.getId()) {
                usersWithoutPrincipal.add(user);
            }
        }
        return usersWithoutPrincipal;

    }
}





