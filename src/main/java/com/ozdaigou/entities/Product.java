package com.ozdaigou.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

import static sun.rmi.transport.TransportConstants.Version;

//@Entity
public class Product {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

//    @Version
    private Integer version;

    private String productId;

    public String getProductUrl() {
        return productUrl;
    }

    public String getDescriptionChinese() {
        return descriptionChinese;
    }

    private String productUrl;
    private String description;
    private String descriptionChinese;
    private String imageUrl;

    public List<String> getSlidePicUrls() {
        return slidePicUrls;
    }

    public void setSlidePicUrls(List<String> slidePicUrls) {
        this.slidePicUrls = slidePicUrls;
    }

    public List<String> getDescriptionPicUrls() {
        return descriptionPicUrls;
    }

    public void setDescriptionPicUrls(List<String> descriptionPicUrls) {
        this.descriptionPicUrls = descriptionPicUrls;
    }

    private List<String> slidePicUrls;
    private List<String> descriptionPicUrls;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    private String category;
    private String brand;

    public String getBrandChinese() {
        return brandChinese;
    }

    public void setBrandChinese(String brandChinese) {
        this.brandChinese = brandChinese;
    }

    private String brandChinese;

    private BigDecimal price;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public void setDescriptionChinese(String descriptionChinese) {
        this.descriptionChinese = descriptionChinese;
    }
}
