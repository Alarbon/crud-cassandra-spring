package com.example.crudcassandraproducts.responses;


public class ErrorResponse {


    private int status;
    private String error;


    public ErrorResponse(int status, String message) {
        this.status = status;
        this.error = message;

    }


}