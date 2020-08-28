package com.example.mab.ui.singlton;

import com.apollographql.apollo.ApolloClient;

import okhttp3.OkHttpClient;

public class NetworkService {
    public ApolloClient getApolloClient(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkHttpClient okHttpClient = builder.build();

        return ApolloClient.builder()
                .serverUrl("https://graphql.anilist.co")
                .okHttpClient(okHttpClient)
                .build();
    }


}
