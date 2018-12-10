package com.example.soyeonlee.myapplication8;

public class ListItem {

    private String date; // private이므로 외부에서 접근할수있도록 setter와 getter만듦
    private String text;

    //입력 시 편의를 위한 생성자
    public ListItem(String date, String text) {
        this.date = date;
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
