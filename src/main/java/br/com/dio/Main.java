package br.com.dio;

import br.com.dio.exception.*;
import br.com.dio.repository.AccountRepository;
import br.com.dio.repository.InvestmentRepository;

import java.util.Arrays;
import java.util.List;
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

            option = readInt(sc, "Option: ");

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

                        accountOption = readInt(sc, "Option: ");


                        switch (accountOption) {
                            case 1 -> handleCreateAccount(sc, accountRepository);
                            case 2 -> handleDeposit(sc, accountRepository);
                            case 3 -> handleWithdraw(sc, accountRepository);
                            case 4 -> handleTransfer(sc, accountRepository);
                            case 5 -> handleListAccounts(accountRepository);
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

                        investmentOption = readInt(sc, "Option: ");

                        switch (investmentOption) {
                            case 1 -> handleCreateInvestmentProduct(sc, investmentRepository);
                            case 2 -> handleOpenInvestmentWallet(sc, accountRepository, investmentRepository);
                            case 3 -> handleDepositToInvestment(sc, investmentRepository);
                            case 4 -> handleWithdrawFromInvestment(sc, investmentRepository);
                            case 5 -> handleApplyEarningsUpdate(investmentRepository);
                            case 6 -> handleListInvestmentProducts(investmentRepository);
                            case 7 -> handleListInvestmentWallets(investmentRepository);
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

                        searchOption = readInt(sc, "Option: ");

                        switch (searchOption) {
                            case 1 -> handleFindAccountByPix(sc, accountRepository);
                            case 2 -> handleFindInvestmentWalletByPix(sc, investmentRepository);
                            case 3 -> handleViewAccountTransactions(sc, accountRepository);
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

    //Helpers for reading input and handling operations

    private static int readInt(Scanner sc, String label) {
        while (true) {
            System.out.print(label);
            if (sc.hasNextInt()) {
                int value = sc.nextInt();
                sc.nextLine();
                return value;
            }
            System.out.println("Invalid input. Please enter an integer number.");
            sc.nextLine();
        }
    }

    private static long readLong(Scanner sc, String label) {
        while (true) {
            System.out.print(label);
            if (sc.hasNextLong()) {
                long value = sc.nextLong();
                sc.nextLine();
                return value;
            }
            System.out.println("Invalid input. Please enter a numeric value.");
            sc.nextLine();
        }
    }

    private static List<String> readPixList(Scanner sc) {
        System.out.print("Enter PIX keys separated by commas: ");
        String input = sc.nextLine();
        return Arrays.stream(input.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();
    }

    //Handlers for account operations

    private static void handleCreateAccount(Scanner sc, AccountRepository repo) {
        var pixList = readPixList(sc);
        if (pixList.isEmpty()) {
            System.out.println("[Error] At least one PIX key is required to create an account.");
            return;
        }
        long initial = readLong(sc, "Enter initial deposit amount: ");
        if (initial < 0) {
            System.out.println("[Error] Initial deposit cannot be negative.");
            return;
        }
        try {
            var acc = repo.create(pixList, initial);
            System.out.println("Account created successfully!");
            System.out.println("Pix: " + acc.getPix());
            System.out.println("Balance: " + acc.getFunds());
        } catch (PixInUseException e) {
            System.out.println("[Error] PIX already in use: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("[Error] Invalid amount: " + e.getMessage());
        }
    }

    private static void handleListAccounts(AccountRepository repo) {
        var list = repo.list();
        if (list.isEmpty()) {
            System.out.println("No accounts found.");
            return;
        }
        System.out.println("=== Accounts ===");
        for (var acc : list) {
            System.out.println("Pix: " + acc.getPix() + " | Balance: " + acc.getFunds());
        }
    }

    private static String readSinglePix(Scanner sc) {
        while (true) {
            System.out.print("Enter PIX key: ");
            String s = sc.nextLine().trim();
            if (!s.isEmpty()) return s;
            System.out.println("[Error] PIX key cannot be empty.");
        }
    }

    private static void handleDeposit(Scanner sc, AccountRepository repo) {
        String pix = readSinglePix(sc);
        long amount = readLong(sc, "Enter deposit amount: ");
        if (amount <= 0) {
            System.out.println("[Error] Amount must be greater than zero.");
            return;
        }
        try {
            repo.deposit(pix, amount);
            long newBalance = repo.findByPix(pix).getFunds();
            System.out.println("Deposit successful!");
            System.out.println("PIX: " + pix + " | New balance: " + newBalance);
        } catch (AccountNotFoundException e) {
            System.out.println("[Error] Account not found: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("[Error] Invalid amount: " + e.getMessage());
        }
    }

    private static void handleWithdraw(Scanner sc, AccountRepository repo) {
        String pix = readSinglePix(sc);
        long amount = readLong(sc, "Enter withdrawal amount: ");
        if (amount <= 0) {
            System.out.println("[Error] Amount must be greater than zero.");
            return;
        }
        try {
            repo.withdraw(pix, amount);
            long newBalance = repo.findByPix(pix).getFunds();
            System.out.println("Withdrawal successful!");
            System.out.println("PIX: " + pix + " | New balance: " + newBalance);
        } catch (AccountNotFoundException e) {
            System.out.println("[Error] Account not found: " + e.getMessage());
        } catch (NoFundsEnoughException e) {
            System.out.println("[Error] Insufficient funds: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("[Error] Invalid value: " + e.getMessage());
        }
    }

    private static void handleTransfer(Scanner sc, AccountRepository repo) {
        System.out.println("=== Transfer ===");
        System.out.println("(Source)");
        String fromPix = readSinglePix(sc);
        System.out.println("(Target)");
        String toPix = readSinglePix(sc);
        if (fromPix.equals(toPix)) {
            System.out.println("[Error] Source and target PIX must be different.");
            return;
        }
        long amount = readLong(sc, "Enter transfer amount: ");

        if (amount <= 0) {
            System.out.println("[Error] Amount must be greater than zero.");
            return;
        }

        try {
            repo.transferMoney(fromPix, toPix, amount);
            long fromBalance = repo.findByPix(fromPix).getFunds();
            long toBalance = repo.findByPix(toPix).getFunds();
            System.out.println("Transfer successful!");
            System.out.println("From PIX: " + fromPix + " | New balance: " + fromBalance);
            System.out.println("To PIX: " + toPix + " | New balance: " + toBalance);
        } catch (AccountNotFoundException e) {
            System.out.println("[Error] Account not found: " + e.getMessage());
        } catch (NoFundsEnoughException e) {
            System.out.println("[Error] Insufficient funds: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("[Error] Invalid amount: " + e.getMessage());
        }
    }

    //Handlers for investment operations

    private static void handleCreateInvestmentProduct(Scanner sc, InvestmentRepository repo) {
        System.out.println("=== Create Investment Product ===");

        long tax = readLong(sc, "Enter tax (% as integer, e.g. 5): ");
        long initialFunds = readLong(sc, "Enter required initial funds (units): ");

        try {
            var inv = repo.create(tax, initialFunds);
            System.out.println("[OK] Investment product created!");
            System.out.println("ID: " + inv.id()
                    + " | Tax: " + inv.tax() + "%"
                    + " | InitialFunds: " + inv.initialFunds());
        } catch (InvalidInvestmentException e) {
            System.out.println("[Error] " + e.getMessage());
        }
    }

    private static void handleListInvestmentProducts(InvestmentRepository repo) {
        var list = repo.list();
        if (list.isEmpty()) {
            System.out.println("(No investment products yet)");
            return;
        }
        System.out.println("\n=== INVESTMENT PRODUCTS ===");
        for (var inv : list) {
            System.out.println("ID: " + inv.id()
                    + " | Tax: " + inv.tax() + "%"
                    + " | InitialFunds: " + inv.initialFunds());
        }
    }

    private static void handleOpenInvestmentWallet(Scanner sc,
                                                   AccountRepository accountRepo,
                                                   InvestmentRepository investRepo) {
        System.out.println("=== Open Investment Wallet ===");

        String pix = readSinglePix(sc);
        long id = readLong(sc, "Enter investment product ID: ");
        if (id <= 0) {
            System.out.println("[Error] ID must be greater than zero.");
            return;
        }

        try {
            var account = accountRepo.findByPix(pix);
            var wallet = investRepo.newInvestment(account, id);

            System.out.println("[OK] Investment wallet opened!");
            System.out.println("Account PIX: " + pix);
            System.out.println("Product ID: " + wallet.getInvestment().id() + " | Tax: "
                    + wallet.getInvestment().tax() + "%");
            System.out.println("Wallet funds (units): " + wallet.getFunds());
            System.out.println("Account funds after opening (units): " + wallet.getAccount().getFunds());

        } catch (AccountNotFoundException e) {
            System.out.println("[Error] Account not found: " + e.getMessage());
        } catch (InvestmentNotFoundException e) {
            System.out.println("[Error] Investment product not found: " + e.getMessage());
        } catch (AccountWithInvestmentException e) {
            System.out.println("[Error] This account already has an investment wallet.");
        } catch (NoFundsEnoughException e) {
            System.out.println("[Error] Insufficient funds in account for initial investment.");
        }
    }

    private static void handleDepositToInvestment(Scanner sc, InvestmentRepository investRepo) {
        System.out.println("=== Deposit to Investment ===");
        String pix = readSinglePix(sc);
        long amount = readLong(sc, "Enter deposit amount (units): ");
        if (amount <= 0) {
            System.out.println("[Error] Amount must be greater than zero.");
            return;
        }
        try {
            var wallet = investRepo.deposit(pix, amount);

            System.out.println("[OK] Deposit to investment done!");
            System.out.println("Account PIX: " + pix);
            System.out.println("Investment funds (units): " + wallet.getFunds());
            System.out.println("Account funds after deposit (units): " + wallet.getAccount().getFunds());
        } catch (WalletNotFoundException e) {
            System.out.println("[Error] Investment wallet not found for this PIX.");
        } catch (NoFundsEnoughException e) {
            System.out.println("[Error] Insufficient account funds for this deposit.");
        } catch (IllegalArgumentException e) {
            System.out.println("[Error] " + e.getMessage());
        }
    }

    private static void handleWithdrawFromInvestment(Scanner sc, InvestmentRepository investRepo) {
        System.out.println("=== Withdraw from Investment ===");
        String pix = readSinglePix(sc);
        long amount = readLong(sc, "Enter withdrawal amount (units): ");
        if (amount <= 0) {
            System.out.println("[Error] Amount must be greater than zero.");
            return;
        }
        try {
            var wallet = investRepo.withdraw(pix, amount);

            boolean closed = (wallet.getFunds() == 0);

            System.out.println("[OK] Withdraw from investment done!");
            System.out.println("Account PIX: " + pix);
            System.out.println("Account funds after withdraw (units): " + wallet.getAccount().getFunds());
            if (closed) {
                System.out.println("Investment wallet balance is 0 and was closed.");
            } else {
                System.out.println("Investment funds (units): " + wallet.getFunds());
            }
        } catch (WalletNotFoundException e) {
            System.out.println("[Error] Investment wallet not found for this PIX.");
        } catch (NoFundsEnoughException e) {
            System.out.println("[Error] Insufficient investment funds for this withdrawal.");
        } catch (IllegalArgumentException e) {
            System.out.println("[Error] " + e.getMessage());
        }
    }

    private static void handleApplyEarningsUpdate(InvestmentRepository repo) {
        var wallets = repo.listWallets();
        if (wallets.isEmpty()) {
            System.out.println("(No investment wallets to update)");
            return;
        }
        repo.updateAmount();

        System.out.println("[OK] Earnings applied to all wallets.");
        System.out.println("=== Wallets after update ===");
        for (var w : wallets) {
            System.out.println("PIX: " + w.getAccount().getPix()
                    + " | Product ID: " + w.getInvestment().id()
                    + " | Tax: " + w.getInvestment().tax() + "%"
                    + " | Funds (units): " + w.getFunds());
        }
    }

    private static void handleListInvestmentWallets(InvestmentRepository repo) {
        var wallets = repo.listWallets();
        if (wallets.isEmpty()) {
            System.out.println("(No investment wallets)");
            return;
        }

        System.out.println("\n=== INVESTMENT WALLETS ===");
        for (var w : wallets) {
            System.out.println("PIX: " + w.getAccount().getPix()
                    + " | Product ID: " + w.getInvestment().id()
                    + " | Tax: " + w.getInvestment().tax() + "%"
                    + " | Funds (units): " + w.getFunds());
        }
    }

    //Handlers for search operations

    private static void handleFindAccountByPix(Scanner sc, AccountRepository accountRepo) {
        System.out.println("=== Find Account by PIX ===");
        String pix = readSinglePix(sc);
        try {
            var acc = accountRepo.findByPix(pix);
            System.out.println("[OK] Account found!");
            System.out.println("PIX list: " + acc.getPix());
            System.out.println("Balance (units): " + acc.getFunds());
        } catch (AccountNotFoundException e) {
            System.out.println("[Error] Account not found: " + e.getMessage());
        }
    }

    private static void handleFindInvestmentWalletByPix(Scanner sc, InvestmentRepository investRepo) {
        System.out.println("=== Find Investment Wallet by PIX ===");
        String pix = readSinglePix(sc);
        try {
            var w = investRepo.findWalletByAccountPix(pix);
            System.out.println("[OK] Investment wallet found!");
            System.out.println("Account PIX list: " + w.getAccount().getPix());
            System.out.println("Product ID: " + w.getInvestment().id()
                    + " | Tax: " + w.getInvestment().tax() + "%");
            System.out.println("Wallet funds (units): " + w.getFunds());
        } catch (WalletNotFoundException e) {
            System.out.println("[Error] Investment wallet not found: " + e.getMessage());
        }
    }

    private static void handleViewAccountTransactions(Scanner sc, AccountRepository accountRepo) {
        System.out.println("=== View Account Transactions ===");
        String pix = readSinglePix(sc);
        try {
            var acc = accountRepo.findByPix(pix);
            var audits = acc.getFinancialTransactions();

            if (audits.isEmpty()) {
                System.out.println("(No transactions)");
                return;
            }

            var last10 = audits.stream()
                    .sorted((a, b) -> b.createdAt().compareTo(a.createdAt()))
                    .limit(10)
                    .toList();

            System.out.println("Showing last " + last10.size() + " transactions for PIX: " + acc.getPix());
            System.out.println("----------------------------------------------------------------");
            for (var a : last10) {
                System.out.println("ID: " + a.transactionId());
                System.out.println("Service: " + a.targetService());
                System.out.println("When: " + a.createdAt());
                System.out.println("Desc: " + a.description());
                System.out.println("----------------------------------------------------------------");
            }
        } catch (AccountNotFoundException e) {
            System.out.println("[Error] Account not found: " + e.getMessage());
        }
    }





}


