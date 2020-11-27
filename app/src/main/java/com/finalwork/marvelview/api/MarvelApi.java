package com.finalwork.marvelview.api;


import com.finalwork.marvelview.api.json.character.CharacterDataContainer;
import com.finalwork.marvelview.model.viewobject.CharacterVO;
import com.finalwork.marvelview.api.exception.MarvelException;
import com.finalwork.marvelview.model.viewobject.SectionVO;
import com.finalwork.marvelview.api.util.DataParser;
import com.finalwork.marvelview.api.json.character.CharacterDataWrapper;
import com.finalwork.marvelview.api.json.section.SectionDataWrapper;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class MarvelApi {

    public static final int MAX_FETCH_LIMIT = 100;
    private static final String BASE_URL = "http://gateway.marvel.com/v1/public/";
    private static final boolean DEBUG=true;//网络日志打印等级
    private static MarvelApi sMarvelApi;
    private final MarvelService mService;

    private Call<CharacterDataWrapper> mLastSearchCall;

    private MarvelApi() {//初始化API

        AuthInterceptor authenticator = new AuthInterceptor();//API验证拦截器

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();//网络日志拦截器
        logging.setLevel(DEBUG ? Level.BODY : Level.BASIC);

        OkHttpClient client = new OkHttpClient.Builder()//初始化ok_http
                .addInterceptor(authenticator)
                .addInterceptor(logging)
                .build();

        Retrofit retrofit = new Retrofit.Builder()//初始化retrofit
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mService = retrofit.create(MarvelService.class);//创建API服务层
    }

    public static MarvelApi getInstance() {//用于获取API实例
        if (sMarvelApi == null) {
            sMarvelApi = new MarvelApi();
        }
        return sMarvelApi;
    }

    public MarvelResult<CharacterVO> listCharacters(int offset) throws IOException, MarvelException {
        Response<CharacterDataWrapper> response = mService.listCharacters(null, offset, MAX_FETCH_LIMIT).execute();
        if (response.isSuccessful()) {
            assert response.body() != null;
            return DataParser.parse(response.body());
        } else {
            throw new MarvelException(response.code(), response.message());
        }
    }

    public void listCharacters(int offset, MarvelCallback<CharacterDataWrapper> callback) {
        mService.listCharacters(null, offset, MAX_FETCH_LIMIT).enqueue(callback);
    }

    public void searchCharacters(String query, MarvelCallback<CharacterDataWrapper> callback) {
        if (mLastSearchCall != null && !mLastSearchCall.isCanceled()) {
            mLastSearchCall.cancel();
        }
        mLastSearchCall = mService.listCharacters(query, /* offset */ 0, MAX_FETCH_LIMIT);
        mLastSearchCall.enqueue(callback);
    }

    public int getTotalOfCharacters() throws IOException, MarvelException {
        int limit = 1;
        Response<CharacterDataWrapper> response = mService.listCharacters(null, /* offset */ 0, limit).execute();
        if (response.isSuccessful()) {
            assert response.body() != null;
            CharacterDataContainer dataContainer = response.body().data;
            if (dataContainer != null) {
                return dataContainer.total;
            }
            return limit;
        } else {
            throw new MarvelException(response.code(), response.message());
        }
    }

    public MarvelResult<CharacterVO> getCharacter(int offset) throws IOException, MarvelException {
        Response<CharacterDataWrapper> response = mService.listCharacters(null, offset, /* limit */ 1).execute();
        if (response.isSuccessful()) {
            assert response.body() != null;
            return DataParser.parse(response.body());
        } else {
            throw new MarvelException(response.code(), response.message());
        }
    }

    public MarvelResult<SectionVO> listComics(long characterId, int offset) throws IOException, MarvelException {
        Response<SectionDataWrapper> response = mService.listComics(characterId, offset, MAX_FETCH_LIMIT).execute();
        if (response.isSuccessful()) {
            assert response.body() != null;
            return DataParser.parse(response.body());
        } else {
            throw new MarvelException(response.code(), response.message());
        }
    }

    public void listComics(long characterId, int offset, MarvelCallback<SectionDataWrapper> callback) {
        mService.listComics(characterId, offset, MAX_FETCH_LIMIT).enqueue(callback);
    }

    public MarvelResult<SectionVO> listSeries(long characterId, int offset) throws IOException, MarvelException {
        Response<SectionDataWrapper> response = mService.listSeries(characterId, offset, MAX_FETCH_LIMIT).execute();
        if (response.isSuccessful()) {
            assert response.body() != null;
            return DataParser.parse(response.body());
        } else {
            throw new MarvelException(response.code(), response.message());
        }
    }

    public void listSeries(long characterId, int offset, MarvelCallback<SectionDataWrapper> callback) {
        mService.listSeries(characterId, offset, MAX_FETCH_LIMIT).enqueue(callback);
    }

    public MarvelResult<SectionVO> listStories(long characterId, int offset) throws IOException, MarvelException {
        Response<SectionDataWrapper> response = mService.listStories(characterId, offset, MAX_FETCH_LIMIT).execute();
        if (response.isSuccessful()) {
            assert response.body() != null;
            return DataParser.parse(response.body());
        } else {
            throw new MarvelException(response.code(), response.message());
        }
    }

    public void listStories(long characterId, int offset, MarvelCallback<SectionDataWrapper> callback) {
        mService.listStories(characterId, offset, MAX_FETCH_LIMIT).enqueue(callback);
    }

    public MarvelResult<SectionVO> listEvents(long characterId, int offset) throws IOException, MarvelException {
        Response<SectionDataWrapper> response = mService.listEvents(characterId, offset, MAX_FETCH_LIMIT).execute();
        if (response.isSuccessful()) {
            assert response.body() != null;
            return DataParser.parse(response.body());
        } else {
            throw new MarvelException(response.code(), response.message());
        }
    }

    public void listEvents(long characterId, int offset, MarvelCallback<SectionDataWrapper> callback) {
        mService.listEvents(characterId, offset, MAX_FETCH_LIMIT).enqueue(callback);
    }

    private interface MarvelService {

        @GET("characters")
        Call<CharacterDataWrapper> listCharacters(
                @Query("nameStartsWith") String query,
                @Query("offset") int offset,
                @Query("limit") int limit);

        @GET("characters/{characterId}/comics")
        Call<SectionDataWrapper> listComics(
                @Path("characterId") long characterId,
                @Query("offset") int offset,
                @Query("limit") int limit);

        @GET("characters/{characterId}/series")
        Call<SectionDataWrapper> listSeries(
                @Path("characterId") long characterId,
                @Query("offset") int offset,
                @Query("limit") int limit);

        @GET("characters/{characterId}/stories")
        Call<SectionDataWrapper> listStories(
                @Path("characterId") long characterId,
                @Query("offset") int offset,
                @Query("limit") int limit);

        @GET("characters/{characterId}/events")
        Call<SectionDataWrapper> listEvents(
                @Path("characterId") long characterId,
                @Query("offset") int offset,
                @Query("limit") int limit);
    }

}

