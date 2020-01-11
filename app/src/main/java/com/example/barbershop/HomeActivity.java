package com.example.barbershop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import com.bumptech.glide.Glide;
import com.example.barbershop.Common.Common;
import com.example.barbershop.Utils.UserUtils;
import com.example.barbershop.entities.User;
import com.facebook.login.LoginManager;


public class HomeActivity extends AppCompatActivity {

    private TextView welcomeLabel;
    private ImageView avatarImage;
    SharedPreferences preferences;
    private static final String TAG = "HomeActivity";
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

        //test
        Log.d(TAG , "name : " + currUser.getName());
        Log.d(TAG ,"id : " + currUser.getId());
        Log.d(TAG , "email : " + currUser.getEmail());
        Log.d(TAG ,"pictureUrl : " + currUser.getPicture().toString());

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
        Log.d(TAG, "onDestroy()");
        if (!PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext())
                .getBoolean(Common.REMEMBER_ME, false))
            LoginManager.getInstance().logOut();

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
