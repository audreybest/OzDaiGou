package com.ozdaigou.services;


class ProductQueryInfo {
    String getSearchUrl() {
        return searchUrl;
    }

    void setSearchUrl(String searchUrl) {
        this.searchUrl = searchUrl;
    }

    String getDescription() {
        return description;
    }

    void setDescription(String description) {
        this.description = description;
    }

    public Integer getIndex() {
        return index;
    }

    void setIndex(Integer index) {
        this.index = index;
    }

    private Integer index;
    private String searchUrl;
    private String description;

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (!obj.getClass().equals(ProductQueryInfo.class))
        {
            return false;
        }
        ProductQueryInfo otherObj = (ProductQueryInfo)obj;
        return otherObj.getSearchUrl().equals(this.getSearchUrl());
    }
}
