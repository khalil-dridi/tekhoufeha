package com.tekhoufeha.userservice.service.impl;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import com.tekhoufeha.userservice.exception.CloudinaryUploadException;
import com.tekhoufeha.userservice.exception.InvalidImageException;
import com.tekhoufeha.userservice.service.CloudinaryService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class CloudinaryServiceImpl implements CloudinaryService {


    private final Cloudinary cloudinary;



    @Override
    public String uploadImage(MultipartFile file) {


        validateImage(file);


        try {

            Map uploadResult =
                    cloudinary.uploader()
                            .upload(
                                    file.getBytes(),
                                    ObjectUtils.emptyMap()
                            );


            return uploadResult
                    .get("secure_url")
                    .toString();


        } catch (IOException e) {

            throw new CloudinaryUploadException(
                    "Failed to upload image to Cloudinary",
                    e
            );
        }
    }





    @Override
    public void deleteImage(String publicId) {


        if (publicId == null || publicId.isBlank()) {
            return;
        }


        try {

            cloudinary.uploader()
                    .destroy(
                            publicId,
                            ObjectUtils.emptyMap()
                    );


        } catch (IOException e) {

            throw new CloudinaryUploadException(
                    "Failed to delete image from Cloudinary",
                    e
            );
        }
    }





    @Override
    public String extractPublicId(String imageUrl) {


        if (imageUrl == null || imageUrl.isBlank()) {
            return null;
        }


        /*
         Exemple:
         https://res.cloudinary.com/dqrtwfpbq/image/upload/v1785420725/photo123.jpg

         résultat:
         photo123
        */


        String[] parts = imageUrl.split("/");


        String fileName =
                parts[parts.length - 1];


        return fileName.substring(
                0,
                fileName.lastIndexOf('.')
        );
    }





    private void validateImage(MultipartFile file) {


        if (file == null || file.isEmpty()) {

            throw new InvalidImageException(
                    "Image file is required"
            );
        }



        if (file.getContentType() == null ||
                !file.getContentType().startsWith("image/")) {


            throw new InvalidImageException(
                    "Only image files are allowed"
            );
        }

    }

}