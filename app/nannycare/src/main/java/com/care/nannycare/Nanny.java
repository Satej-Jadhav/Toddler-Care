package com.care.nannycare;

public class Nanny
{
    String nannyuser_id;
    String user_id;
    public Nanny() {}

    public String getNannyuser_id() {
        return nannyuser_id;
    }

    public void setNannyuser_id(String nannyuser_id) {
        this.nannyuser_id = nannyuser_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Nanny(String nannyuser_id, String user_id) {
        this.nannyuser_id = nannyuser_id;
        this.user_id = user_id;
    }
}
