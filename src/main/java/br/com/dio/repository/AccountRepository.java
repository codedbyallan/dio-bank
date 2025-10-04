package br.com.dio.repository;

import br.com.dio.exception.AccountNotFoundException;
import br.com.dio.exception.PixInUseException;
import br.com.dio.model.AccountWallet;

import java.util.List;

import static br.com.dio.repository.CommonsRepository.checkFundsForTransaction;

public class AccountRepository {

    private List<AccountWallet> accounts;

    public AccountWallet create(final List<String> pix, final long initialAmount) {

        var pixInUse = accounts.stream().flatMap(a -> a.getPix().stream()).toList();
        for (var p : pix) {
            if (pixInUse.contains(p)) {
                throw new PixInUseException("Pix already in use: " + p);
            }
        }
        var newAccount = new AccountWallet(initialAmount, pix);
        accounts.add(newAccount);
        return newAccount;
    }

    public void deposit(final String pix, final long fundsAmount) {
        var account = findByPix(pix);
        account.addMoney(fundsAmount, "Deposit by PIX");
    }

    public long withdraw(final String pix, final long amount) {
        var source = findByPix(pix);
        checkFundsForTransaction(source, amount);
        source.reduceMoney(amount);
        return amount;
    }

    public void transferMoney(final String sourcePix, final String targetPix, final long amount) {
        var source = findByPix(sourcePix);
        checkFundsForTransaction(source, amount);
        var target = findByPix(targetPix);
        var message = "Transfer from '" + sourcePix + "' to '" + targetPix + "'";
        target.addMoney(source.reduceMoney(amount), source.getService(), message);
    }

    public AccountWallet findByPix(final String pix) {
        return accounts.stream()
                .filter(a -> a.getPix().contains(pix))
                .findFirst()
                .orElseThrow(() -> new AccountNotFoundException("Invalid pix. The key: " + pix + " does not exist."));
    }

    public List<AccountWallet> list() {
        return this.accounts;
    }
}
