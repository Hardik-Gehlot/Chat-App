package com.hardik.chatapp.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hardik.chatapp.Models.Message;
import com.hardik.chatapp.R;
import com.hardik.chatapp.Utils.Session;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ChatAdapter extends RecyclerView.Adapter{
    ArrayList<Message> data;
    Context context;
    int SENDER_VIEW_TYPE = 1;
    int RECIEVER_VIEW_TYPE = 2;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm aa");
    Calendar calendar = Calendar.getInstance();

    public ChatAdapter(ArrayList<Message> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==SENDER_VIEW_TYPE){
            View view = LayoutInflater.from(context).inflate(R.layout.sender_single_row,parent,false);
            return new SenderViewHolder(view);
        }else{
            View view = LayoutInflater.from(context).inflate(R.layout.reciever_single_row,parent,false);
            return new RecieverViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(data.get(position).getSender() == Session.getId(context)){
            return SENDER_VIEW_TYPE;
        }else{
            return RECIEVER_VIEW_TYPE;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message m = data.get(position);
        calendar.setTimeInMillis(Long.parseLong(""+m.getTime()));
        if(holder.getClass() == SenderViewHolder.class){
            ((SenderViewHolder)holder).smessage.setText(m.getMessage());
            ((SenderViewHolder)holder).stime.setText(simpleDateFormat.format(calendar.getTime()));
            switch (data.get(position).getStatus()){
                case 1:
                    ((SenderViewHolder)holder).status.setImageResource(R.drawable.ic_send);
                    break;
                case 2:
                    ((SenderViewHolder)holder).status.setImageResource(R.drawable.ic_recieve);
                    break;
                case 3:
                    ((SenderViewHolder)holder).status.setImageResource(R.drawable.ic_seen);
                    break;
            }
        }else{
            ((RecieverViewHolder)holder).rmessage.setText(m.getMessage());
            ((RecieverViewHolder)holder).rtime.setText(simpleDateFormat.format(calendar.getTime()));
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class SenderViewHolder extends RecyclerView.ViewHolder {
        TextView smessage, stime;
        ImageView status;
        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            smessage = itemView.findViewById(R.id.sender_single_row_message);
            stime = itemView.findViewById(R.id.sender_single_row_time);
            status = itemView.findViewById(R.id.sender_single_row_status);
        }
    }
    public class RecieverViewHolder extends RecyclerView.ViewHolder {
        TextView rmessage,rtime;
        public RecieverViewHolder(@NonNull View itemView) {
            super(itemView);
            rmessage = itemView.findViewById(R.id.reciever_single_row_message);
            rtime = itemView.findViewById(R.id.reciever_single_row_time);
        }
    }
}
