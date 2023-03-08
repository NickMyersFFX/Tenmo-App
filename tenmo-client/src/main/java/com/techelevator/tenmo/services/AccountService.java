package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class AccountService {

    private String baseApiUrl;
    private AuthenticatedUser currentUser;
    private RestTemplate restTemplate = new RestTemplate();


    public AccountService(String baseApiUrl, AuthenticatedUser currentUser) {
        this.baseApiUrl = baseApiUrl;
        this.currentUser = currentUser;
    }


    public Account getAccountBalance() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(currentUser.getToken());
        HttpEntity<Void> requestEntity = new HttpEntity<Void>(headers);

        ResponseEntity<Account> response = restTemplate.exchange(baseApiUrl + "accountbalance", HttpMethod.GET,
                requestEntity, Account.class);

        return response.getBody();

    }


}
