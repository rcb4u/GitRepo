package com.example.rspl_rahul.gitrepo.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.HashMap;

public class PersistenceManager {
    private static SharedPreferences preferences;
    public static final String MY_CART_LIST_LOCAL = "MyCartItems";
    private static final String LOCAL_PREFERENCES = "retail-perf";
    private static final String SESSION_ID_KEY = "AccessToken";
    private static final String TIME_KEY = "Time";
    private static final String REFRESH_TOKEN_KEY = "RefreshToken";
    Context context;

    int PRIVATE_MODE = 0;
    // Shared preferences file name
    private static final String PREF_NAME = "androidhive-welcome";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private static final String IS_LOGIN_TIME_LAUNCH = "IsLOGINTimeLaunch";

  /*  public PersistenceManager(Context appContext) {
        this.context=appContext;
        preferences = PreferenceManager.getDefaultSharedPreferences(appContext);
    }
    public void setFirstTimeLaunch(boolean isFirstTime) {
        preferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(IS_LOGIN_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        preferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        SharedPreferences.Editor editor = preferences.edit();
        return preferences.getBoolean(IS_LOGIN_TIME_LAUNCH, true);
    }

    public void setWelComeFirstTimeLaunch(boolean isFirstTime) {
        preferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isWelComeFirstTimeLaunch() {
        preferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        SharedPreferences.Editor editor = preferences.edit();
        return preferences.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    //To save Hash Map
    public static void saveMap(Context context, String key, HashMap<String,Product> inputMap){
        preferences = context.getSharedPreferences(MY_CART_LIST_LOCAL, Context.MODE_PRIVATE);
        if (preferences != null){
            Gson gson = new Gson();
            String hashMapString = gson.toJson(inputMap);
            //save in shared prefs
            preferences.edit().putString(key, hashMapString).apply();
        }
    }

    public static HashMap<String,Product> loadMap(String key, Context context){
        HashMap<String,Product> outputMap = new HashMap<String, Product>();
        preferences = context.getSharedPreferences(MY_CART_LIST_LOCAL, Context.MODE_PRIVATE);
        try{
            //get from shared prefs
            String storedHashMapString = preferences.getString(key, (new JSONObject()).toString());
            java.lang.reflect.Type type = new TypeToken<HashMap<String, Product>>(){}.getType();
            Gson gson = new Gson();
            return  gson.fromJson(storedHashMapString, type);

        }catch(Exception e){
            e.printStackTrace();
        }
        return outputMap;
    }

    public static void saveRefreshToken(Context context, String refreshtoken) {
        SharedPreferences sharedPreferences = context.getApplicationContext().getSharedPreferences(LOCAL_PREFERENCES,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(REFRESH_TOKEN_KEY, refreshtoken);
        editor.commit();
    }



    public static void saveTime(Context context, String storeId) {
        SharedPreferences sharedPreferences = context.getApplicationContext().getSharedPreferences(LOCAL_PREFERENCES,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TIME_KEY, storeId);
        editor.commit();
    }

    public static String getTime(Context context) {
        SharedPreferences sharedPreferences = context.getApplicationContext().getSharedPreferences(
                LOCAL_PREFERENCES,
                Context.MODE_PRIVATE);
        return sharedPreferences.getString(TIME_KEY, "0");
    }*/
}
