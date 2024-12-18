package com.example.cartest.network;

public interface Callback<R> {
    void runResultOnUIThread(R result);
}
