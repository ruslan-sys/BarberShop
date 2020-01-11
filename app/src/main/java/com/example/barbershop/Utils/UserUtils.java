package com.example.barbershop.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;

import com.example.barbershop.Common.Common;
import com.example.barbershop.entities.User;
import com.facebook.login.LoginManager;

import org.json.JSONException;
import org.json.JSONObject;


public class UserUtils {
    public static final String DEFAULT_FIRST_NAME = "default_first";
    public static final String DEFAULT_LAST_NAME = "default_last";
    public static final String DEFAULT_ID = "0";
    public static final String DEFAULT_PICTURE = "https://png.pngitem.com/pimgs/s/105-1050694_user-placeholder-image-png-transparent-png.png";
    public static final String DEFAULT_EMAIL = "def.def@domain.com";

    public static void saveUserState (JSONObject object , Context context) throws JSONException {
        Uri picture = Uri.parse(object.getJSONObject("picture").getJSONObject("data").getString("url"));
        String firstname = object.getString("first_name");
        String lastname = object.getString("last_name");
        String id = object.getString("id");
        String email = object.optString("email", "None");

        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString(Common.PICTURE, picture.toString());
        editor.putString(Common.FIRST_NAME, firstname);
        editor.putString(Common.LAST_NAME, lastname);
        editor.putString(Common.ID, id);
        editor.putString(Common.EMAIL, email);
        editor.apply();
    }

    public static User restoreUserState (Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        String firstname = preferences.getString(Common.FIRST_NAME, DEFAULT_FIRST_NAME);
        String lastname = preferences.getString(Common.LAST_NAME, DEFAULT_LAST_NAME);
        String id = preferences.getString(Common.ID, DEFAULT_ID);
        String picture = preferences.getString(Common.PICTURE, DEFAULT_PICTURE);
        String email = preferences.getString(Common.EMAIL, DEFAULT_EMAIL);

        return new User(Uri.parse(picture), firstname , lastname , id, email, null);
    }
}
