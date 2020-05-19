package com.linwei.frame.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.util.DisplayMetrics;

import java.util.HashMap;
import java.util.Locale;

public class AppLanguageUtils {
    private static String currentLanguage = "";
    public static HashMap<String, Locale> allLanguages = new HashMap<String, Locale>(8) {{
        put("en", Locale.ENGLISH);
        put("zh", Locale.SIMPLIFIED_CHINESE);
        put("in", new Locale("in", "IN", ""));
        put("vi", new Locale("vi", "VN", ""));
    }};

    @SuppressWarnings("deprecation")
    public static void setLanguage(Context context, String language) {
        Resources resources = context.getApplicationContext().getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();

        Locale locale = getLocale(language);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(locale);
        } else {
            config.locale = locale;
        }
        resources.updateConfiguration(config, dm);
    }

    public static Locale getLocale(String language) {
        if (allLanguages.containsKey(language)) {
            return allLanguages.get(language);
        } else {
            return Locale.getDefault();
        }
    }


    public static Context attachBaseContext(Context context, String language) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return updateResources(context, getLocale(language));
        } else {
            return context;
        }
    }


    @TargetApi(Build.VERSION_CODES.N)
    private static Context updateResources(Context context, Locale locale) {
        Resources resources = context.getResources();

        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        configuration.setLocales(new LocaleList(locale));
        return context.createConfigurationContext(configuration);
    }

}