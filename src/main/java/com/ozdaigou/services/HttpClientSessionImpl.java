package com.ozdaigou.services;

import com.ozdaigou.exceptions.GetProductPageFailException;
import com.ozdaigou.exceptions.LoginFailException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.URLEncoder;
import java.util.Base64;

/**
 * Created by pandysong on 16/11/17.
 * Refer to code on https://www.mkyong.com/java/apache-httpclient-examples/
 */
@Component
public class HttpClientSessionImpl implements HttpClientSession {

    @Autowired
    private HttpClient httpClient;

    @Autowired
    private ImageStorageService imageStorageService;

    public HttpClientSessionImpl() {
        //Cookier Manager
        CookieHandler.setDefault(new CookieManager());
    }

    @Override
    public void login(String url, String userName, String passWord)
            throws Exception {

        HttpPost request = new HttpPost(url);

        String charset = "UTF-8";

        setHeadersForLoginReqeust(request, charset);

        String loginInfo = String.format("%s=%s&%s=%s&%s=",
                URLEncoder.encode("myEmail", charset), URLEncoder.encode(userName, charset),
                URLEncoder.encode("myPassword", charset), URLEncoder.encode(passWord, charset),
                URLEncoder.encode("verifyCode", charset));

        HttpEntity entity = new StringEntity(loginInfo, "UTF-8");
        request.setEntity(entity);
        HttpResponse response = httpClient.execute(request);

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuilder result = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        JSONObject jsonObject = new JSONObject(result.toString());

        if (!jsonObject.getBoolean("success"))
            throw new LoginFailException(jsonObject.get("errorMessage").toString());

    }

    private void setHeadersForLoginReqeust(HttpPost request, String charset) {
        request.setHeader("Accept", "*/*");
        request.setHeader("Accept-Encoding","gzip, deflate, br");
        request.setHeader("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.6");
        request.setHeader("Connection","keep-alive");
        request.setHeader("Cache-Control","no-cache");
        request.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
        request.setHeader("Host", "shop.healthday.com.au");
        request.setHeader("Origin", "https://shop.healthday.com.au");
        request.setHeader("Pragma","no-cache");
        request.setHeader("Referer","https://shop.healthday.com.au/");
        request.setHeader("User-Agent","Mozilla/5.0 ");
        request.setHeader("Accept-Charset", charset);
        request.setHeader("X-Requested-With","XMLHttpRequest");
    }

    @Override
    public String fetchPageByGetRequest(String productUrl)
            throws Exception {
        HttpGet request = new HttpGet(productUrl);

        setHeadersForHttpGetRequest(request);


        HttpResponse response = httpClient.execute(request);

        if (response.getStatusLine().getStatusCode() != 200 ) {
            throw new GetProductPageFailException("Status Code "+ response.getStatusLine().getStatusCode());
        }

        return EntityUtils.toString(response.getEntity());

    }

    private void setHeadersForHttpGetRequest(HttpGet request) {
        request.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        request.setHeader("Accept-Encoding","gzip, deflate, br");
        request.setHeader("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.6");
        request.setHeader("Connection","keep-alive");
        request.setHeader("Cache-Control","no-cache");
        request.setHeader("Host", "shop.healthday.com.au");
        request.setHeader("Pragma","no-cache");
        request.setHeader("Referer","https://shop.healthday.com.au/");
        request.setHeader("Upgrade-Insecure-Requests","1");
        request.setHeader("User-Agent","Mozilla/5.0 ");
    }

    @Override
    public void fetchImage(String url,String fileToSave) throws IOException {
        //need to check if the url is embedded with the image.
        //Started with data:image/jpeg;base64,
        HttpGet request = new HttpGet(url);
        //setHeadersForHttpGetRequest(request);
        HttpResponse response = httpClient.execute(request);
        HttpEntity entity= response.getEntity();

        imageStorageService.saveToFile(entity,fileToSave);
    }



    }
}
