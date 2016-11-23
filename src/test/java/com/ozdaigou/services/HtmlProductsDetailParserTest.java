package com.ozdaigou.services;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest

public class HtmlProductsDetailParserTest {

    @Autowired
    private HtmlProductDetailParser htmlProductDetailParser;

    @Test
    public void parsePageAndReturnThePrice () throws ParseException {

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("productdetailspage.html").getFile());
        String page = null;
        try {
            page = FileUtils.readFileToString(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        htmlProductDetailParser.parse(page);
        assertThat(htmlProductDetailParser.getPrice().toString()).isEqualTo("17.89");
        List<String> urls =  htmlProductDetailParser.getSlidePicUrls();
        assertThat(urls.size()).isEqualTo(1);
        assertThat(urls.get(0)).isEqualTo(
                "//cdn42.jinriaozhou.com/product/9300807299661/1bec62cad7b4c31e856864c0e6c6d407.jpg"
        );

        urls = htmlProductDetailParser.getDescritpionPicUrls();
        assertThat(urls.size()).isEqualTo(9);
        assertThat(urls.get(1)).isEqualTo(
                "https://img.alicdn.com/imgextra/i2/2165616006/TB2AKtWtXXXXXc6XpXXXXXXXXXX_!!2165616006.jpg"
        );


    }

    @Test
    public void parsePageAndReturnThePrice2 () throws ParseException {

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("productdetailspage2.html").getFile());
        String page = null;
        try {
            page = FileUtils.readFileToString(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        htmlProductDetailParser.parse(page);
        assertThat(htmlProductDetailParser.getPrice().toString()).isEqualTo("35");
        List<String> urls =  htmlProductDetailParser.getSlidePicUrls();
        assertThat(urls.size()).isEqualTo(3);
        assertThat(urls.get(2)).isEqualTo(
                "//cdn42.jinriaozhou.com/product/9421902960031/12a9809d9febeb87a622a09a20246869.jpg"
        );

        urls = htmlProductDetailParser.getDescritpionPicUrls();
        assertThat(urls.size()).isEqualTo(1);
//        assertThat(urls.get(0)).isEqualTo(
//                "//cdn42.jinriaozhou.com/product/9421902960031/12a9809d9febeb87a622a09a20246869.jpg"
//        );

    }

}
