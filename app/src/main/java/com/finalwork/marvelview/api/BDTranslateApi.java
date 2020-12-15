package com.finalwork.marvelview.api;


import com.finalwork.marvelview.api.json.translateresult.TranslateDataWrapper;


import java.io.IOException;


import okhttp3.FormBody;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;

import retrofit2.http.GET;

import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public class BDTranslateApi {//单例模式
    private static final String Base_Url = "http://api.fanyi.baidu.com/";
    private static BDTranslateApi bdTranslateApi;
    private final BDTranslateService bdTranslateService;
    private String result;

    private BDTranslateApi() {
        result = "translate error";
        BDAuthInterceptor bdAuthInterceptor = new BDAuthInterceptor();
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(bdAuthInterceptor)
                .addInterceptor(httpLoggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Base_Url)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        bdTranslateService = retrofit.create(BDTranslateService.class);

    }

    public static BDTranslateApi getInstance() {
        if (bdTranslateApi == null) {
            bdTranslateApi = new BDTranslateApi();
        }
        return bdTranslateApi;

    }

    public String asyTranslate(String query) throws IOException {//异步get请求
        Call<TranslateDataWrapper> translateDataWrapperCall = bdTranslateService.translate(query);
        translateDataWrapperCall.enqueue(new Callback<TranslateDataWrapper>() {
            @Override
            public void onResponse(Call<TranslateDataWrapper> call, Response<TranslateDataWrapper> response) {
                TranslateDataWrapper translateDataWrapper;
                translateDataWrapper = response.body();
                if (translateDataWrapper.getError_code() == 0) {
                    result = translateDataWrapper.getTrans_result().get(0).getDst();
                } else result = translateDataWrapper.getError_msg();
            }

            @Override
            public void onFailure(Call<TranslateDataWrapper> call, Throwable t) {
                result = "translate api error";
            }
        });
        return result;
    }

    public String translate(String query) throws IOException {
        Response<TranslateDataWrapper> dataResponse = bdTranslateService.translate(query).execute();
        TranslateDataWrapper dataWrapperCall = dataResponse.body();
        return dataWrapperCall.getTrans_result().get(0).getDst();
    }

    public String postTranslate(String query) throws IOException {
        String salt = String.valueOf(System.currentTimeMillis());
        String sign = HashHelper.generate("20201210000643668" + query + salt + "ePE2_QbkzD_adJsjDm5v");
        RequestBody requestBody = new FormBody.Builder()
                .addEncoded("q", query)
                .addEncoded("sign", sign)
                .addEncoded("salt", salt)
                .addEncoded("from", "en")
                .addEncoded("to", "zh")
                .addEncoded("appid", "20201210000643668")
                .build();
        Response<TranslateDataWrapper> dataResponse = bdTranslateService.postTranslate(requestBody).execute();
        TranslateDataWrapper translateDataWrapper = dataResponse.body();
        if (translateDataWrapper.getError_code() == 0) {
            result = translateDataWrapper.getTrans_result().get(0).getDst();
        } else result = translateDataWrapper.getError_msg();
        return result;
    }

    private interface BDTranslateService {
        @GET("api/trans/vip/translate")
        Call<TranslateDataWrapper> translate(
                @Query("q") String query
        );

        @Headers("Content-Type:application/x-www-form-urlencoded")
        @POST("api/trans/vip/translate")
        Call<TranslateDataWrapper> postTranslate(
                @Body RequestBody requestBody
        );
    }
}
