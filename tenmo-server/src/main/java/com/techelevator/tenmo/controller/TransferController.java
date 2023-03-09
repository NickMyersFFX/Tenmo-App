package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public Transfer newTransfer(@RequestBody Transfer transfer, Principal principal) {
            return transferDao.createTransfer(transfer);
    }

    @RequestMapping(path = "/transfer/send", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void BalanceSender(@RequestBody Transfer transfer) {
        transferDao.updateBalanceSender(transfer);
    }

    @RequestMapping(path = "/transfer/receive", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void BalanceReceiver(@RequestBody Transfer transfer) {
        transferDao.updateBalanceReceiver(transfer);
    }

//
//    @Override
//    public Transfer sendMoney(int transferId, int accountFrom, int accountTo, double amount) {
//        return null;
//    }
}
