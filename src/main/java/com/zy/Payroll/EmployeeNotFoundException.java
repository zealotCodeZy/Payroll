package com.zy.Payroll;

public class EmployeeNotFoundException extends RuntimeException{
    EmployeeNotFoundException(Long id){
        super("could not find employee "+id);
    }
}
