package com.example.game_dialogue_generator.service;

import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.polly.PollyClient;
import software.amazon.awssdk.services.polly.model.SynthesizeSpeechRequest;
import software.amazon.awssdk.services.polly.model.SynthesizeSpeechResponse;
import software.amazon.awssdk.services.polly.model.VoiceId;

import java.io.InputStream;

@Service
public class PollyService {

    private final PollyClient pollyClient;

    public PollyService(){
        this.pollyClient = PollyClient.builder()
                .region(Region.US_WEST_1)
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();
    }

    public PollyService(PollyClient pollyClient) {
        this.pollyClient = pollyClient;
    }

    public InputStream synthesizeSpeech(String text) {
        SynthesizeSpeechRequest request = SynthesizeSpeechRequest.builder()
                .text(text)
                .voiceId(VoiceId.KIMBERLY)
                .outputFormat("mp3")
                .build();

        ResponseInputStream<SynthesizeSpeechResponse> response = pollyClient.synthesizeSpeech(request);

        return response;
    }
}