package com.ozdaigou.services;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCrawlerTest {

    @MockBean
    private HttpClientSession httpClientSession;

    @Value("${com.ozdaigou.crawler.baseUrl}")
    private String baseUrl ;

    @Value("${com.ozdaigou.crawler.loginUrl}")
    private String loginUrl ;

    @Value("${com.ozdaigou.crawler.userName}")
    private String userName ;

    @Value("${com.ozdaigou.crawler.passWord}")
    private String passWord ;

    @Value("${com.ozdaigou.crawler.productUrl}")
    private String productUrl ;

    private String searchUrl = "https://shop.healthday.com.au/brands/search/e585a8e983a8e59381e7898c/e5a9b4e5b9bce584bfe5a5b6e7b289/2";

    @Autowired
    private ProductCrawler productCrawler;

    @Before
    public void init() throws Exception {
        //productCrawler = mock(ProductCrawler.class);

//        when(productCrawler.login(loginUrl,userName,passWord)).thenReturn(void););
        //login function will do nothing
        Mockito.doNothing().when(httpClientSession).login(loginUrl,userName,passWord);

        //get the product page
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("productpage.html").getFile());
        String page = null;
        try {
            page = FileUtils.readFileToString(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Mockito.when(httpClientSession.fetchPageByGetRequest(productUrl)).thenReturn(page);

        //get the product page
        classLoader = getClass().getClassLoader();
        file = new File(classLoader.getResource("productinonecategory.html").getFile());
        page = null;
        try {
            page = FileUtils.readFileToString(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Mockito.when(httpClientSession.fetchPageByGetRequest(searchUrl)).thenReturn(page);

    }

    @Test
    public void crawlAndReturnLsitOfProducts() throws Exception {
        assertThat(productCrawler.getProducts().size()).isEqualTo(844);
    }

    @Test
    public void getCompleteUrlTest(){
        assertThat(productCrawler.getCompleteUrl("//ab.cd.ef.gn")).isEqualTo("ab.cd.ef.gn");
        assertThat(productCrawler.getCompleteUrl("/ab.cd.ef.gn")).isEqualTo(baseUrl+"/ab.cd.ef.gn");
    }

}
