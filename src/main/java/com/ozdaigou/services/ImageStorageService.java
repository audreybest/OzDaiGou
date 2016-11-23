package com.ozdaigou.services;

import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

@Component
public class ImageStorageService {


    public ImageStorageService() {

        String rootPath = System.getProperty("user.dir");
        System.out.println(rootPath);
        String filePath= rootPath +"/src/main/resources/images/";

    }


}
