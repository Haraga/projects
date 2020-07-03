package com.example.ubbapp.structures;

/**
 * The SimpleCallBack interface helps out to return values from
 * the inside of objects
 *
 * @param <T> Any kind of Object which will be returned
 */
public interface SimpleCallBack<T> {
    void callback(T data);
}
