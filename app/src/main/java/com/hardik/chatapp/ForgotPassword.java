package com.hardik.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hardik.chatapp.API.APIController;
import com.hardik.chatapp.Models.APIResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPassword extends AppCompatActivity {
TextView email,resend,message;
Button send;
ProgressBar progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        initView();
        sendEmail();
        resendEmail();
    }

    private void resendEmail() {
        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.getText().toString().trim().isEmpty()){
                    email.setError("Write correct Email");
                }else{
                    message.setVisibility(View.VISIBLE);
                    progress.setVisibility(View.VISIBLE);
                    Call<APIResponse> call = APIController.getInstance().getApi().recover(email.getText().toString().trim());
                    call.enqueue(new Callback<APIResponse>() {
                        @Override
                        public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                            progress.setVisibility(View.GONE);
                            message.setText(response.body().getMessage());
                        }

                        @Override
                        public void onFailure(Call<APIResponse> call, Throwable t) {
                            progress.setVisibility(View.GONE);
                            message.setText("Please try again later..");
                        }
                    });
                }
            }
        });
    }

    private void sendEmail() {
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.getText().toString().trim().isEmpty()){
                    email.setError("Write correct Email");
                }else{
                    message.setVisibility(View.VISIBLE);
                    progress.setVisibility(View.VISIBLE);
                    Call<APIResponse> call = APIController.getInstance().getApi().recover(email.getText().toString().trim());
                    call.enqueue(new Callback<APIResponse>() {
                        @Override
                        public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                            progress.setVisibility(View.GONE);
                            resend.setVisibility(View.VISIBLE);
                            send.setVisibility(View.GONE);
                            message.setText(response.body().getMessage());
                        }

                        @Override
                        public void onFailure(Call<APIResponse> call, Throwable t) {
                            progress.setVisibility(View.GONE);
                            message.setText("Please try again later..");
                        }
                    });
                }
            }
        });
    }

    private void initView() {
        email = findViewById(R.id.forgot_password_email);
        resend = findViewById(R.id.forgot_password_resend);
        message = findViewById(R.id.forgot_password_message);
        send = findViewById(R.id.forgot_password_button);
    }
}