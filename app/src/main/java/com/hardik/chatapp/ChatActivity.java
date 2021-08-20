package com.hardik.chatapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hardik.chatapp.API.APIController;
import com.hardik.chatapp.Adapters.ChatAdapter;
import com.hardik.chatapp.Models.APIResponse;
import com.hardik.chatapp.Models.Message;
import com.hardik.chatapp.Models.MessageResponse;
import com.hardik.chatapp.Utils.Session;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {
ImageView send,emoji,attachment,mic;
CircleImageView profileImg,scrollToEnd;
EditText message;
RecyclerView recyclerView;
ChatAdapter adapter;
TextView username,lastSeen,badgeNum;
LinearLayoutManager linearLayoutManager;
int reciever,mid=0,badgeNumber=0;
String name,profile;
long last;
ArrayList<Message> data;
myThread t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getSupportActionBar().hide();

        initilize();
        setToolbar();
        getChats();
        sendMessage();
        onTextChanged();
        scrollToEnd();
        onScrollRecyclerView();
    }

    private void onScrollRecyclerView() {
        recyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(linearLayoutManager.findLastVisibleItemPosition() == (data.size()-1)){
                    scrollToEnd.setVisibility(View.GONE);
                    badgeNum.setVisibility(View.GONE);
                    badgeNumber = 0;
                }
            }
        });
    }

    private void scrollToEnd() {
        scrollToEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayoutManager.scrollToPosition(data.size()-1);
                scrollToEnd.setVisibility(View.GONE);
                badgeNum.setVisibility(View.GONE);
                badgeNumber = 0;
            }
        });
    }

    private void getChats() {
        t = new myThread();
        t.start();
    }

    private void onTextChanged() {
        message.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                linearLayoutManager.scrollToPosition(data.size()-1);
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(message.getText().toString().isEmpty()){
                    send.setVisibility(View.GONE);
                    mic.setVisibility(View.VISIBLE);
                    attachment.setVisibility(View.VISIBLE);
                }else{
                    send.setVisibility(View.VISIBLE);
                    mic.setVisibility(View.GONE);
                    attachment.setVisibility(View.GONE);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void setToolbar() {
        username.setText(name);
        Glide.with(this).load(profile).into(profileImg);
        if(last == 1){
            lastSeen.setText("Online");
        }else{
            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm aa");
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(last);
            lastSeen.setText("last seen "+dateFormat.format(cal.getTime()));
        }
    }

    private void sendMessage() {
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = message.getText().toString().trim();
                if(!msg.isEmpty()){
                    message.setText("");
                    Call<APIResponse> call = APIController.getInstance().getApi().sendMessage(Session.getId(getApplicationContext()),reciever,msg);
                    call.enqueue(new Callback<APIResponse>() {
                        @Override
                        public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                            if(response.body().getStatus() == 1){

                            }
                        }
                        @Override
                        public void onFailure(Call<APIResponse> call, Throwable t) {

                        }
                    });
                }
            }
        });
    }

    private void initilize() {
        message = findViewById(R.id.chat_message);
        send = findViewById(R.id.chat_send);
        profileImg = findViewById(R.id.chat_profile);
        username = findViewById(R.id.chat_username);
        lastSeen = findViewById(R.id.chat_last_seen);
        recyclerView = findViewById(R.id.chat_recyclerView);
        emoji = findViewById(R.id.chat_emoji);
        attachment = findViewById(R.id.chat_attachment);
        mic = findViewById(R.id.chat_mic);
        scrollToEnd = findViewById(R.id.scrollToEnd);
        badgeNum = findViewById(R.id.chat_badge);
        data = new ArrayList<>();
        mid = 0;
        adapter = null;

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(false);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        reciever = getIntent().getIntExtra("reciever",0);
        name = getIntent().getStringExtra("username");
        profile = getIntent().getStringExtra("profile");
        last = getIntent().getLongExtra("lastSeen",0);
    }

    class myThread extends Thread {
        @Override
        public void run() {
            super.run();
            Call<MessageResponse> call = APIController.getInstance().getApi().getMessages(Session.getId(getApplicationContext()),reciever,mid,"ASC");
            call.enqueue(new Callback<MessageResponse>() {
                @Override
                public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                    MessageResponse result = response.body();
                    if(result.getStatus() == 1){
                        if(data.isEmpty()){
                            data = result.getData();
                            mid = data.get(data.size() - 1).getMid();
                            adapter = new ChatAdapter(data,getApplicationContext());
                            recyclerView.setAdapter(adapter);
                        }else{
                            for(Message m : result.getData()){
                                mid = m.getMid();
                                data.add(m);
                                mid = data.get(data.size() - 1).getMid();
                                adapter.notifyItemInserted(data.size() - 1);
                            }
                            int lastVisibleId = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                            if(lastVisibleId == (data.size()-2)){
                                linearLayoutManager.scrollToPosition(data.size() - 1);
                            }else{
                                if(Session.getId(getApplicationContext()) != result.getData().get(0).getSender()){
                                    scrollToEnd.setVisibility(View.VISIBLE);
                                    badgeNum.setVisibility(View.VISIBLE);
                                    badgeNumber += result.getData().size();
                                    badgeNum.setText(""+badgeNumber);
                                }
                            }

                        }
                    }
                    getChats();
                }
                @Override
                public void onFailure(Call<MessageResponse> call, Throwable t) {
                    Toast.makeText(ChatActivity.this, "network problem", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}