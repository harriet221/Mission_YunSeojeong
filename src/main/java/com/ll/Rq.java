package com.ll;

import java.util.HashMap;
import java.util.Map;

public class Rq {
    private String cmd;
    private String action;
    private Map<String, String> paramMap;

    public Rq(String cmd) {
        paramMap = new HashMap<>();
        this.cmd = cmd;

        String[] cmdBits = cmd.split("\\?", 2);
        action = cmdBits[0].trim();

        if(cmdBits.length == 1) {
            return;
        }

        String query = cmdBits[1].trim();
        String[] queryBits = query.split("&");

        for(int i=0; i<queryBits.length; i++) {
            String[] paramStrBits = queryBits[i].split("=", 2);
            String paramKey = paramStrBits[0].trim();
            String paramValue = paramStrBits[1].trim();
            paramMap.put(paramKey, paramValue);
        }
    }

    public String getAction() {
        return action;
    }

    public int getParamId(String paramName, int defaultValue) {
        try {
            return Integer.parseInt(paramMap.get(paramName));
        } catch(NumberFormatException e) {
            return  defaultValue;
        }
    }
}
