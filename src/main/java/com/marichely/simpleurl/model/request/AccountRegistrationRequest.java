package com.marichely.simpleurl.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AccountRegistrationRequest {
    @JsonProperty("AccountId")
    private String accountId;
}
