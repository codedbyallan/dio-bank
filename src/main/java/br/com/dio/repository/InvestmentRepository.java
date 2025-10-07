package br.com.dio.repository;

import br.com.dio.exception.AccountWithInvestmentException;
import br.com.dio.exception.InvalidInvestmentException;
import br.com.dio.exception.InvestmentNotFoundException;
import br.com.dio.exception.WalletNotFoundException;
import br.com.dio.model.AccountWallet;
import br.com.dio.model.Investment;
import br.com.dio.model.InvestmentWallet;

import java.util.ArrayList;
import java.util.List;

import static br.com.dio.repository.CommonsRepository.checkFundsForTransaction;

public class InvestmentRepository {

    private long nextId;
    private final List<Investment> investments = new ArrayList<>();
    private final List<InvestmentWallet> wallets = new ArrayList<>();

    public Investment create(final long tax, final long initialFunds) {
        if (tax <= 0 || initialFunds <= 0) {
            throw new InvalidInvestmentException("Tax and initial funds must be greater than zero.");
        }
        this.nextId++;
        var investment = new Investment(this.nextId, tax, initialFunds);
        this.investments.add(investment);
        return investment;
    }

    public InvestmentWallet newInvestment(final AccountWallet account, final long id) {
        var accountInUse = wallets.stream().map(InvestmentWallet::getAccount).toList();
            if (accountInUse.contains(account)) {
                throw new AccountWithInvestmentException("Account '" + account + "' already has an investment wallet.");
            }

        var investment = findById(id);
        checkFundsForTransaction(account, investment.initialFunds());
        var wallet = new InvestmentWallet(investment, account, investment.initialFunds());
        this.wallets.add(wallet);
        return wallet;
    }

    public InvestmentWallet deposit(final String pix, final long funds) {
        var wallet = findWalletByAccountPix(pix);
        wallet.addMoney(wallet.getAccount().reduceMoney(funds), wallet.getService(), "Deposit to investment");
        return wallet;
    }

    public InvestmentWallet withdraw(final String pix, final long funds) {
        var wallet = findWalletByAccountPix(pix);
        checkFundsForTransaction(wallet, funds);
        wallet.getAccount().addMoney(wallet.reduceMoney(funds), wallet.getService(), "Withdraw from investment");
        if (wallet.getFunds() == 0) {
            this.wallets.remove(wallet);
        }
        return wallet;
    }

    public void updateAmount() {
        this.wallets.forEach(w -> w.updateAmount(w.getInvestment().tax()));
    }

    public Investment findById(final long id) {
        return this.investments.stream()
                .filter(i -> i.id() == id)
                .findFirst()
                .orElseThrow(() -> new InvestmentNotFoundException("Investment not found for id: " + id));
    }

    public InvestmentWallet findWalletByAccountPix(final String pix) {
        return this.wallets.stream()
                .filter(w -> w.getAccount().getPix().contains(pix))
                .findFirst()
                .orElseThrow(() -> new WalletNotFoundException("Wallet not found for pix: " + pix));
    }

    public List<Investment> list() {
        return this.investments;
    }

    public List<InvestmentWallet> listWallets() {
        return this.wallets;
    }


}
