package com.ozdaigou.services;

import org.apache.http.HttpEntity;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class ImageStorageServiceImpl implements ImageStorageService {

    private String filePath;

    public ImageStorageServiceImpl() {

        String rootPath = System.getProperty("user.dir");
        //// TODO: 16/11/24  change it to production code
        filePath= rootPath +"/src/main/resources/images/";

    }

    public void storeImage(byte[] data, String fileName)
    {
        try (OutputStream stream = new FileOutputStream(filePath + fileName)) {
            stream.write(data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void storeImage(HttpEntity entity, String fileName){
        File file = new File(fileName);
        try (FileOutputStream outstream = new FileOutputStream(file)) {
            entity.writeTo(outstream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
