package com.ozdaigou.services;

import org.apache.http.HttpEntity;

/**
 * Created by pandysong on 16/11/24.
 */
public interface ImageStorageService {
    public void storeImage(byte[] data, String fileName);
    public void storeImage(HttpEntity entity, String fileName);
}
