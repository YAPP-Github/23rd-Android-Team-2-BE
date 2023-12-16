package com.moneymong.global.config.cloud;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NcpConfig {
	@Value("${cloud.ncp.credentials.access-key}")
	private String accessKey;

	@Value("${cloud.ncp.credentials.secret-key}")
	private String secretKey;

	@Value("${cloud.ncp.region.static}")
	private String region;

	@Value("${cloud.ncp.s3.endpoint}")
	private String endpoint;

	@Bean
	public AmazonS3 amazonS3() {
		AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
		return AmazonS3ClientBuilder.standard()
			.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpoint, region))
			.withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
			.build();
	}
}
