package com.finalwork.marvelview.api;

import com.finalwork.marvelview.BuildConfig;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {

    private static final String QUERY_NAME_TIMESTAMP = "ts";
    private static final String QUERY_NAME_APIKEY = "apikey";
    private static final String QUERY_NAME_HASH = "hash";
    private static final String MARVEL_PRIVATE_KEY="8b8de9bd0732cc90d1acd729e296f3604063db22";
    private static final String MARVEL_PUBLIC_KEY="efcd27aebaa7d99f2208523bded7f048";

    @Override
    public Response intercept(Chain chain) throws IOException {

        String ts = String.valueOf(System.currentTimeMillis());
        String hash = HashHelper.generate(ts + MARVEL_PRIVATE_KEY + MARVEL_PUBLIC_KEY);

        Request currentRequest = chain.request();

        HttpUrl url = currentRequest.url().newBuilder()
                .addQueryParameter(QUERY_NAME_TIMESTAMP, ts)
                .addQueryParameter(QUERY_NAME_APIKEY, MARVEL_PUBLIC_KEY)
                .addQueryParameter(QUERY_NAME_HASH, hash).build();

        Request newRequest = currentRequest.newBuilder().url(url).build();

        return chain.proceed(newRequest);
    }
}
//生成MD5加密的哈希码
class HashHelper {

    public static String generate(String s) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] digest = messageDigest.digest(s.getBytes());
            StringBuilder md5 = new StringBuilder();
            for (byte value : digest) {
                md5.append(Integer.toHexString((value & 0xFF) | 0x100).substring(1, 3));
            }
            return md5.toString();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
}