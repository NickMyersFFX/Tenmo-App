package com.techelevator.tenmo;

import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.services.*;

import java.math.BigDecimal;
import java.util.ArrayList;

// might need to add things here

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";

    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);

    private AuthenticatedUser currentUser;
    private AccountService accountService;
    private UserService userService;
    private TransferService transferService;

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private void run() {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            mainMenu();
        }
    }

    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != 0 && currentUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                handleRegister();
            } else if (menuSelection == 2) {
                handleLogin();
            } else if (menuSelection != 0) {
                System.out.println("Invalid Selection");
                consoleService.pause();
            }
        }
    }

    private void handleRegister() {
        System.out.println("Please register a new user account");
        UserCredentials credentials = consoleService.promptForCredentials();
        if (authenticationService.register(credentials)) {
            System.out.println("Registration successful. You can now login.");
        } else {
            consoleService.printErrorMessage();
        }
    }

    private void handleLogin() {
        UserCredentials credentials = consoleService.promptForCredentials();
        currentUser = authenticationService.login(credentials);
        if (currentUser == null) {
            consoleService.printErrorMessage();
        } else {
            accountService = new AccountService(API_BASE_URL, currentUser);
            transferService = new TransferService(API_BASE_URL, currentUser);
            userService = new UserService(API_BASE_URL, currentUser);
        }
    }

    private void mainMenu() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printMainMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                viewCurrentBalance();
            } else if (menuSelection == 2) {
                viewTransferHistory();
            } else if (menuSelection == 3) {
                viewPendingRequests();
            } else if (menuSelection == 4) {
                sendBucks();
            } else if (menuSelection == 5) {
                requestBucks();
            } else if (menuSelection == 6) {
                viewTransferById();
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }

    private void viewTransferById() {
        Integer promptUserInput = consoleService.promptForInt("Please enter a transfer Id to view: ");
        Transfer transferId = transferService.getTransferById(promptUserInput);
        consoleService.displayTransferDetails(transferId);

    }

    private void viewCurrentBalance() {
        Double accountBalance = accountService.getAccountBalance().getBalance();

        consoleService.displayAccountBalance(accountBalance);

    }

    private void viewTransferHistory() {
        Transfer[] transfer = transferService.listOfTransfers();
        consoleService.transferHistory(transfer);

        // TODO Auto-generated method stub

    }

    private void viewPendingRequests() {
        // TODO Auto-generated method stub

    }

    private void sendBucks() {

        User[] user = userService.listOfAllUsers();
            if (user != null) {
                consoleService.displayUsers(user);
                } else consoleService.printErrorMessage();

      //  consoleService.promptForInt("Enter ID of user you are sending to (0 to cancel): ");
//            while (consoleService.promptUserForInt(0) == 0 ) {
//                break;
//                } consoleService.printMainMenu();
           // else consoleService.promptForInt("Enter ID of user you are sending to (0 to cancel): ");

            int idTo = consoleService.promptForInt("Enter ID of user you are sending to (0 to cancel): ");
            BigDecimal amount = consoleService.promptForBigDecimal("Enter amount: ");

            if (amount.doubleValue() <= accountService.getAccountBalance().getBalance() && amount.doubleValue() > 0 ) {
                Transfer transfer = transferService.gettingTransfer
                        (accountService.gettingAccountIdByUserId(currentUser.getUser().getId()),
                                accountService.gettingAccountIdByUserId(idTo), amount.doubleValue());
                transferService.updateBalance(transfer);
                consoleService.displayTransferDetails(transfer);

            } else {
                consoleService.printErrorMessage();
            }


        }



//    User[] user = userService.listOfAllUsers();
//        if (user != null) {
//        consoleService.displayUsers(user);
//    } else consoleService.printErrorMessage();
//
//
//    int idTo = consoleService.promptForInt("Enter ID of user you are sending to (0 to cancel): ");
//    BigDecimal amount =  consoleService.promptForBigDecimal("Enter amount: ");
//
//    Transfer transfer = transferService.gettingTransfer
//            (accountService.gettingAccountIdByUserId(currentUser.getUser().getId()), accountService.gettingAccountIdByUserId(idTo), amount.doubleValue());
//
//        transferService.updateBalance(transfer);


	private void requestBucks() {
		// TODO Auto-generated method stub

	}

}
