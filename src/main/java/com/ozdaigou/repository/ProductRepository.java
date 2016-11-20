package com.ozdaigou.repository;

import java.util.List;

import com.ozdaigou.domain.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by Audrey on 20/11/16.
 */
public interface ProductRepository extends MongoRepository<Product, String> {

    Product findByEnTitle(String enTitle);
    Product findByCnTitle(String CnTitle);
    List<Product> findByCategory(String category);
    List<Product> findByBrand(String brand);
}
