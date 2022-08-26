package com.zy.Payroll;

public class OrderNotFoundException extends RuntimeException {
    OrderNotFoundException(Long id) {
        super("could not find employee " + id);
    }
}
