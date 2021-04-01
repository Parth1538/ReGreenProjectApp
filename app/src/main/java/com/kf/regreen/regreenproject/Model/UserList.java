package com.kf.regreen.regreenproject.Model;

import java.io.Serializable;

public class UserList implements Serializable {

    public String que_id = "";
    public String que_title = "";
    public String que_description = "";
    public String que_image_url_1 = "";
    public String que_image_url_2 = "";
    public String que_image_url_3 = "";
    public String que_image_url_4 = "";
    public String que_date = "";
    public String que_answer = "";

    // Todo 0= Pending, 1= Completed
    public int que_status = 0;

    public String user_id = "";
    public String user_name = "";
    public String user_pic = "";
    public String user_email = "";
    public String user_phoneno = "";
    public String expert_name = "";

    public void setQue_id(String que_id) {
        this.que_id = que_id;
    }

    public String getQue_id() {
        return que_id;
    }

    public void setQue_title(String que_title) {
        this.que_title = que_title;
    }

    public String getQue_title() {
        return que_title;
    }

    public void setQue_description(String que_description) {
        this.que_description = que_description;
    }

    public String getQue_description() {
        return que_description;
    }

    public void setQue_image_url_1(String que_image_url_1) {
        this.que_image_url_1 = que_image_url_1;
    }

    public String getQue_image_url_1() {
        return que_image_url_1;
    }

    public void setQue_image_url_2(String que_image_url_2) {
        this.que_image_url_2 = que_image_url_2;
    }

    public String getQue_image_url_2() {
        return que_image_url_2;
    }

    public void setQue_image_url_3(String que_image_url_3) {
        this.que_image_url_3 = que_image_url_3;
    }

    public String getQue_image_url_3() {
        return que_image_url_3;
    }

    public void setQue_image_url_4(String que_image_url_4) {
        this.que_image_url_4 = que_image_url_4;
    }

    public String getQue_image_url_4() {
        return que_image_url_4;
    }

    public void setQue_date(String que_date) {
        this.que_date = que_date;
    }

    public String getQue_date() {
        return que_date;
    }

    public void setQue_status(int que_status) {
        this.que_status = que_status;
    }

    public int getQue_status() {
        return que_status;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_pic(String user_pic) {
        this.user_pic = user_pic;
    }

    public String getUser_pic() {
        return user_pic;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_phoneno(String user_phoneno) {
        this.user_phoneno = user_phoneno;
    }

    public String getUser_phoneno() {
        return user_phoneno;
    }

    public void setQue_answer(String que_answer) {
        this.que_answer = que_answer;
    }

    public String getQue_answer() {
        return que_answer;
    }

    public void setExpert_name(String expert_name) {
        this.expert_name = expert_name;
    }

    public String getExpert_name() {
        return expert_name;
    }
}
