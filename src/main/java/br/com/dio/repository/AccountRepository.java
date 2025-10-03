package br.com.dio.repository;

import br.com.dio.model.AccountWallet;

import java.util.List;

public class AccountRepository {

    private List<AccountWallet> accounts;

    public List <AccountWallet> list() {
        return this.accounts;
    }
}
