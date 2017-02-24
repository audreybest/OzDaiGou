package com.ozdaigou.services;

import java.util.Base64;

/**
 * Created by pandysong on 16/11/24.
 */
public class UrlUtils {
    static boolean imgUrlWithDataEmbedded(String url) {
        String dataTag[] = url.split(":");
        if (dataTag.length > 1 && dataTag[0].equals("data") ) {
            //it is a "data" prefixed
            if (url.substring(5).startsWith("image/")) {
                //it is an image
                return true;
            }
        }

        return false;
    }

    static String imgUrlDataEmbeddedGetImgType(String url) {
        return url.substring(url.indexOf("/") + 1, url.indexOf(";"));
    }

    static byte[] imgUrlDataEmbeddedGetData(String url) {
        return Base64.getDecoder().decode(url.substring(url.indexOf(",")+1).getBytes());
    }

    public static boolean imgUrlWithExternalLink(String url) {
        return false;
    }
}
