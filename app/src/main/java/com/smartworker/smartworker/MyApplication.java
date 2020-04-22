package com.smartworker.smartworker;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;


import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp.StethoInterceptor;
import com.franmontiel.localechanger.LocaleChanger;
import com.squareup.okhttp.OkHttpClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by hp on 12/09/2017.
 */

public class MyApplication extends Application {

    private static MyApplication anInstance;
    SharedPreferences sharedPreferences;
    String appLanguage;
    SharedPreferences.Editor editor_signUp;
    private  SharedPManger sharedPManger;



    @Override
    public void onCreate() {
        super.onCreate();
        anInstance = this;
        List<Locale> locales = new ArrayList<>();
        locales.add(new Locale("en"));
        sharedPManger=new SharedPManger(this);
        LocaleChanger.initialize(getApplicationContext(), locales);
        LocaleChanger.setLocale(new Locale("en"));
        Stetho.initializeWithDefaults(this);
//        OkHttpClient client = new OkHttpClient();
//        client.networkInterceptors().add(new StethoInterceptor());

    }




    public static synchronized MyApplication getInstance(){
        return anInstance;
    }

    private static Activity mCurrentActivity = null;
    public static Activity getCurrentActivity(){
        return mCurrentActivity;
    }
    public void setCurrentActivity(Activity mCurrentActivity){
        MyApplication.mCurrentActivity = mCurrentActivity;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        LocaleChanger.onConfigurationChanged();

    }
    public SharedPManger getSharedPManger(){


      return  sharedPManger;
    }
}
