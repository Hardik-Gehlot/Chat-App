package com.hardik.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hardik.chatapp.Settings.ChangePassword;
import com.hardik.chatapp.Settings.EditProfile;
import com.hardik.chatapp.Settings.Wallpaper;
import com.hardik.chatapp.Utils.Session;

public class SettingActivity extends AppCompatActivity {
ImageView profile;
TextView username,email;
LinearLayout editProfile,changePassword,wallpaper,inviteFriends,aboutUs,terms;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initialize();
        editProfile();
        changePassword();
        wallpaper();
    }

    private void wallpaper() {
        wallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, Wallpaper.class));
            }
        });
    }

    private void changePassword() {
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, ChangePassword.class));
            }
        });
    }

    private void editProfile() {
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, EditProfile.class));
            }
        });
    }

    private void initialize() {
        profile = findViewById(R.id.setting_profile_img);
        username = findViewById(R.id.setting_username);
        email = findViewById(R.id.setting_email);
        editProfile = findViewById(R.id.setting_edit_profile);
        changePassword = findViewById(R.id.setting_change_password);
        wallpaper = findViewById(R.id.setting_wallpaper);
        inviteFriends = findViewById(R.id.setting_invite);
        aboutUs = findViewById(R.id.setting_about_us);
        terms = findViewById(R.id.setting_terms);

        Glide.with(this).load(Session.getProfile(this)).into(profile);
        username.setText(Session.getUsername(this));
        email.setText(Session.getEmail(this));
    }
}