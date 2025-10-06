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
        int option = -1;

        do {
            System.out.println("\n## Dio-Bank ##\n");
            System.out.println("Please choose an option:");
            System.out.println("1) Accounts");
            System.out.println("2) Investments");
            System.out.println("3) Search");
            System.out.println("0) Exit\n");
            System.out.print("Option: ");

            if (!sc.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                sc.next();
                continue;
            }

            option = sc.nextInt();
            sc.nextLine();

            switch (option) {
                case 1 -> {
                    int accountOption = -1;

                    do {
                        System.out.println("\n=== ACCOUNT MENU ===");
                        System.out.println("[1] Create Account");
                        System.out.println("[2] Deposit");
                        System.out.println("[3] Withdraw");
                        System.out.println("[4] Transfer");
                        System.out.println("[5] List All Accounts");
                        System.out.println("[0] Back to Main Menu");
                        System.out.print("Option: ");

                        if (!sc.hasNextInt()) {
                            System.out.println("Invalid input. Please enter a number.");
                            sc.next();
                            continue;
                        }

                        accountOption = sc.nextInt();
                        sc.nextLine();

                        switch (accountOption) {
                            case 1 -> System.out.println("Creating account... (placeholder)");
                            case 2 -> System.out.println("Depositing... (placeholder)");
                            case 3 -> System.out.println("Withdrawing... (placeholder)");
                            case 4 -> System.out.println("Transferring... (placeholder)");
                            case 5 -> System.out.println("Listing all accounts... (placeholder)");
                            case 0 -> System.out.println("Returning to main menu...");
                            default -> System.out.println("Invalid option. Please try again.");
                        }
                    } while (accountOption != 0);
                }

                case 2 -> {
                    int investmentOption = -1;

                    do {
                        System.out.println("\n=== INVESTMENT MENU ===");
                        System.out.println("[1] Create Investment Product");
                        System.out.println("[2] Open Investment Wallet");
                        System.out.println("[3] Deposit to Investment");
                        System.out.println("[4] Withdraw from Investment");
                        System.out.println("[5] Apply Earnings Update");
                        System.out.println("[6] List Investment Products");
                        System.out.println("[7] List Investment Wallets");
                        System.out.println("[0] Back to Main Menu");
                        System.out.print("Option: ");
                        if (!sc.hasNextInt()) {
                            System.out.println("Invalid input. Please enter a number.");
                            sc.next();
                            continue;
                        }

                        investmentOption = sc.nextInt();
                        sc.nextLine();

                        switch (investmentOption) {
                            case 1 -> System.out.println("Creating investment product... (placeholder)");
                            case 2 -> System.out.println("Opening investment wallet... (placeholder)");
                            case 3 -> System.out.println("Depositing to investment... (placeholder)");
                            case 4 -> System.out.println("Withdrawing from investment... (placeholder)");
                            case 5 -> System.out.println("Applying earnings update... (placeholder)");
                            case 6 -> System.out.println("Listing investment products... (placeholder)");
                            case 7 -> System.out.println("Listing investment wallets... (placeholder)");
                            case 0 -> System.out.println("Returning to main menu...");
                            default -> System.out.println("Invalid option. Please try again.");
                        }
                    } while (investmentOption != 0);
                }

                case 3 -> {
                    int searchOption = -1;

                    do {
                        System.out.println("\n=== SEARCH MENU ===");
                        System.out.println("[1] Find Account by PIX");
                        System.out.println("[2] Find Investment Wallet by PIX");
                        System.out.println("[3] View Account Transactions");
                        System.out.println("[0] Back to Main Menu");
                        System.out.print("Option: ");

                        if (!sc.hasNextInt()) {
                            System.out.println("Invalid input. Please enter a number.");
                            sc.next();
                            continue;
                        }

                        searchOption = sc.nextInt();
                        sc.nextLine();

                        switch (searchOption) {
                            case 1 -> System.out.println("Finding account by PIX... (placeholder)");
                            case 2 -> System.out.println("Finding investment wallet by PIX... (placeholder)");
                            case 3 -> System.out.println("Viewing account transactions... (placeholder)");
                            case 0 -> System.out.println("Returning to main menu...");
                            default -> System.out.println("Invalid option. Please try again.");
                        }
                    } while (searchOption != 0);
                }
                case 0 -> System.out.println("Thank you for using our services. See you next time!");
                default -> System.out.println("Invalid option. Please try again.");
            }

        } while (option != 0);

        sc.close();
    }
}