package com.hardik.chatapp.Adapters;

import android.app.Dialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hardik.chatapp.ChatActivity;
import com.hardik.chatapp.Models.User;
import com.hardik.chatapp.R;
import com.hardik.chatapp.Utils.Session;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder>{
    ArrayList<User> data;

    public UsersAdapter(ArrayList<User> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_single_row,parent,false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersAdapter.UserViewHolder holder, int position) {
        holder.username.setText(data.get(position).getUsername());
        if(data.get(position).getMessage() == null){
            holder.lastMessage.setText("Click to start chats");
        }else{
            if(Session.getId(holder.profile.getContext()) == data.get(position).getSender()){
                holder.status.setVisibility(View.VISIBLE);
                holder.lastMessage.setText(data.get(position).getMessage());
                switch (data.get(position).getStatus()){
                    case 1:
                        holder.status.setImageResource(R.drawable.ic_send);
                        break;
                    case 2:
                        holder.status.setImageResource(R.drawable.ic_recieve);
                        break;
                    case 3:
                        holder.status.setImageResource(R.drawable.ic_seen);
                        break;
                }
            }else{
                holder.lastMessage.setText(data.get(position).getMessage());
            }
        }
        Glide.with(holder.profile.getContext()).load(data.get(position).getProfile_img()).into(holder.profile);
        holder.profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(holder.username.getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.profile_dialog);
                dialog.setCancelable(true);

                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

                ((ImageView) dialog.findViewById(R.id.profile_dialog_close)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                ((TextView) dialog.findViewById(R.id.profile_dialog_username)).setText(data.get(position).getUsername());
                Glide.with(holder.profile.getContext()).load(data.get(position).getProfile_img()).into(((ImageView) dialog.findViewById(R.id.profile_dialog_image)));
                dialog.show();
                dialog.getWindow().setAttributes(lp);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.username.getContext(), ChatActivity.class);
                intent.putExtra("reciever",data.get(position).getId());
                intent.putExtra("username",data.get(position).getUsername());
                intent.putExtra("lastSeen",data.get(position).getLastSeen());
                intent.putExtra("profile",data.get(position).getProfile_img());
                holder.username.getContext().startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return data.toArray().length;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder{
        CircleImageView profile;
        ImageView status;
        TextView username,lastMessage;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            profile = itemView.findViewById(R.id.users_single_row_profile);
            username = itemView.findViewById(R.id.users_single_row_name);
            lastMessage = itemView.findViewById(R.id.users_single_row_lastmsg);
            status = itemView.findViewById(R.id.users_single_row_status_icon);
        }
    }
}
