package com.tekhoufeha.userservice.service;


import com.tekhoufeha.userservice.dto.response.CloudinaryUploadResponse;
import org.springframework.web.multipart.MultipartFile;


public interface CloudinaryService {


    CloudinaryUploadResponse uploadImage(MultipartFile file);


    void deleteImage(String publicId);

}