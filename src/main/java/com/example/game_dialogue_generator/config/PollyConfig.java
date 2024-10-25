package com.example.game_dialogue_generator.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.polly.AmazonPolly;
import com.amazonaws.services.polly.AmazonPollyClientBuilder;

@Configuration
public class PollyConfig {

    @Value("${aws.polly.api.key}")
    private String pollyKey;

    @Value("${aws.polly.api.secret}")
    private String pollySecret;

    @Value("${aws.polly.api.region}")
    private String pollyRegion;

    @Bean
    public AmazonPolly Polly() {
        return AmazonPollyClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(
                        new BasicAWSCredentials(pollyKey, pollySecret)))
                .withRegion(pollyRegion).build();
    }
}

