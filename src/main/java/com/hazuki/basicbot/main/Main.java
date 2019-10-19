package com.hazuki.basicbot.main;

import com.hazuki.basicbot.Bot;

import javax.security.auth.login.LoginException;

public class Main {

    public static void main(String[] args) {
        try {
            Bot.getInstance().buildBot();
            Bot.getInstance().init();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }
}