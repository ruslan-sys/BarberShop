package com.example.barbershop;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.UserManager;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.barbershop.Common.Common;
import com.example.barbershop.Utils.UserUtils;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;

import java.util.Objects;


public class MainActivity extends AppCompatActivity implements FacebookCallback<LoginResult> {

    private LoginButton loginButton;
    private CallbackManager mCallbackManager;
    private CheckBox remembermeCheck;
    private static final String TAG = "MainActivity01";
    private Context context ;
    private SharedPreferences defaultSharedPrefernces;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //general init
        context = getApplicationContext();
        defaultSharedPrefernces = PreferenceManager.getDefaultSharedPreferences(context);

        //rememeber me init
        remembermeCheck = findViewById(R.id.remember_me_check);
        remembermeCheck.setChecked(defaultSharedPrefernces.getBoolean(Common.REMEMBER_ME, false));
        remembermeCheck.setOnCheckedChangeListener((compoundButton, b) -> defaultSharedPrefernces.edit().putBoolean(Common.REMEMBER_ME, b).apply());

        //login button init
        loginButton = findViewById(R.id.login_button);
        loginButton.setPermissions(Common.EMAIL);

        if (AccessToken.getCurrentAccessToken() == null) {
            mCallbackManager = CallbackManager.Factory.create();
            loginButton.registerCallback(mCallbackManager, this);
        } else
            goHomeActivity();

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(Common.ACTAG, "onResume (Main Activity)");
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        GraphRequest request = GraphRequest.newMeRequest(accessToken, (object, response) -> {
            try {
                UserUtils.saveUserState(object, getApplicationContext());
                goHomeActivity();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "first_name,last_name,name,id,picture,email");
        request.setParameters(parameters);
        request.executeAsync();


    }

    @Override
    public void onCancel() {
        Toast.makeText(getBaseContext(), "Login Has Been Canceled", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onError(FacebookException error) {
        Toast.makeText(getBaseContext(), "Oops Something Went Wrong..", Toast.LENGTH_LONG).show();
        Log.d("laCasadelpapel", Objects.requireNonNull(error.getMessage()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }


    public void goHomeActivity() {
        Intent goHome = new Intent(getBaseContext(), HomeActivity.class);
        startActivity(goHome);
    }
}

