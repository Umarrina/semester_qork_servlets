package ru.kpfu.itis.group400.amirova.util;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

public class CloudinaryUtil {

    private static Cloudinary cloudinary;

    public static Cloudinary getInstance(){
        if (cloudinary == null){
            cloudinary = new Cloudinary(ObjectUtils.asMap(
                    "cloud_name", ConfigUtil.getProperty("cloudinary.cloud_name"),
                    "api_key", ConfigUtil.getProperty("cloudinary.api_key"),
                    "api_secret", ConfigUtil.getProperty("cloudinary.api_secret")
            ));
        }
        return cloudinary;
    }
}
