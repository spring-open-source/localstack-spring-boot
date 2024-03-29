package com.hardik.killchamber.storage;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.hardik.killchamber.storage.bean.AmazonS3Bean;
import com.hardik.killchamber.storage.configuration.AmazonS3Configuration;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
@EnableConfigurationProperties(AmazonS3Configuration.class)
public class StorageService {

	private final AmazonS3Bean amazonS3Bean;

	private final AmazonS3Configuration amazonS3Configuration;

	public void save(String key, MultipartFile multipartFile) {
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(multipartFile.getSize());
		try {
			amazonS3Bean.amazonS3().putObject(amazonS3Configuration.getStorage().getBucketName(), key,
					multipartFile.getInputStream(), null);
		} catch (SdkClientException | IOException e) {
			log.error("UNABLE TO STORE IMAGE IN S3 FOR " + key + " " + LocalDateTime.now());
		}
	}

	public S3Object getFile(String key) {
		return amazonS3Bean.amazonS3().getObject(amazonS3Configuration.getStorage().getBucketName(), key);
	}

	public void deleteFile(String key) {
		amazonS3Bean.amazonS3().deleteObject(amazonS3Configuration.getStorage().getBucketName(), key);
	}

}
