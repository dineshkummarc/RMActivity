package com.tmobile.reallyme.core.api.remote.pojo.tmo;

/**
 * dku
 */
public class TMOAuthenticationError {
    private String id;
    private String detail;
    private String message;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
