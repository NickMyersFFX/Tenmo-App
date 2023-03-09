package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;



@PreAuthorize("isAuthenticated()")
@RestController
public class TransferController implements TransferDao {

        private AccountDao accountDao;
        private UserDao userDao;

        public TransferController(AccountDao accountDao, UserDao userDao) {
            this.accountDao = accountDao;
            this.userDao = userDao;
        }


    @Override
    public Transfer sendMoney(int transferId, int accountFrom, int accountTo, double amount) {
        return null;
    }
}
