package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@PreAuthorize("isAuthenticated()")
@RestController
public class AccountController {

private AccountDao accountDao;
private UserDao userDao;

public AccountController (AccountDao accountDao, UserDao userDao) {
    this.accountDao = accountDao;
    this.userDao = userDao;
}


@RequestMapping(path = "/accountbalance", method = RequestMethod.GET)
@ResponseStatus(HttpStatus.OK)
    public Account currentAccountBalance(Account account, Principal principal) {

    int loggedInUsersId = userDao.findIdByUsername( principal.getName() );


    return accountDao.seeAccountBalance(loggedInUsersId);
}


}
