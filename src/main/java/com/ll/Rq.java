package com.ll;

public class Rq {
    String cmd;
    String action;

    Rq(String cmd) {
        this.cmd = cmd;

        String[] cmdBits = cmd.split("\\?", 2);
        action = cmdBits[0].trim();
    }
}
