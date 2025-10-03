package br.com.dio.model;

import lombok.Getter;
import lombok.ToString;

import java.util.UUID;
import java.util.stream.Stream;

import static br.com.dio.model.BankService.*;

@ToString
@Getter

public class InvestmentWallet extends Wallet {

    private final Investment investment;
    private final AccountWallet account;

    public InvestmentWallet(final Investment investment, final AccountWallet account, final long amount) {
        super(INVESTMENT);
        this.investment = investment;
        this.account = account;
        addFunds(account.reduceMoney(amount), getService(), "Investimento inicial");
    }

    public void updateAmount(final long percent) {
        var amount = getFunds() * percent / 100;
        var history = new MoneyAudit(UUID.randomUUID(), getService(), "Rendimento de " + percent + "%", java.time.OffsetDateTime.now());
        var money = Stream.generate(() -> new Money(history)).limit(amount).toList();
        this.money.addAll(money);

    }
}
