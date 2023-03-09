package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

public interface TransferDao {

    Transfer viewTransfers(int transferId, int accountFrom, int accountTo, int amount);

}
