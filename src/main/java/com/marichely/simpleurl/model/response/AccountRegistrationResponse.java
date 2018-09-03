package com.marichely.simpleurl.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AccountRegistrationResponse {
    private boolean success;
    private String description;
    private String password;
    private static final String OK_MESSAGE = "Your account is opened";

    public static AccountRegistrationResponse createOk(String pass){
        AccountRegistrationResponse base = createBase(OK_MESSAGE, pass);
        base.setSuccess(true);
        return base;
    }

    public static AccountRegistrationResponse createFail(String desc){
        AccountRegistrationResponse base = createBase(desc,null);
        base.setSuccess(false);
        return base;
    }

    private static AccountRegistrationResponse createBase(String description,String password){
        AccountRegistrationResponse accountRegistrationResponse = new AccountRegistrationResponse();
        accountRegistrationResponse.setDescription(description);
        accountRegistrationResponse.setPassword(password);
        return accountRegistrationResponse;
    }
}
