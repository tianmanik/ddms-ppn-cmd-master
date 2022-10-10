package com.eksadsupport.minilab.dto.response;

import com.eksadsupport.minilab.dto.ppn.ResPostPpn;

public class ResponseSavePpn {
    private int code;
    private ResPostPpn data;
    private String message;
    private String status;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ResPostPpn getData() {
        return data;
    }

    public void setData(ResPostPpn data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
