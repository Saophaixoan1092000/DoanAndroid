package com.example.daltdd.Activity.model;

public class GuestModel {

    private boolean success;
    private String guest_session_id;
    private String expires_at;


    // Getter Methods

    public boolean getSuccess() {
        return success;
    }

    public String getGuest_session_id() {
        return guest_session_id;
    }

    public String getExpires_at() {
        return expires_at;
    }

    // Setter Methods

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setGuest_session_id(String guest_session_id) {
        this.guest_session_id = guest_session_id;
    }

    public void setExpires_at(String expires_at) {
        this.expires_at = expires_at;
    }
}
