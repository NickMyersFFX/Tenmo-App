package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;


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
    public Transfer newTransfer(@RequestBody Transfer transfer) {
            return transferDao.createTransfer(transfer);
    }

    @RequestMapping(path = "/transfer/update", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void updatingBalance(@RequestBody Transfer transfer) {
        transferDao.updateBalance(transfer);
    }

    @RequestMapping(path = "/transfer/history", method = RequestMethod.GET)
        public List<Transfer> getTransferHistory(Principal principal) {
            int currentUser = userDao.findIdByUsername(principal.getName());
            return transferDao.listOfTransfers(currentUser);

    }

    @RequestMapping(path = "/transfer/{id}", method = RequestMethod.GET)
    public Transfer gettingTransferById(@PathVariable("id") int transferId) {
        Transfer transfer = transferDao.transferById(transferId);
        if ( transfer == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Transfer not found");
        } else {
            return transfer;
        }



    }


}
