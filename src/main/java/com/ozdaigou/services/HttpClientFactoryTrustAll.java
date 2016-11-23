package com.ozdaigou.services;

import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
class HttpClientFactoryTrustAll implements FactoryBean<HttpClient>{

    private static CloseableHttpClient client;

    @Override
    public HttpClient getObject() throws Exception {

        if (client != null) {
            return client;
        }
        SSLContext sslcontext = SSLContexts.custom().useSSL().build();
        sslcontext.init(null, new X509TrustManager[]{new HttpsTrustManager()}, new SecureRandom());
        SSLConnectionSocketFactory factory = new SSLConnectionSocketFactory(sslcontext,
                SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER
                /*SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER*/);
        client = HttpClients.custom().setSSLSocketFactory(factory).build();

        return client;
    }

    @Override
    public Class<HttpClient> getObjectType() {
        return HttpClient.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}