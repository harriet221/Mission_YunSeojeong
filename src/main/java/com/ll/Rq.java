package com.ll;

import java.util.HashMap;
import java.util.Map;

public class Rq {
    private String cmd;
    private String action;
    private Map<String, String> paramMap;

    Rq(String cmd) {
        paramMap = new HashMap<>();
        this.cmd = cmd;

        String[] cmdBits = cmd.split("\\?", 2);
        action = cmdBits[0].trim();

    }

    String getAction() {
        return action;
    }
}
