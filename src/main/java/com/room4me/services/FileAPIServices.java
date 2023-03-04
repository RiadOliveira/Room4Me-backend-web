package com.room4me.services;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.room4me.errors.ServerException;

@Service
public class FileAPIServices {
    @Autowired
    private Environment environment;

    private final String API_ACCESS_URL = "https://i.ibb.co/";
    private final String API_UPLOAD_URL = "https://api.imgbb.com/1/upload?key=";

    private static class APIResponse {
        public Data data;

        private static class Data {
            public String display_url;
        }
    }

    public String sendFile(MultipartFile file) {
        try {
            String fileBase64 = Base64.getEncoder().encodeToString(file.getBytes());
            String parsedUrl = API_UPLOAD_URL + environment.getProperty("file.api.key");
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
    
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("image", fileBase64);
    
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
    
            RestTemplate restTemplate = new RestTemplate();
            APIResponse response = restTemplate.postForEntity(
                parsedUrl, requestEntity,
                APIResponse.class
            ).getBody();
    
            return response.data.display_url;
        } catch (Exception exception) {
            throw ServerException.INTERNAL_SERVER_EXCEPTION;
        }
    }

    public String getUniqueLinkPart(String link) {
        if(link == null) return null;
        return link.split(API_ACCESS_URL)[1];
    }

    public String getFullLinkFromUniquePart(String uniquePart) {
        if(uniquePart == null) return null;
        return API_ACCESS_URL + uniquePart;
    }
}