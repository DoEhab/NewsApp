package test.com.newsApp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import test.com.newsApp.model.Article;

import java.util.ArrayList;

public class PreferenceHelper {

    private final Context context;

    private static final String ARTICLES="articles";


    public String getArticles() {
        return get(ARTICLES, null);
    }
    public void setArticles(String articles){

        set(ARTICLES, articles);
    }

    public Context getContext() {
        return context;
    }


    public PreferenceHelper(Context context) {
        this.context = context;
    }


    private void set(String key, String value) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString(key, value);
        editor.apply();
    }

    private String get(String key, String defaultValue) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        return settings.getString(key, defaultValue);
    }


    public void clearData(){
        PreferenceManager.getDefaultSharedPreferences(context).edit().clear().apply();
    }


}
