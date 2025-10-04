package br.com.dio.repository;

import br.com.dio.exception.NoFundsEnoughException;
import br.com.dio.model.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static br.com.dio.model.BankService.*;
import static lombok.AccessLevel.*;

@NoArgsConstructor(access = PRIVATE)
public final class CommonsRepository {

    public static void checkFundsForTransaction(final Wallet source, final long amount) {
        if (source.getFunds() < amount) {
            throw new NoFundsEnoughException("Insufficient funds for the transaction.");
        }
    }

    public static List<Money> generateMoney(final UUID transactionId, final long funds, final String description) {
        var history = new MoneyAudit(transactionId, ACCOUNT, description, OffsetDateTime.now());
        return Stream.generate(() -> new Money(history)).limit(funds).toList();
    }
}
