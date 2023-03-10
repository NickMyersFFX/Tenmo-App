package com.techelevator.tenmo.services;


import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserCredentials;

import java.math.BigDecimal;
import java.sql.SQLOutput;
import java.util.Scanner;

// WILL HAVE TO WRITE METHODS IN HERE TO SHOW SUB MENU, BALANCE, ETC
// WILL BE USING RESTTEMPLATE IN THE CLIENT SIDE

public class ConsoleService {

    private final Scanner scanner = new Scanner(System.in);

    public int promptForMenuSelection(String prompt) {
        int menuSelection;
        System.out.print(prompt);
        try {
            menuSelection = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            menuSelection = -1;
        }
        return menuSelection;
    }

    public void printGreeting() {
        System.out.println("*********************");
        System.out.println("* Welcome to TEnmo! *");
        System.out.println("*********************");
    }

    public void printLoginMenu() {
        System.out.println();
        System.out.println("1: Register");
        System.out.println("2: Login");
        System.out.println("0: Exit");
        System.out.println();
    }

    public void printMainMenu() {
        System.out.println();
        System.out.println("1: View your current balance");
        System.out.println("2: View your past transfers");
        System.out.println("3: View your pending requests");
        System.out.println("4: Send TE bucks");
        System.out.println("5: Request TE bucks");
        System.out.println("6: View transfer by Id");
        System.out.println("0: Exit");
        System.out.println();
    }

    public UserCredentials promptForCredentials() {
        String username = promptForString("Username: ");
        String password = promptForString("Password: ");
        return new UserCredentials(username, password);
    }

    public String promptForString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public int promptForInt(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }

    public BigDecimal promptForBigDecimal(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return new BigDecimal(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a decimal number.");
            }
        }
    }

    public void pause() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    public void printErrorMessage() {
        System.out.println("An error occurred. Check the log for details.");
    }

    public void displayAccountBalance(Double balance){
        System.out.println("Your current balance is: " + balance);
    }

    public void displayUsers(User[] user) {
        System.out.println("-".repeat(30));
        System.out.println("Users");
        System.out.println("ID" + "             " + "Name");
        System.out.println("-".repeat(30));
        for (User users : user) {
            System.out.println(users.getId() + "           " + users.getUsername());
        }
        System.out.println("-".repeat(10));
        System.out.println("\n");

    }

    public int promptUserForInt(Integer integer) {
        System.out.print(integer);
        return integer;
    }

    public void displayTransferDetails(Transfer transfer) {
        System.out.println("-".repeat(30));
        System.out.println("Transfer Details");
        System.out.println("-".repeat(30));
        System.out.println("Id: " + transfer.getTransferId());
        System.out.println("From: " + transfer.getAccountFrom());
        System.out.println("To: "  + transfer.getAccountTo());
        System.out.println("Type: " + transfer.getTransferTypeId());
        System.out.println("Status: " + transfer.getTransferStatusId());
        System.out.println("Amount: " + transfer.getAmount());
        System.out.println("-".repeat(30));
    }

    public void transferHistory(Transfer[] transfer) {
        // INSERT FORMATTING
        System.out.println("-".repeat(30));
        System.out.println("Transfers");
        System.out.println("-".repeat(30));
        System.out.println("Id          From/To        Amount");
        for (Transfer transfers : transfer) {
            System.out.printf("%-7d From: %-10s $%1.2f\n", transfers.getTransferId(), transfers.getUsernameFrom(), transfers.getAmount());
            System.out.printf("%-7d To: %-10s $%1.2f\n", transfers.getTransferId(), transfers.getUsernameTo(), transfers.getAmount());
        }

    }

}
