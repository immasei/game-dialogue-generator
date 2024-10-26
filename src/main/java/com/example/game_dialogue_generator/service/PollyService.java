package com.example.game_dialogue_generator.service;

import com.amazonaws.services.polly.AmazonPolly;
import com.amazonaws.services.polly.model.OutputFormat;
import com.amazonaws.services.polly.model.SynthesizeSpeechRequest;
import com.amazonaws.services.polly.model.SynthesizeSpeechResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.amazonaws.services.polly.model.VoiceId;

import java.io.InputStream;

@Service
public class PollyService {
    @Autowired
    private AmazonPolly polly;

    public InputStream synthesizeSpeech(String text) {
        SynthesizeSpeechRequest request = new SynthesizeSpeechRequest()
                .withText(text)
                .withVoiceId(VoiceId.Joanna)
                .withOutputFormat(OutputFormat.Mp3)
                .withEngine("neural");

        SynthesizeSpeechResult result = polly.synthesizeSpeech(request);

        return result.getAudioStream();
    }
}