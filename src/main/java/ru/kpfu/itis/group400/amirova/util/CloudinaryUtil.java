package ru.kpfu.itis.group400.amirova.util;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

public class CloudinaryUtil {

    private static Cloudinary cloudinary;

    public static Cloudinary getInstance() {
        if (cloudinary == null) {
            String cloudName = System.getenv("CLOUDINARY_CLOUD_NAME");
            if (cloudName == null) cloudName = ConfigUtil.getProperty("cloudinary.cloud_name");

            String apiKey = System.getenv("CLOUDINARY_API_KEY");
            if (apiKey == null) apiKey = ConfigUtil.getProperty("cloudinary.api_key");

            String apiSecret = System.getenv("CLOUDINARY_API_SECRET");
            if (apiSecret == null) apiSecret = ConfigUtil.getProperty("cloudinary.api_secret");

            cloudinary = new Cloudinary(ObjectUtils.asMap(
                    "cloud_name", cloudName,
                    "api_key", apiKey,
                    "api_secret", apiSecret
            ));
        }
        return cloudinary;
    }
}