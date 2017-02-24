package com.ozdaigou.services;

import com.ozdaigou.entities.Product;
import com.ozdaigou.exceptions.GetProductPageFailException;
import com.ozdaigou.exceptions.LoginFailException;
import com.sun.deploy.util.URLUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
class ProductCrawler {

    private static final Logger log = Logger.getLogger("ProductCrawler");

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

    @Autowired
    private HttpClientSession httpClientSession;

    List<Product> getProducts() {

        try {



//            LOGGER.log(Level.INFO, "trying to login");

            httpClientSession.login(loginUrl, userName, passWord);

//            LOGGER.log(Level.INFO, "Login Success");
//            LOGGER.log(Level.INFO, "Fetching Product Page:");

            String productPage = httpClientSession.fetchPageByGetRequest(productUrl);

//            LOGGER.log(Level.INFO, "Got a Product Page");
//            LOGGER.log(Level.INFO, "Parsing the Pages");

            //String productPage = new String(Files.readAllBytes(Paths.get("./temp/productpage.html")));

            HtmlProductsParser htmlProductsParser = new HtmlProductsParser();

            htmlProductsParser.parse(productPage);

            //Get All Products
            List<Product> products = htmlProductsParser.getProducts();

            fetchAndFillCategoryInfoToProducts(products, htmlProductsParser.getCategories()) ;
            fetchAndFillBrandsInfoToProducts(products, htmlProductsParser.getBrands());

            fetchProductsDetailedInfo(products);

            fetchProductsImages(products);


            return products;

        }
        catch (LoginFailException e) {
            log.log(Level.WARNING,"Login Fail, with message:" + e.getMessage());
        }
        catch (GetProductPageFailException e) {
            log.log(Level.WARNING,"Get Product Page Fail" + e.getMessage());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    private void fetchProductsImages(List<Product> products) throws IOException {
        for (Product product: products) {
            List<String> slidePicUrls = product.getSlidePicUrls();

            for (String urlstr:slidePicUrls) {
                URL url = new URL(urlstr);
                httpClientSession.fetchImage(urlstr,url.getFile());
            }

        }
    }

    private void fetchProductsDetailedInfo(List<Product> products) throws Exception {
        for (Product product:products) {
            String url = getCompleteUrl(product.getProductUrl());

            String detailsPage = httpClientSession.fetchPageByGetRequest(url);
            HtmlProductDetailParser htmlProductDetailParser = new HtmlProductDetailParser();
            product.setSlidePicUrls(htmlProductDetailParser.getSlidePicUrls());
            product.setDescriptionPicUrls(htmlProductDetailParser.getDescritpionPicUrls());
        }
    }

    private void fetchAndFillBrandsInfoToProducts(List<Product> products, List<ProductQueryInfo> productBrands) throws Exception {
        int count = 0;

        for (ProductQueryInfo productBrand:productBrands) {

            String productPage = httpClientSession.fetchPageByGetRequest(getCompleteUrl(productBrand.getSearchUrl()));

            if (productPage == null) {
                log.log(Level.WARNING,"Get URL:"+
                        baseUrl +
                        productBrand.getSearchUrl() +
                        " Fail"
                );
                continue;
            }

            log.log(Level.INFO, "Search Products for Brands: "+productBrand.getDescription());

            HtmlProductsParser htmlProductsParser = new HtmlProductsParser();

            htmlProductsParser.parse(productPage);//parse the full page

            String brandDescription = productBrand.getDescription();
            String[] description = StringUtils.split(brandDescription,"//");
            String brandDescriptionChinese = null;

            if (description != null) {

                brandDescription = description[0];
                if (description.length > 1) {
                    brandDescriptionChinese = description[1];
                }
            }

            StringUtils.trimAllWhitespace(brandDescription);

            if (brandDescriptionChinese != null)
                StringUtils.trimAllWhitespace(brandDescriptionChinese);

            List<Product> productsWithThisBrand= htmlProductsParser.getProducts();

            log.log(Level.INFO, productsWithThisBrand.size() + " Products found ");

            for (Product product: productsWithThisBrand) {
                //Search in the all products and update the category information
                Optional<Product> productInAllList =
                        products
                                .stream()
                                .filter(e -> e.getProductUrl().equals(product.getProductUrl()) )
                                .findFirst();
                if (productInAllList.isPresent()) {
                    productInAllList.get().setBrand(brandDescription);
                    productInAllList.get().setBrandChinese(brandDescriptionChinese);
                    count ++;
                }
            }

//                break;
        }

        log.log(Level.INFO, "Brands of "+  count + " Products updated, in Total " +
                products.size() + " Products");
    }

    protected String getCompleteUrl(String url) {
        if (url.startsWith("//")) {
            return url.substring(2);
        } else if (url.startsWith("/") )
            return baseUrl + url;
        return url;
    }

    private void fetchAndFillCategoryInfoToProducts(List<Product> products, List<ProductQueryInfo> productCategories) throws Exception {
        int count = 0;

        for (ProductQueryInfo productCategory:productCategories ) {

            String productPage = httpClientSession.fetchPageByGetRequest(getCompleteUrl(productCategory.getSearchUrl()));

            if (productPage == null) {
                log.log(Level.WARNING,"Get URL:"+
                        baseUrl +
                        productCategory.getSearchUrl() +
                        " Fail"
                );
                continue;
            }

            log.log(Level.INFO, "Search Products for Catetory: "+productCategory.getDescription());

            HtmlProductsParser htmlProductsParser = new HtmlProductsParser();

            htmlProductsParser.parse(productPage);//parse the full page

            String categoryDescription = productCategory.getDescription();

            List<Product> productsWithThisCategory= htmlProductsParser.getProducts();

            log.log(Level.INFO, productsWithThisCategory.size() + " Products found ");

            for (Product product: productsWithThisCategory) {
                //Search in the all products and update the category information
                Optional<Product> productInAllList =
                        products
                                .stream()
                                .filter(e -> e.getProductUrl().equals(product.getProductUrl()) )
                                .findFirst();
                if (productInAllList.isPresent()) {
                    productInAllList.get().setCategory(categoryDescription);
                    count ++;
                }

            }
//                break;
        }

        log.log(Level.INFO, "Categories of "+  count + " Products updated, in Total " +
                products.size() + " Products");

    }

}
