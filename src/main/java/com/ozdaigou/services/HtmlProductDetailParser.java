package com.ozdaigou.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

@Component
class HtmlProductDetailParser {

    private Document document;
    void parse(String detailsPage ){
        document = Jsoup.parse(detailsPage);
    }

    Number getPrice() throws ParseException {
        String priceStr = document.getElementsByClass("dg-main-product-core-panel-price-current").get(0).text();
        return NumberFormat.getCurrencyInstance(Locale.US).parse(priceStr);
    }

    List<String> getSlidePicUrls() {
        Element element = document.getElementsByClass("dg-main-product-core-slide-pic").get(0);
        Elements picElements =  element.getElementsByTag("img");
        List<String> picUrls = new LinkedList<>();
        for (Element picElement: picElements) {
            picUrls.add(picElement.attr("src"));
        }
        return picUrls;
    }

    List<String> getDescritpionPicUrls() {
//        Element element = document.getElementsByClass("dg-main-product-description").get(0);
        Elements picElements = document.getElementById("description").getElementsByTag("img");
//        Elements picElements =  element.getElementsByTag("img");
        List<String> picUrls = new LinkedList<>();
        for (Element picElement: picElements) {
            picUrls.add(picElement.attr("src"));
        }
        return picUrls;
    }


}
