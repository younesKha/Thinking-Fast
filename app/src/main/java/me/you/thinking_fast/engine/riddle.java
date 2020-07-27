package me.you.thinking_fast.engine;

import android.util.Log;
import android.widget.ImageView;

/**
 * Created by younes_hp on 3/17/2017.
 */

public class riddle extends  core{
    int id;
    int type;
    String answer1,answer2;
    int trueAnswer;
    String img = null;
    String riddleText;
    byte shap;
    private int color;
    private byte state;
    private long req_time;
    private long spendTime;
    public boolean is_yes_no_qus;
    public boolean stop_time;



    private int  is_man_lvl;
    private int  is_child_lvl;


    public riddle(int id, int type, String answer1, String answer2, String img, String riddleText, byte shap, int color,long req_time,boolean is_yes_no_qus,int is_man_lvl,int is_child_lvl) {
        this.id = id;
        this.type = type;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.trueAnswer = 1;
        this.img = img;
        this.riddleText = riddleText;
        this.shap = shap;
        this.color = color;
        this.req_time=req_time;
        spendTime = 0;
        state = RS_NOT_ANSWERED;
        this.is_yes_no_qus =is_yes_no_qus;
        stop_time =false;

        this.is_man_lvl = is_man_lvl;
        this.is_child_lvl = is_child_lvl;
    }


    public void setspendTime(long spendTime) {
        this.spendTime = spendTime;
    }

    public  void swap_answer(){
        String sn = this.answer1;

        this.answer1 = this.answer2;
        this.answer2 = sn;

        this.trueAnswer = 2;
    }

    public void change_state(byte state,long spendTime){
        this.state = state;
        this.spendTime  = spendTime;
        Log.i("SpendTime","time:" +spendTime);
    }
    public long getSpendTime() {
        return spendTime;
    }
    public int getId() {
        return id;
    }
    public int getType() {
        return type;
    }
    public String getAnswer1() {
        return answer1;
    }
    public String getAnswer2() {
        return answer2;
    }
    public int getTrueAnswer() {
        return trueAnswer;
    }
    public String getImg() {
        return img;
    }
    public String getRiddleText() {
        return riddleText;
    }
    public byte getShap() {
        return shap;
    }
    public int getColor() {
        return color;
    }
    public byte getState() {
        return this.state;
    }
    public boolean isStop_time() {
        return stop_time;
    }
    public void setStop_time(boolean stop_time) {
        this.stop_time = stop_time;
    }

    public boolean is_yes_no_qus() {return is_yes_no_qus;}

    public long getReq_time() {
        return req_time;
    }

    public int getIs_man_lvl() {
        return is_man_lvl;
    }

    public int getIs_child_lvl() {
        return is_child_lvl;
    }



}