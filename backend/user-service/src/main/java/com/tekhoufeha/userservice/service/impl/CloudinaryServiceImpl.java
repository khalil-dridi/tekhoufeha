package com.tekhoufeha.userservice.service.impl;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import com.tekhoufeha.userservice.dto.response.CloudinaryUploadResponse;
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
    public CloudinaryUploadResponse uploadImage(
            MultipartFile file
    ) {


        validateImage(file);


        try {


            Map uploadResult =
                    cloudinary.uploader()
                            .upload(
                                    file.getBytes(),
                                    ObjectUtils.emptyMap()
                            );



            return new CloudinaryUploadResponse(

                    uploadResult
                            .get("secure_url")
                            .toString(),

                    uploadResult
                            .get("public_id")
                            .toString()

            );



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