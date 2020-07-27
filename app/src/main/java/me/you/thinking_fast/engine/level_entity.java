package me.you.thinking_fast.engine;


import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class level_entity{
    int num;
    int color1;
    int color2;
    int textColor;
    int riddle_count;

   int need_points_count;
    int extra_fail_points;
    int  drop_count;

    float man_pers;
    float child_pers;

    int time_ms;

    public List<riddle> riddles_list = null;



    /////////////////////////UI////////////////////
    public boolean show_options = false;

    public level_entity(int num, int color1, int color2, int textColor, int need_points_count, int extra_fail_points, int drop_count, float man_pers , float child_pers,int time_ms) {
        this.num = num;
        this.color1 = color1;
        this.color2 = color2;
        this.textColor = textColor;
        this.drop_count = drop_count;
        this.riddle_count = need_points_count + extra_fail_points + (extra_fail_points *drop_count);
        this.need_points_count = need_points_count;
        this.extra_fail_points = extra_fail_points;

        this.time_ms = time_ms;
        this.man_pers = man_pers;
        this.child_pers = child_pers;


    }

    public int getTime_ms() {
        return time_ms;
    }


    public int getNum() {
        return num;
    }

    public int getColor1() {
        return color1;
    }

    public int getColor2() {
        return color2;
    }

    public int getTextColor() {
        return textColor;
    }

     public int get_false_answer_count(){
      int ff=0;
        for(int i =0 ; i<riddles_list.size();i++ ){
          if(riddles_list.get(i).getState() == core.RS_FALSE_ANSWERED ) {
                ff = ff + 1;
            }

        //  Log.i("state" + i,"state--:"+riddles_list.get(i).getState() );
      }
    return ff;
}

    public int getDrop_count() {
        return drop_count;
    }

    public int get_imgsRidCount(){
        int ff=0;
        for(int i =0 ; i<riddles_list.size();i++ ){
            if (riddles_list.get(i).getType() ==3 ||riddles_list.get(i).getType() ==4 ){
                ff = ff + 1;
            }

            //  Log.i("state" + i,"state--:"+riddles_list.get(i).getState() );
        }
        return ff;
    }





    public float  get_speed_pers(){

        double req_timetot=0,spendtimeTot=0;
        for(int i =0 ; i<riddles_list.size();i++){
            if(riddles_list.get(i).getState() == core.RS_TRUE_ANSWER && !riddles_list.get(i).isStop_time() ) {
                if(i!=0){
                req_timetot = req_timetot +time_ms;
                spendtimeTot = spendtimeTot + riddles_list.get(i).getSpendTime();

            }}
            //  Log.i("state" + i,"state--:"+riddles_list.get(i).getState() );
        }


        return (float) (((req_timetot - spendtimeTot)/(req_timetot/2)) * 100);



    }

    public int getRiddle_count() {
        return riddle_count;
    }

    public int getNeed_points_count() {
        return need_points_count;
    }

    public int getExtra_fail_points() {
        return extra_fail_points;
    }



    public float getChild_pers() {
        return child_pers;
    }

    public float getMan_pers() {
        return man_pers;
    }


}


