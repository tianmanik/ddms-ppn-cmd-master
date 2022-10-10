package com.eksadsupport.minilab.dto.response;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class ResponseBadRequest extends Response{

    public ResponseBadRequest() {
        super();
        setCode("400");
        setMessage("Process Failed");
        setStatus("F");
    }
}
