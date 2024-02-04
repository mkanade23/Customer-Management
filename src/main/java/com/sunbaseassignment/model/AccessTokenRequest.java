package com.sunbaseassignment.model;

import lombok.Data;

@Data
public class AccessTokenRequest {

    private String login_id;
    private String password;

    public AccessTokenRequest(String login_id, String password) {
        this.login_id = login_id;
        this.password = password;
    }
}
