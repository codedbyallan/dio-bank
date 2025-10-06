package br.com.dio;

import br.com.dio.repository.AccountRepository;
import br.com.dio.repository.InvestmentRepository;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        AccountRepository accountRepository = new AccountRepository();
        InvestmentRepository investmentRepository = new InvestmentRepository();

        System.out.println("Welcome to the Dio-Bank, the official bank of DIO.");
        int option;

        do {
            System.out.println("\n## Dio-Bank ##\n");
            System.out.println("Please choose an option:");
            System.out.println("1. Account");
            System.out.println("2. Investments");
            System.out.println("3. Search");
            System.out.println("0. Exit\n");
            option = sc.nextInt();
            sc.nextLine();

            switch (option) {
                case 1:
                    System.out.println("Account selected.");
                    System.out.println("Please choose an option:\n");
                    System.out.println("[1] Create Account");
                    System.out.println("[2] Deposit");
                    System.out.println("[3] Withdraw");
                    System.out.println("[4] Transfer");
                    System.out.println("[5] List All Accounts");
                    System.out.println("[0] Back to Main Menu");

                    final int accountOption = sc.nextInt();
                    sc.nextLine();

                    if (accountOption == 1) {
                    } else if (accountOption == 2) {
                    } else if (accountOption == 3) {
                    } else if (accountOption == 4) {
                    } else if (accountOption == 5) {
                    } else if (accountOption == 0) {
                        System.out.println("Returning to main menu...");
                    } else {
                        System.out.println("Invalid option. Please try again.");
                    }
                    break;

                case 2:
                    System.out.println("Investments selected.");
                    System.out.println("Please choose an option:\n");
                    System.out.println("[1] Create Investment Product");
                    System.out.println("[2] Open Investment Wallet");
                    System.out.println("[3] Deposit to Investment");
                    System.out.println("[4] Withdraw from Investment");
                    System.out.println("[5] Apply Earnings Update");
                    System.out.println("[6] List Investment Products");
                    System.out.println("[7] List Investment Wallets");
                    System.out.println("[0] Back to Main Menu");

                    final int investmentOption = sc.nextInt();
                    sc.nextLine();

                    if (investmentOption == 1) {
                    } else if (investmentOption == 2) {
                    } else if (investmentOption == 3) {
                    } else if (investmentOption == 4) {
                    } else if (investmentOption == 5) {
                    } else if (investmentOption == 6) {
                    } else if (investmentOption == 7) {
                    } else if (investmentOption == 0) {
                        System.out.println("Returning to main menu...");
                    } else {
                        System.out.println("Invalid option. Please try again.");
                    }
                    break;

                case 3:
                    System.out.println("Search selected.");
                    System.out.println("Please choose an option:\n");
                    System.out.println("[1] Find Account by PIX");
                    System.out.println("[2] Find Investment Wallet by PIX");
                    System.out.println("[3] View Account Transactions");
                    System.out.println("[0] Back to Main Menu");
                    final int searchOption = sc.nextInt();
                    sc.nextLine();

                    if (searchOption == 1) {
                    } else if (searchOption == 2) {
                    } else if (searchOption == 3) {
                    } else if (searchOption == 0) {
                        System.out.println("Returning to main menu...");
                    } else {
                        System.out.println("Invalid option. Please try again.");
                    }
                    break;

                case 0:
                    System.out.println("Thank you for using our services. See you next time!");
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        } while (option != 0);


        sc.close();
    }
}



