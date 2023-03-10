package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.User;
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

   public Account[] listOfAllAccounts() {
       Account[] accounts = null;
       HttpHeaders headers = new HttpHeaders();
       headers.setBearerAuth(currentUser.getToken());

       HttpEntity<Void> entityForGet = new HttpEntity<Void>(headers);

       ResponseEntity<Account[]> response = restTemplate.exchange(
               baseApiUrl + "accounts", // URL
               HttpMethod.GET,                    // HttpMethod (GET, POST, PUT, DELETE)
               entityForGet,                      // HttpEntity with BearerAuth set to the token in the header
               Account[].class);              // Data type of the response

       accounts = response.getBody(); // getBody() retrieves the deserialized object(s) from the response ResponseEntity

       return accounts;
   }

   public int gettingAccountIdByUserId(int userId) {
        int accountId = 0;
        for (int i = 0; i < listOfAllAccounts().length; i++) {
            if (listOfAllAccounts()[i].getUserId() == userId) {
                accountId = listOfAllAccounts()[i].getAccountId();
            }

        }
        return accountId;
   }

}
