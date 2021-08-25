package com.hardik.chatapp;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hardik.chatapp.API.APIController;
import com.hardik.chatapp.Models.APIResponse;
import com.hardik.chatapp.Models.User;
import com.hardik.chatapp.Utils.Session;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignIn extends AppCompatActivity {
    private CircleImageView profile;
    private EditText fname,lname,username,email,password,confirmPassword;
    private Button signIn;
    private TextView goToLogin;
    private ProgressBar progressBar;
    private User user;
    private String password1,password2,profile_img;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        initializing();
        getSignIn();
        goToLogin();
        managePermissions();
    }

    private void managePermissions() {
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withContext(getApplicationContext())
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                getImageFromGallery.launch("image/*");

                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }
                        }).check();
            }
        });
    }
    private void goToLogin() {
        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignIn.this, Login.class));
                finish();
            }
        });
    }
    private void getSignIn() {
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                user = new User(fname.getText().toString().trim(),
                        lname.getText().toString().trim(),
                        email.getText().toString().trim(),
                        username.getText().toString().trim(),
                        profile_img);
                password1 = password.getText().toString().trim();
                password2 = confirmPassword.getText().toString().trim();
                if(!user.getFname().isEmpty() && !user.getUsername().isEmpty() && !user.getLname().isEmpty() && !user.getEmail().isEmpty() && !password1.isEmpty()){
                    if(password1.equals(password2)){
                        progressBar.setVisibility(View.VISIBLE);
                        Call<APIResponse> call = APIController.getInstance().getApi().register(user.getFname(),user.getLname(),user.getUsername(),user.getEmail(),password1,user.getProfile_img());
                        call.enqueue(new Callback<APIResponse>() {
                            @Override
                            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                                APIResponse data = response.body();
                                progressBar.setVisibility(View.GONE);
                                if(data.getStatus() == 0){
                                    Toast.makeText(SignIn.this, data.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    user = data.getData();
                                    if(Session.setUser(getApplicationContext(),user)){
                                        Intent intent = new Intent(SignIn.this,MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            }
                            @Override
                            public void onFailure(Call<APIResponse> call, Throwable t) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(SignIn.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else{
                        Toast.makeText(SignIn.this, "Passwords not match", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(SignIn.this, "All Fields are required", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    private void initializing() {
        fname = findViewById(R.id.signin_fname);
        lname = findViewById(R.id.signin_lname);
        email = findViewById(R.id.signin_email);
        password = findViewById(R.id.signin_password);
        confirmPassword = findViewById(R.id.signin_confirm_password);
        signIn = findViewById(R.id.signin_button);
        progressBar = findViewById(R.id.signin_progress);
        goToLogin = findViewById(R.id.go_to_login);
        profile = findViewById(R.id.signin_profile_image);
        username = findViewById(R.id.signin_username);
        profile_img="1";
    }

    ActivityResultLauncher<String> getImageFromGallery = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                    if(result != null){
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(),result);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        profile.setImageBitmap(bitmap);
                        encodeBitmap();
                    }
                }
            }
    );

    private void encodeBitmap() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,80,byteArrayOutputStream);

        byte[] bytes = byteArrayOutputStream.toByteArray();
        profile_img=android.util.Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(Session.isLogin(getApplicationContext())){
            startActivity(new Intent(SignIn.this, MainActivity.class));
            finish();
        }
    }
}