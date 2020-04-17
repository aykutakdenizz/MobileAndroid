package com.example.myapplication1321;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {
    ArrayList<User> userList;
    LayoutInflater inflater;
    Context context;

    public UserAdapter(Context context, ArrayList<User> users){
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.userList = users;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = inflater.inflate(R.layout.item_user_card, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position){
        User selectedUser = userList.get(position);
        holder.setData(selectedUser, position);
    }
    @Override
    public int getItemCount(){ return userList.size();}

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView userNameText,userName,userPasswordText,userPassword;
        ImageView userImage;
        CheckBox checkBox;

        public MyViewHolder(View itemView){
            super(itemView);
            userNameText = (TextView) itemView.findViewById(R.id.userNameText);
            userName = (TextView) itemView.findViewById(R.id.userName);
            userPasswordText = (TextView) itemView.findViewById(R.id.userPasswordText);
            userPassword = (TextView) itemView.findViewById(R.id.userPassword);
            userImage = (ImageView) itemView.findViewById(R.id.userImage);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkBox) ;
            checkBox.setOnClickListener(this);
        }

        public void setData(User selectedUser, int position){
            this.userImage.setImageResource(selectedUser.getImageId());
            this.userName.setText(selectedUser.getUserName());
            this.userPassword.setText(selectedUser.getPassword());
            this.checkBox.setChecked(true);
        }
        public void onClick(View v){
            if(checkBox.isChecked()){
                Toast.makeText(context,"VISIBLE",Toast.LENGTH_SHORT).show();
                userPassword.setVisibility(View.VISIBLE);
            }
            else{
                Toast.makeText(context,"INVISIBLE",Toast.LENGTH_SHORT).show();
                userPassword.setVisibility(View.INVISIBLE);
            }
        }
    }
}
