package com.ozdaigou.repository;

import java.util.List;

import com.ozdaigou.domain.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Audrey on 20/11/16.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    Product bFishOil, sCalc, a2;

    @Before
    public void setUp() throws Exception {
        productRepository.deleteAll();

        bFishOil = new Product();
        bFishOil.setBrand("Blackmores");
        bFishOil.setCnTitle("鱼油");
        bFishOil.setEnTitle("fish oil");
        bFishOil.setCategory("vitamin");

        sCalc = new Product();
        sCalc.setBrand("Swiss");
        sCalc.setPrice(20.0);
        sCalc.setAmount(100l);
        sCalc.setCategory("vitamin");

        a2 = new Product();
        a2.setCategory("formula");
        a2.setFreight(70.0);
        a2.setPrice(200.0);

        productRepository.save(bFishOil);
        productRepository.save(sCalc);
        productRepository.save(a2);

    }

    @Test
    public void testSetIdOnSave() throws Exception {
        Product p = productRepository.save(bFishOil);
        assertThat(p.id).isNotNull();
    }

    @Test
    public void testFindByEnTitle() throws Exception {
        Product p = productRepository.findByEnTitle("fish oil");
        assertThat(p).isEqualTo(bFishOil);
    }

    @Test
    public void testFindByCnTitle() throws Exception {
        Product p = productRepository.findByCnTitle("鱼油");
        assertThat(p).isEqualTo(bFishOil);
    }

    @Test
    public void testFindByCategory() throws Exception {
        List<Product> productList = productRepository.findByCategory("vitamin");
        assertThat(productList).size().isEqualTo(2);
        productList = productRepository.findByCategory("formula");
        assertThat(productList).size().isEqualTo(1);

    }

    @Test
    public void testFindByBrand() throws Exception {
        List<Product> productList = productRepository.findByBrand("Swiss");
        assertThat(productList).size().isEqualTo(1);
    }
}