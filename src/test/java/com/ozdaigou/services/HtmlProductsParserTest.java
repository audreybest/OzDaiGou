package com.ozdaigou.services;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest

public class HtmlProductsParserTest {

    @Autowired
    private HtmlProductsParser htmlProductsParser;

    @Test
    public void parsePageAndReturnCorrectNumberOfProducts ()
    {

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("productpage.html").getFile());
        String page = null;
        try {
            page = FileUtils.readFileToString(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        htmlProductsParser.parse(page);
        assertThat(htmlProductsParser.getProducts().size()).isEqualTo(844);
        assertThat(htmlProductsParser.getBrands().size()).isEqualTo(105);
        assertThat(htmlProductsParser.getCategories().size()).isEqualTo(19);
    }

    @Test (expected = IllegalArgumentException.class)
    public void parseNullPage ()
    {
        htmlProductsParser.parse(null);
    }


}
