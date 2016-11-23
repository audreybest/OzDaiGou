package com.ozdaigou.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import com.ozdaigou.entities.Product;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCrawlerIT {

    @Autowired
    private ProductCrawler productCrawler;


    @Test
    public void crawlRealWebAndCheckTheContent() {
        List<Product> products = productCrawler.getProducts();
        assertThat(products.size()).isEqualTo(844);
    }

}
