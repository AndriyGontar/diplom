package com.example.full1.buisnesLogic.error;

public class SettingHttpError extends Error{
    @Override
    public String getMessage() {
        return "this setting not valid";
    }
}
