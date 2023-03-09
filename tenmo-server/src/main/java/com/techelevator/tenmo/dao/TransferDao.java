package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

public interface TransferDao {

    Transfer sendMoney(int transferId, int accountFrom, int accountTo, double amount);


    // methods needed
        // add transfer - insert values into the transfer table
        // update balance from user sending
        // update balance from user receiving
        // get transfer amount

}
