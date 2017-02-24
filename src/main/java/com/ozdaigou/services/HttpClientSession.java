package com.ozdaigou.services;

import org.apache.http.client.HttpClient;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
interface HttpClientSession {
    void login(String url, String userName, String passWord)  throws Exception ;
    String fetchPageByGetRequest(String productUrl) throws Exception ;
    void fetchImage(String urlstr, String url) throws IOException;
}
