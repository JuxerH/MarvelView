package com.finalwork.marvelview.api.util;

import android.content.Context;


import androidx.annotation.NonNull;

import com.finalwork.marvelview.R;
import com.finalwork.marvelview.api.exception.MarvelException;

import java.io.IOException;

public class StringUtils {

    private StringUtils() {
    }

    public static String getApiErrorMessage(@NonNull Context context, @NonNull Throwable e) {
        if (e instanceof IOException) {
            return context.getString(R.string.connection_error);
        } else if (e instanceof MarvelException) {
            return context.getString(R.string.server_error);
        } else {
            return "";
        }
    }

}
