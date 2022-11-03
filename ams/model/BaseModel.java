package com.ams.model;

public class BaseModel {
    public String ResponseCode;
    public String reserved;
    public String operationCode;


    public String getOperationCode() {
        return this.operationCode;

    }


    public void setOperationCode(String operationCode) {
        this.operationCode = operationCode;

    }


    public String getResponse_code() {
        return this.ResponseCode;

    }


    public void setResponse_code(String ResponseCode) {
        this.ResponseCode = ResponseCode;

    }


    public String getReserved() {
        return this.reserved;

    }


    public void setReserved(String reserved) {
        this.reserved = reserved;

    }

}