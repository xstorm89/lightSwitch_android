package com.fknussel.lightswitch.networking;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.GET;


public class RaspiClient {

    private static final String API_URL = "http://192.168.1.5:8080";

    public static Raspi getRaspiInterface() {
        // Create a very simple REST adapter which points the GitHub API endpoint.
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(API_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        // Create an instance of our GitHub API interface.
        Raspi raspi = restAdapter.create(Raspi.class);

        return raspi;
    }

    // Model
    public static class Lights {
        Boolean on;

        public Boolean on() {
            return on;
        }

    }

    // Interface w/ the web service
    public interface Raspi {
        @GET("/lights/toggle")
        void toggleLights(Callback<Lights> cb);

        @GET("/lights/status")
        void getStatus(Callback<Lights> cb);

        @GET("/lights/test")
        void pinTest(Callback<Lights> cb);

    }


}