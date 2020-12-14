package com.finalwork.marvelview.api;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class BDAuthInterceptor implements Interceptor {
    private static final String appId = "20201210000643668";
    private static final String appKey = "ePE2_QbkzD_adJsjDm5v";

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request currentRequest = chain.request();



        if (currentRequest.method().equals("GET")) {
            String salt = String.valueOf(System.currentTimeMillis());
            String query = currentRequest.url().queryParameter("q");
            String sign = HashHelper.generate(appId + query + salt + appKey);
            HttpUrl httpUrl = currentRequest.url().newBuilder()
                    .addQueryParameter("from", "en")
                    .addQueryParameter("to", "zh")
                    .addQueryParameter("appid", appId)
                    .addQueryParameter("salt", salt)
                    .addQueryParameter("sign", sign)
                    .build();

            Request newRequest = currentRequest.newBuilder().url(httpUrl).build();
            return chain.proceed(newRequest);
        }
        if (currentRequest.method().equals("POST")) {
//            HttpUrl httpUrl = currentRequest.url().newBuilder()
//                    .addQueryParameter("appid", appId)
//                    .build();
//
//            Request newRequest = currentRequest.newBuilder().url(httpUrl).build();
//            chain.proceed(newRequest);
//            if (currentRequest.body() instanceof RequestBody) {
//                FormBody.Builder bodyBuilder = new FormBody.Builder();
//                RequestBody requestBody =  currentRequest.body();
//                // 添加公共参数
//                requestBody = bodyBuilder
//                        .addEncoded("from", "en")
//                        .addEncoded("to", "zh")
//                        .addEncoded("appid", appId)
//                        .build();
//
//                Request newRequest = currentRequest.newBuilder().post(requestBody).build();
//                return chain.proceed(newRequest);
//            }
        }
        return chain.proceed(currentRequest);
    }

}
