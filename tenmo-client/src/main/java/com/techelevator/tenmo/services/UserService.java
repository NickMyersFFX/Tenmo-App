package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.User;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class UserService {

    private String baseApiUrl;
    private AuthenticatedUser currentUser;
    private RestTemplate restTemplate = new RestTemplate();

    public UserService(String baseApiUrl, AuthenticatedUser currentUser) {
        this.baseApiUrl = baseApiUrl;
        this.currentUser = currentUser;
    }



    public User[] listOfAllUsers() {

        User[] users = null;
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(currentUser.getToken());

        HttpEntity<Void> entityForGet = new HttpEntity<Void>(headers);

        ResponseEntity<User[]> response = restTemplate.exchange(
                baseApiUrl + "users", // URL
                HttpMethod.GET,                    // HttpMethod (GET, POST, PUT, DELETE)
                entityForGet,                      // HttpEntity with BearerAuth set to the token in the header
                User[].class);              // Data type of the response

        users = response.getBody(); // getBody() retrieves the deserialized object(s) from the response ResponseEntity

        return users;


    }

}
