package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@PreAuthorize("isAuthenticated()")
@RestController
public class TransferController {

        private TransferDao transferDao;
        private UserDao userDao;

        public TransferController(TransferDao transferDao, UserDao userDao) {
            this.transferDao = transferDao;
            this.userDao = userDao;
        }

    @RequestMapping(path = "/transfer", method = RequestMethod.POST)
    public Transfer newTransfer(@RequestBody @Validated Transfer transfer) {
            return transferDao.createTransfer(transfer);
    }

    @RequestMapping(path = "/transfer/update", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void updatingBalance(@RequestBody Transfer transfer) {
        transferDao.updateBalance(transfer);
    }

//    @RequestMapping(path = "/transfer/receive", method = RequestMethod.POST)
//    @ResponseStatus(HttpStatus.CREATED)
//    public void BalanceReceiver(@RequestBody Transfer transfer) {
//        transferDao.updateBalanceReceiver(transfer);
//    }

//
//    @Override
//    public Transfer sendMoney(int transferId, int accountFrom, int accountTo, double amount) {
//        return null;
//    }
}
