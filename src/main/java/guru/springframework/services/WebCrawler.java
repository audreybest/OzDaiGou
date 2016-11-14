package guru.springframework.services;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

public class WebCrawler {


    public static void main(String[] args) {

        String baseUri = "https://www.healthday.com.au";
//        String baseUri = "https://shop.healthday.com.au";
//        String baseUri = "https://www.google.com.au";

        HttpClient httpclient = HttpClientBuilder.create().build();
        HttpGet httpget = new HttpGet(baseUri);

        try {
            HttpResponse httpResponse =  httpclient.execute(httpget);

            //this.handleContentEncoding(httpResponse);
//
//            HttpEntity httpEntity = httpResponse.getEntity();
//            Document document = Jsoup.parse(httpEntity.getContent(),
//                    httpEntity.getContentEncoding().getName(),
//                    baseUri
//                    );

//            System.out.println(.getContent().toString());
//            System.out.println(httpResponse.getAllHeaders().toString());
            System.out.println(httpResponse.getStatusLine().getStatusCode());
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpget.releaseConnection();
        }
    }

}

