package com.eksadsupport.minilab.dto.response;

import java.util.ArrayList;

public class ResponseUnauthorized extends Response{

    public ResponseUnauthorized() {
        super();
        setCode("401");
        setMessage("Process Fail");
        setStatus("F");
        setData(new ArrayList<>());
    }
}
