package com.example.barbershop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.barbershop.Common.Common;
import com.example.barbershop.Utils.UserUtils;
import com.example.barbershop.entities.User;
import com.facebook.login.LoginManager;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;


public class HomeActivity extends AppCompatActivity {

    private TextView welcomeLabel;
    private ImageView avatarImage;
    SharedPreferences preferences;
    private static final String TAG = "HomeActivity01";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        User currUser = UserUtils.restoreUserState(getApplicationContext());
        welcomeLabel = findViewById(R.id.welcome_label);
        welcomeLabel.setText("Welcome ".concat(currUser.getFirstName()));
        avatarImage = findViewById(R.id.avatar_image);
        Glide.with(this)
                .load(currUser.getPicture())
                .into(avatarImage);

         Dexter .withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                }).check();

        //test
        Log.d(TAG , "name : " + currUser.getName());
        Log.d(TAG ,"id : " + currUser.getId());
        Log.d(TAG , "email : " + currUser.getEmail());
        Log.d(TAG ,"pictureUrl : " + currUser.getPicture().toString());
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(Common.ACTAG, "onPause (HomeActivity)");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(Common.ACTAG, "onResume (HomeActivity)");
    }

    @Override
    public void onBackPressed() {
        //Do Nothing
        //Disable Go Back Arrow
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.threedots_menu, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "(Home Activity) onStop()");
        if (!PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext())
                .getBoolean(Common.REMEMBER_ME, false)){
            LoginManager.getInstance().logOut();
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout_menu) {
            LoginManager.getInstance().logOut();
            startActivity(new Intent(this, MainActivity.class));

            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    public void showPopup(View view) {
        PopupMenu popup = new PopupMenu(getApplicationContext(), view);
        popup.setOnMenuItemClickListener(menuItem -> {
            onOptionsItemSelected(menuItem);
            return false;
        });
        popup.inflate(R.menu.threedots_menu);
        popup.show();
    }
}
