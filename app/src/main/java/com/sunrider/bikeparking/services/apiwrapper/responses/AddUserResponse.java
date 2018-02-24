package com.sunrider.bikeparking.services.apiwrapper.responses;


import com.sunrider.bikeparking.db.entities.User;

public class AddUserResponse extends BaseResponse {

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
