package com.cwi.managed_data.roles.contract;

public class ContractBrokenException extends Error {

    public ContractBrokenException(String message) {
        super(message);
    }

    public ContractBrokenException(String message, Throwable throwable) {
        super(message, throwable);
    }

}