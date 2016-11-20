package com.ozdaigou.domain;

import java.util.List;

import org.springframework.data.annotation.Id;

/**
 * Created by Audrey on 20/11/16.
 */
public class Product {

    @Id
    public String id;

    public String enTitle;
    public String cnTitle;
    public String decs;
    public List<String> imageUrls;
    public String category;
    public String brand;
    public Double price;
    public Long amount;
    public Double freight;

    public String getEnTitle() {
        return enTitle;
    }

    public void setEnTitle(String enTitle) {
        this.enTitle = enTitle;
    }

    public String getCnTitle() {
        return cnTitle;
    }

    public void setCnTitle(String cnTitle) {
        this.cnTitle = cnTitle;
    }

    public String getDecs() {
        return decs;
    }

    public void setDecs(String decs) {
        this.decs = decs;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Double getFreight() {
        return freight;
    }

    public void setFreight(Double freight) {
        this.freight = freight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        return new org.apache.commons.lang3.builder.EqualsBuilder()
                .append(id, product.id)
                .append(enTitle, product.enTitle)
                .append(cnTitle, product.cnTitle)
                .append(decs, product.decs)
                .append(imageUrls, product.imageUrls)
                .append(category, product.category)
                .append(brand, product.brand)
                .append(price, product.price)
                .append(amount, product.amount)
                .append(freight, product.freight)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new org.apache.commons.lang3.builder.HashCodeBuilder(17, 37)
                .append(id)
                .append(enTitle)
                .append(cnTitle)
                .append(decs)
                .append(imageUrls)
                .append(category)
                .append(brand)
                .append(price)
                .append(amount)
                .append(freight)
                .toHashCode();
    }
}
