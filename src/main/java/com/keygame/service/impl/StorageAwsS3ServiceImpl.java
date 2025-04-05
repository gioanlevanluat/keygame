//package com.keygame.service.impl;
//
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.model.CannedAccessControlList;
//import com.amazonaws.services.s3.model.ObjectMetadata;
//import com.amazonaws.services.s3.model.PutObjectRequest;
//import com.keygame.service.StorageAwsS3Service;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//
//@Service
//public class StorageAwsS3ServiceImpl implements StorageAwsS3Service {
//
//    Logger logger = LoggerFactory.getLogger(StorageAwsS3ServiceImpl.class);
//    @Autowired
//    AmazonS3 amazonS3;
//
//    @Override
//    public String uploadFile(String bucketName, String keyName, File file, boolean isPublic) {
//        if (!amazonS3.doesBucketExistV2(bucketName)) {
//            amazonS3.createBucket(bucketName);
//        }
//
//        try {
//            ObjectMetadata metadata = new ObjectMetadata();
//            metadata.setContentLength(file.length());
//            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, keyName, new FileInputStream(file), metadata);
//            if (isPublic) {
//                putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead);
//            } else {
//                putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead);
//            }
//            amazonS3.putObject(putObjectRequest);
//            return amazonS3.getObjectAsString(bucketName, keyName);
//        } catch (IOException ioe) {
//            logger.error("Upload file s3 error : ", ioe);
//        }
//        return null;
//    }
//}
