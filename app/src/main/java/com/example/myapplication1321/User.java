package com.example.myapplication1321;

import java.util.ArrayList;

public class User {
    private String userName;
    private String password;
    private int imageId;

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public User() {

    }
    public User(String userName, String password, int imageId) {
        this.userName = userName;
        this.password = password;
        this.imageId = imageId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public boolean checkPassword(String username, String password){
        for(User user:this.getDataList()){
            if(username.equals(user.getUserName()) && password.equals(user.getPassword())){
                return true;
            }
        }
        return false;
    }
    public ArrayList<User> getDataList(){
        ArrayList<User> list = new ArrayList<User>();
        list.add(new User("aykut", "123",R.drawable.avatar1));
        list.add(new User("aykut2", "2",R.drawable.avatar1));
        list.add(new User("aykut3", "3",R.drawable.avatar2));
        return list;
    }
}
