package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class TransferService {

    private String baseApiUrl;
    private AuthenticatedUser currentUser;
    private RestTemplate restTemplate = new RestTemplate();
    private Transfer transfer;


    public TransferService(String baseApiUrl, AuthenticatedUser currentUser) {
        this.baseApiUrl = baseApiUrl;
        this.currentUser = currentUser;
    }

    public Transfer gettingTransfer(int accountFrom, int accountTo, double amount) {
        Transfer newTransfer = new Transfer();

        newTransfer.setTransferTypeId(2);
        newTransfer.setTransferStatusId(2);
        newTransfer.setAccountFrom(accountFrom);
        newTransfer.setAccountTo(accountTo);
        newTransfer.setAmount(amount);


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(currentUser.getToken());

        HttpEntity<Void> requestEntity = new HttpEntity<Void>(headers);

        ResponseEntity<Transfer> response = restTemplate.exchange(baseApiUrl + "transfer", HttpMethod.GET,
        requestEntity, Transfer.class);

        return response.getBody();

    }

//    transferTypeId: 2,
//    transferStatusId: 2,
//    accountFrom: 2001,
//    accountTo: 2002,
//    amount: 500.00


//    HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.setBearerAuth(currentUser.getToken());
//    HttpEntity<Void> requestEntity = new HttpEntity<Void>(headers)

//        ResponseEntity<Account> response = restTemplate.exchange(baseApiUrl + "accountbalance", HttpMethod.GET,
//        requestEntity, Account.class);



}
