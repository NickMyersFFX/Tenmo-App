package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import javax.swing.table.TableRowSorter;
import java.math.BigDecimal;

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

        HttpEntity<Transfer> requestEntity = new HttpEntity<Transfer>(newTransfer, headers);

        ResponseEntity<Transfer> response = restTemplate.exchange(baseApiUrl + "transfer", HttpMethod.POST,
        requestEntity, Transfer.class);

        return response.getBody();

    }


    public Transfer updateBalance(Transfer transfer) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(currentUser.getToken());

        HttpEntity<Transfer> requestEntity = new HttpEntity<Transfer>(transfer, headers);

        ResponseEntity<Transfer> response = restTemplate.exchange(baseApiUrl + "transfer/update", HttpMethod.POST,
                requestEntity, Transfer.class);

        return response.getBody();

    }

    public Transfer getTransferById(int transferId) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(currentUser.getToken());
        HttpEntity<Void> requestEntity = new HttpEntity<Void>(headers);

        Transfer transfer = null;
        try {
            ResponseEntity<Transfer> response = restTemplate.exchange(baseApiUrl + "transfer/" + transferId, HttpMethod.GET,
                    requestEntity, Transfer.class);
            transfer = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
      return transfer;

    }

    public Transfer[] listOfTransfers() {
        Transfer[] transferArray = null;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(currentUser.getToken());

        HttpEntity<Void> requestEntity = new HttpEntity<Void>(headers);

        try {
            ResponseEntity<Transfer[]> response = restTemplate.exchange(baseApiUrl + "transfer/history", HttpMethod.GET,
                    requestEntity, Transfer[].class);
            transferArray = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return transferArray;
    }


}
