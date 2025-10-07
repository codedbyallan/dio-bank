package br.com.dio.exception;

public class InvalidInvestmentException extends RuntimeException {

    public InvalidInvestmentException(String message) {
        super(message);
    }
}
