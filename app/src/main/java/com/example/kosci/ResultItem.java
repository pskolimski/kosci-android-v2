package com.example.kosci;

public class ResultItem {
    private String nickName;
    private String date;
    private String points;

    public ResultItem(String nickName, String date, String points) {
        this.nickName = nickName;
        this.date = date;
        this.points = points;
    }

    public String getNickName() {
        return nickName;
    }

    public String getDate() {
        return date;
    }

    public String getPoints() {
        return points;
    }
}