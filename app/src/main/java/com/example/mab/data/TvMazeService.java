package com.example.mab.data;

import com.example.mab.ui.bottom_navigation.models.Cast;
import com.example.mab.ui.bottom_navigation.models.Crew;
import com.example.mab.ui.bottom_navigation.models.EpisodeModel;
import com.example.mab.ui.bottom_navigation.models.FilmModel;
import com.example.mab.ui.bottom_navigation.models.ImageOfGetImageById;
import com.example.mab.ui.bottom_navigation.models.Season;
import com.example.mab.ui.bottom_navigation.models.Show;
import com.example.mab.ui.bottom_navigation.models.ShowModel;
import com.example.mab.ui.bottom_navigation.models.ShowModelGetFullSchedule;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class TvMazeService {

    OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.MINUTES) // connect timeout
            .writeTimeout(5, TimeUnit.MINUTES) // write timeout
            .readTimeout(5, TimeUnit.MINUTES).build();

    private static TvMazeService mInstance;


    private Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.tvmaze.com/")
            .client(client)
            .build();

    private TvMazeApi service = retrofit.create(TvMazeApi.class);

    public static TvMazeService getInstance() {
        if (mInstance == null) {
            mInstance = new TvMazeService();
        }
        return mInstance;
    }


    public void searchShow(String search, final TvMazeCallback callback) {
        service.searchShow(search)
                .enqueue(new Callback<List<FilmModel>>() {
                    @Override
                    public void onResponse(@NotNull Call<List<FilmModel>> call, @NotNull Response<List<FilmModel>> response) {
                        callback.onResponseList(response);
                    }

                    @Override
                    public void onFailure(@NotNull Call<List<FilmModel>> call, @NotNull Throwable t) {
                        callback.onFailure(t);
                    }
                });
    }

    public void getEpisodeByShowId(Integer id, TvMazeCallback callback){
        service.getEpisodeByShowId(id)
                .enqueue(new Callback<List<EpisodeModel>>() {
                    @Override
                    public void onResponse(@NotNull Call<List<EpisodeModel>> call, @NotNull Response<List<EpisodeModel>> response) {
                        callback.onResponseListEpisode(response);
                    }

                    @Override
                    public void onFailure(@NotNull Call<List<EpisodeModel>> call, @NotNull Throwable t) {
                        callback.onFailure(t);
                    }
                });
    }

    public void getAll(int page, TvMazeCallback callback){
        service.getAll(page)
                .enqueue(new Callback<List<Show>>() {
                    @Override
                    public void onResponse(@NotNull Call<List<Show>> call, @NotNull Response<List<Show>> response) {
                        callback.onResponseListGetAll(response);
                    }

                    @Override
                    public void onFailure(@NotNull Call<List<Show>> call, @NotNull Throwable t) {
                        callback.onFailure(t);
                    }
                });
    }

    public void getSchedule(TvMazeCallback callback){
        service.getSchedule()
                .enqueue(new Callback<List<ShowModel>>() {
                    @Override
                    public void onResponse(@NotNull Call<List<ShowModel>> call, @NotNull Response<List<ShowModel>> response) {
                        callback.onResponseListSchedule(response);
                    }

                    @Override
                    public void onFailure(@NotNull Call<List<ShowModel>> call, @NotNull Throwable t) {
                        callback.onFailure(t);
                    }
                });
    }

    public void getScheduleFull(TvMazeCallback callback){
        service.getScheduleFull()
                .enqueue(new Callback<List<ShowModelGetFullSchedule>>() {
                    @Override
                    public void onResponse(@NotNull Call<List<ShowModelGetFullSchedule>> call, @NotNull Response<List<ShowModelGetFullSchedule>> response) {
                        callback.onResponseListScheduleFull(response);
                    }

                    @Override
                    public void onFailure(@NotNull Call<List<ShowModelGetFullSchedule>> call, Throwable t) {
                        callback.onFailure(t);
                    }
                });
    }

    public void getImagesById(int id, TvMazeCallback callback){
        service.getImagesById(id)
                .enqueue(new Callback<List<ImageOfGetImageById>>() {
                    @Override
                    public void onResponse(@NotNull Call<List<ImageOfGetImageById>> call, @NotNull Response<List<ImageOfGetImageById>> response) {
                        callback.onResponseImagesByIdTVShow(response);
                    }

                    @Override
                    public void onFailure(@NotNull Call<List<ImageOfGetImageById>> call, @NotNull Throwable t) {
                        callback.onFailure(t);
                    }
                });
    }

    public void getCastById(int id, TvMazeCallback callback){
        service.getCastById(id)
                .enqueue(new Callback<List<Cast>>() {
                    @Override
                    public void onResponse(@NotNull Call<List<Cast>> call, @NotNull Response<List<Cast>> response) {
                        callback.onResponseCastList(response);
                    }

                    @Override
                    public void onFailure(@NotNull Call<List<Cast>> call, @NotNull Throwable t) {
                        callback.onFailure(t);
                    }
                });
    }

    public void getCrewById(int id, TvMazeCallback callback){
        service.getCrewByIdI(id)
                .enqueue(new Callback<List<Crew>>() {
                    @Override
                    public void onResponse(@NotNull Call<List<Crew>> call, @NotNull Response<List<Crew>> response) {
                        callback.onResponseListCrew(response);
                    }

                    @Override
                    public void onFailure(@NotNull Call<List<Crew>> call, @NotNull Throwable t) {
                        callback.onFailure(t);
                    }
                });
    }

    public void getShowById(int id, TvMazeCallback callback){
        service.getShowById(id)
                .enqueue(new Callback<Show>() {
                    @Override
                    public void onResponse(@NotNull Call<Show> call, @NotNull Response<Show> response) {
                        callback.onResponseTvShowById(response);
                    }

                    @Override
                    public void onFailure(@NotNull Call<Show> call, @NotNull Throwable t) {
                        callback.onFailure(t);
                    }
                });
    }

    public interface TvMazeApi {

        //http://api.tvmaze.com/search/shows?q=girls

        @GET("search/shows")
        Call<List<FilmModel>> searchShow(@Query("q") String q);

        //http://api.tvmaze.com/shows/1

        @GET("shows/{id}")
        Call<Show> getShowById(@Path("id") int id);

        //

        @GET("schedule")
        Call<List<ShowModel>> getSchedule();

        //http://api.tvmaze.com/schedule/full

        @GET("schedule/full")
        Call<List<ShowModelGetFullSchedule>> getScheduleFull();

        //http://api.tvmaze.com/seasons/1/episodes

        @GET("shows/{id}/episodes")
        Call<List<EpisodeModel>> getEpisodeByShowId(@Path("id") Integer id);

        //http://api.tvmaze.com/shows?page=1

        @GET("shows")
        Call<List<Show>> getAll(@Query("page") int page);

        //http://api.tvmaze.com/shows/1/images

        @GET("shows{id}images")
        Call<List<ImageOfGetImageById>> getImagesById(@Path("id") int id);

        //http://api.tvmaze.com/shows/1/cast

        @GET("shows{id}cast")
        Call<List<Cast>> getCastById(@Path("id") int id);

        //http://api.tvmaze.com/shows/1/crew

        @GET("shows{id}crew")
        Call<List<Crew>> getCrewByIdI(@Path("id") int id);

        //http://api.tvmaze.com/shows/1/seasons

        @GET("shows{id}seasons")
        Call<List<Season>> getSeasonsById(@Path("id") int id);

        //http://api.tvmaze.com/seasons/1/episodes


    }

    public interface TvMazeCallback {
        void onResponseList(@NotNull Response<List<FilmModel>> response);

        void onResponseListGetAll(@NotNull Response<List<Show>> response);

        void onResponseListSchedule(@NotNull Response<List<ShowModel>> response);

        void onResponseListScheduleFull(@NotNull Response<List<ShowModelGetFullSchedule>> response);

        void onResponseListEpisode(@NotNull Response<List<EpisodeModel>> response);

        void onResponseImagesByIdTVShow(@NotNull Response<List<ImageOfGetImageById>> response);

        void onFailure(Throwable t);

        void onResponseCastList(@NotNull Response<List<Cast>> response);

        void onResponseListCrew(@NotNull Response<List<Crew>> response);

        void onResponseTvShowById(@NotNull Response<Show> response);
    }
}