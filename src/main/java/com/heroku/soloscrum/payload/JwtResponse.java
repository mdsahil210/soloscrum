package com.heroku.soloscrum.payload;

public class JwtResponse {
    private boolean success;
    private String token;

    public JwtResponse(boolean success, String token) {
        this.success = success;
        this.token = token;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "JwtResponse{" +
                "success=" + success +
                ", token='" + token + '\'' +
                '}';
    }
}
