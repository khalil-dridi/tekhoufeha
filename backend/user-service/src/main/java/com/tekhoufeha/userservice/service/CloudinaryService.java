package com.tekhoufeha.userservice.service;


import org.springframework.web.multipart.MultipartFile;


public interface CloudinaryService {


    /**
     * Upload image to Cloudinary
     *
     * @param file image file
     * @return secure URL of uploaded image
     */
    String uploadImage(MultipartFile file);



    /**
     * Delete image from Cloudinary
     *
     * @param publicId Cloudinary public identifier
     */
    void deleteImage(String publicId);



    /**
     * Extract Cloudinary publicId from image URL
     *
     * @param imageUrl Cloudinary image URL
     * @return publicId
     */
    String extractPublicId(String imageUrl);

}