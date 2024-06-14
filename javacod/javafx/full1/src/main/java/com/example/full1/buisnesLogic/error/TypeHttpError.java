package com.example.full1.buisnesLogic.error;

public class TypeHttpError extends Error {
    public TypeHttpError(byte name) {
        this.name = name;
    }

    final byte name;

    @Override
    public String getMessage() {
        return "Type " + name + " not valid";
    }
}
