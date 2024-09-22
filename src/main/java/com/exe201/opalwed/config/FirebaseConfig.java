package com.exe201.opalwed.config;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@Configuration
public class FirebaseConfig {

    @Bean
    public FirebaseApp initialize() throws IOException {

        // Replace with the path to your service account JSON key file
        var serviceAccount = ServiceAccountCredentials.fromStream(new ClassPathResource("opalwed-firebase.json").getInputStream());
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(serviceAccount)
                .setStorageBucket("opalwed-16.appspot.com")
                .build();

        return FirebaseApp.initializeApp(options);

    }
}
