package br.com.dio.model;

import java.util.List;

import static br.com.dio.model.BankService.ACCOUNT;

public class AccountWallet extends Wallet {

    private final List<String> pix;

    public AccountWallet(final List<String> pix) {
        super(ACCOUNT);
        this.pix = pix;
    }

    public AccountWallet(final long amount, List<String> pix) {
        super(ACCOUNT);
        this.pix = pix;
        addFunds(amount, "Valor inicial");
    }

    public void addMoney(final long amount, final String description) {
        var money = generateMoney(amount, description);
        this.money.addAll(money);
    }
}
