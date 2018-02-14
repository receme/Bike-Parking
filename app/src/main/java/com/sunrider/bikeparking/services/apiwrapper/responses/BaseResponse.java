package com.sunrider.bikeparking.services.apiwrapper.responses;


public class BaseResponse {

    private boolean success;
    private String errormsg;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrormsg() {
        return errormsg;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }
}
