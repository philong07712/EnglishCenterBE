package com.example.EnglishCenterBE.api.resources;

import com.example.EnglishCenterBE.data.filter.CorsFilter;
import com.fasterxml.jackson.core.util.JacksonFeature;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;
import java.io.FileInputStream;

@ApplicationPath("/api")
public class ApplicationConfig extends ResourceConfig {
    public ApplicationConfig() {
        packages("com.example.EnglishCenterBE");
        register(MultiPartFeature.class);
        register(JacksonFeature.class);
        register(CorsFilter.class);
        System.out.println("Init application");
        try {
            //TODO: change path before push to heroku
//            FileInputStream serviceAccount = new FileInputStream("./ServiceAccountKey.json");
            FileInputStream serviceAccount = new FileInputStream("C:\\Users\\OS\\Desktop\\Work\\EnglishCenterBE\\ServiceAccountKey.json");
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://englishcenter-6e5e5-default-rtdb.firebaseio.com")
                    .build();

            FirebaseApp.initializeApp(options);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
