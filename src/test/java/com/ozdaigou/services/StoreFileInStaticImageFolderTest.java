package com.ozdaigou.services;

import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class StoreFileInStaticImageFolderTest {
    @Test
    public void storeFileAndRetrieve() throws IOException {


        ClassLoader classLoader = getClass().getClassLoader();
        String fileName = classLoader.getResource(".")+"images/test.txt";
//        String rootPath = System.getProperty("user.dir");
//        System.out.println(rootPath);
//        String fileName = rootPath +"/src/main/resources/images" +"/test.txt";

        System.out.println(fileName);
        File file = new File(fileName);
        if (!file.exists())
            file.createNewFile();
        FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
        fileWriter.append("test");
        fileWriter.close();
    }
}
