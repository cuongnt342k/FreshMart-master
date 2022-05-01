package com.dt.ducthuygreen.Utils;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.dt.ducthuygreen.exception.UploadImageException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class UploadFile {
    private Cloudinary cloudinary;
	
	public Cloudinary config() {
        Map<String,String> config = new HashMap<>();
        config.put("cloud_name", "dlqdesqni");
        config.put("api_key", "355535952451761");
        config.put("api_secret", "8jymQqkrM5HHSZMtW-yIMcAPK78");
        return new Cloudinary(config);
    }

    public String getUrlFromFile(MultipartFile multipartFile) {
    	cloudinary = config();
        try {
            Map<?, ?> map = cloudinary.uploader().upload(multipartFile.getBytes(), ObjectUtils.emptyMap());
            return map.get("secure_url").toString();
        } catch (IOException e) {
            throw new UploadImageException("Upload image failed");
        }
    }

    public void removeImageFromUrl(String url) {
    	cloudinary = config();
        try {
            cloudinary.uploader().destroy(url, ObjectUtils.emptyMap());
        } catch (IOException e) {
            throw new UploadImageException("Upload image failed");
        }
    }

}
