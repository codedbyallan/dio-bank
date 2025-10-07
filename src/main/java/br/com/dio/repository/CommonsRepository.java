package br.com.dio.repository;

import br.com.dio.exception.NoFundsEnoughException;

import br.com.dio.model.BankService;
import br.com.dio.model.Money;
import br.com.dio.model.MoneyAudit;
import br.com.dio.model.Wallet;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

import static lombok.AccessLevel.*;

@NoArgsConstructor(access = PRIVATE)
public final class CommonsRepository {

    public static void checkFundsForTransaction(final Wallet source, final long amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("The amount must be greater than zero.");
        }
        if (source.getFunds() < amount) {
            throw new NoFundsEnoughException("Insufficient funds for the transaction.");
        }
    }

    public static List <Money> generateMoney(
            final UUID transactionId,
            final BankService service,
            final long amount,
            final String description) {

        if (amount <= 0) {
            throw new IllegalArgumentException("The amount must be greater than zero.");
        }
        Objects.requireNonNull(transactionId, "transactionId");
        Objects.requireNonNull(service, "service");
        Objects.requireNonNull(description, "description");

        var history = new MoneyAudit(transactionId, service, description, OffsetDateTime.now());
        return Stream.generate(() -> new Money(history)).limit(amount).toList();
    }

}
