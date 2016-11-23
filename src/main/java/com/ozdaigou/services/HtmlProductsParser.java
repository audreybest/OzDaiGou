package com.ozdaigou.services;

import com.ozdaigou.entities.Product;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Component
class HtmlProductsParser {
    /*
* this will update ProductCategories and ProductBrands, as well as  Products
* */

    private Document document;

    List<Product> getProducts() {

        /*
        * Search for all products
        * */

        //get dg-main-bulk-list by id
        //then find the tables, and return the first table (since it only has one table
        Element tableElement = document.getElementById("dg-main-bulk-list").getElementsByTag("table").get(0);
        Elements rows = tableElement.getElementsByTag("tr");

//        LOGGER.log(Level.INFO, "Total products: " + rows.size());

        List<Product> products = new LinkedList<>();

//        int rowNum = 0;

        for (Element row: rows) {

            //First column is the small image, as well as product link

            Product product = new Product();

            //<td width="14%">
            //<a href="/products/healthy-lungs-200ml">
            //<img width="52" src="/....humb.jpg">
            //</a>
            //</td>
            Element col1 = row.getElementsByTag("td").get(0);

            product.setProductUrl(col1.getElementsByTag("a").get(0).attr("href"));
            product.setImageUrl(col1.getElementsByTag("a").get(0).getElementsByTag("img").attr("src"));


            //<td width="68%">
            // <a href="/products/healthy-lungs-200ml">好健康肺康Healthy Lungs液 </a>
            // <br />
            // <a href="/products/healthy-lungs-200ml">Healthy Lungs 200ml </a>
            // </td>
            Element col2 = row.getElementsByTag("td").get(1);
            Elements elementsA = col2.getElementsByTag("a");

            product.setDescriptionChinese(elementsA.get(0).text());
            product.setDescription(elementsA.get(1).text());

            products.add(product);

//            LOGGER.log(Level.INFO, product.toString());
//            rowNum ++;
//            if (rowNum > 3)
//                break;
        }

        return products;

    }


    List<ProductQueryInfo> getBrands(){

        /*
        * Search for all brands
        * */
        Element brandElement = document.getElementsByClass("dg-main-bulk-brand").get(0);
        Elements rows = brandElement.getElementsByTag("a");

        List<ProductQueryInfo>productBrands = new LinkedList<>();

        Integer count= 0;
        for (Element row:rows) {

            String href = row.attr("href");
            if (href.startsWith("/brands/search")) { // this is to filter out all and other non relavant link
                ProductQueryInfo productBrand = new ProductQueryInfo();
                productBrand.setDescription(row.text());
                productBrand.setSearchUrl(href);
                productBrand.setIndex(count);
                count ++;
                productBrands.add(productBrand);
            }
        }

        //Sort it
        Collections.sort(productBrands,(ProductQueryInfo o1, ProductQueryInfo o2) ->
                o1.getSearchUrl().compareTo(o2.getSearchUrl()) );
//        Collections.sort(productBrands, new Comparator<ProductQueryInfo>() {
//            @Override
//            public int compare(ProductQueryInfo o1, ProductQueryInfo o2) {
//                return o1.getSearchUrl().compareTo(o2.getSearchUrl());
//            }
//        });


        //Remove duplicated items
        productBrands = productBrands.parallelStream().distinct().collect(Collectors.toList());

        return productBrands;

    }

    List<ProductQueryInfo> getCategories() {
                /*
        * Search for all categories
        * */
        Element catElement = document.getElementsByClass("dg-main-bulk-series").get(0);
        Elements rows = catElement.getElementsByTag("a");

        List<ProductQueryInfo> productCategories = new LinkedList<>();

        Boolean flagFirstOne = true;
        for (Element row:rows) {
            if (flagFirstOne) {
                //Skip the first category which is "All"
                flagFirstOne = false;
                continue;
            }

            String href = row.attr("href");

            if (href.startsWith("/brands/search")) { // this is to filter out all and other non relavant link
                ProductQueryInfo productCat = new ProductQueryInfo();
                productCat.setDescription(row.text());
                productCat.setSearchUrl(href);
                productCategories.add(productCat);
            }
        }

        return productCategories;
    }

    void parse(String productPage) {
        document = Jsoup.parse(productPage);
    }
}
