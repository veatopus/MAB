package com.example.mab.data;


import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Input;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.example.mab.GetCharacterQuery;
import com.example.mab.GetLislAnimePagenationQuery;
import com.example.mab.MediaByIdQuery;
import com.example.mab.SearchQuery;
import com.example.mab.type.MediaFormat;
import com.example.mab.type.MediaSeason;

import org.jetbrains.annotations.NotNull;

public class AnilistService {
    private ApolloClient apolloClient = ApolloClient.builder()
            .serverUrl("https://graphql.anilist.co")
            .build();

    public void getCharacterById(int id, final AnilistCallback callback){
        Input<Integer> inputId = new Input<>(id, true);

        apolloClient.query(new GetCharacterQuery(inputId))
                .enqueue(new ApolloCall.Callback<GetCharacterQuery.Data>() {
                    @Override
                    public void onResponse(@NotNull Response<GetCharacterQuery.Data> response) {
                        callback.onResponseCharacter(response);
                    }

                    @Override
                    public void onFailure(@NotNull ApolloException e) {
                        callback.onFailure(e);
                    }
                });
    }

    public void search(int page, int perPage, String search, MediaFormat format,final AnilistCallback callback){
        Input<Integer> inputPage = new Input<>(page, true);
        Input<Integer> inputPerPage = new Input<>(perPage, true);
        Input<String> inputSearch = new Input<>(search, true);
        Input<MediaFormat> inputFormat = new Input<>(format, true);

        apolloClient.query(new SearchQuery(inputPage, inputPerPage, inputSearch, inputFormat))
                .enqueue(new ApolloCall.Callback<SearchQuery.Data>() {
                    @Override
                    public void onResponse(@NotNull Response<SearchQuery.Data> response) {
                        callback.onResponseSearch(response);
                    }

                    @Override
                    public void onFailure(@NotNull ApolloException e) {
                        callback.onFailure(e);
                    }
                });
    }

    public void getMediaById(int id, final AnilistCallback callback){
        Input<Integer> inputId = new Input<>(id, true);

        apolloClient.query(new MediaByIdQuery(inputId))
                .enqueue(new ApolloCall.Callback<MediaByIdQuery.Data>() {
                    @Override
                    public void onResponse(@NotNull Response<MediaByIdQuery.Data> response) {
                        callback.onResponseAnimeById(response);
                    }

                    @Override
                    public void onFailure(@NotNull ApolloException e) {
                        callback.onFailure(e);
                    }
                });
    }

    public void getPage(int page, int perPage, MediaFormat format, int year, MediaSeason season, final AnilistCallback callback) {

        Input<Integer> inputPage = new Input<>(page, true);
        Input<Integer> inputPerPage = new Input<>(perPage, true);
        Input<MediaFormat> inputFormat = new Input<>(format, true);
        Input<Integer> inputYear = new Input<>(year, true);
        Input<MediaSeason> inputSeason = new Input<>(season, true);

        apolloClient.query(new GetLislAnimePagenationQuery(inputPage, inputPerPage, inputFormat, inputYear, inputSeason))
                .enqueue(new ApolloCall.Callback<GetLislAnimePagenationQuery.Data>() {
                    @Override
                    public void onResponse(@NotNull Response<GetLislAnimePagenationQuery.Data> response) {
                        callback.onResponse(response);
                    }

                    @Override
                    public void onFailure(@NotNull ApolloException e) {
                        callback.onFailure(e);
                    }
                });

    }

    public interface AnilistCallback {
        void onResponse(@NotNull Response<GetLislAnimePagenationQuery.Data> response);

        void onResponseSearch(@NotNull Response<SearchQuery.Data> response);

        void onResponseCharacter(@NotNull Response<GetCharacterQuery.Data> response);

        void onResponseAnimeById(@NotNull Response<MediaByIdQuery.Data> response);

        void onFailure(@NotNull ApolloException e);
    }
}