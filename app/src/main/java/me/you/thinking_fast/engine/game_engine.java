package me.you.thinking_fast.engine;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import me.you.thinking_fast.R;

import static android.content.Context.MODE_PRIVATE;
import static java.lang.Integer.*;

/**
 * Created by younes_hp on 3/10/2017.
 */

public class game_engine extends core {
   // private final Random rand;
    private final Context applicationContext;
    private final MediaPlayer whoo, correct,incorrect,incorrect3,click,win,lose,bubble_pop,extra_score,whoo2,Coin_Echo;


    public   level_entity current_level = null;
    private byte game_mode ;
    private int index = -1;
    private AQuery aquery;
    private static int stop_time_points = 0;


    on_ridds_loaded loaded_ridd;


    public riddle fetch_riddle(){

        try {
            if(index  < current_level.riddles_list.size() -1)
            index ++;

            return  current_level.riddles_list.get(index);
        }catch (Exception e)
        {
            Log.i("fetch_riddle","no Riddle Found");
            return  null;
        }
    }

    public void set_onLoaded_ridd(on_ridds_loaded loaded_ridd) {
        this.loaded_ridd = loaded_ridd;
    }

    public List<riddle>  get_riddle_list(){
        return  current_level.riddles_list;
    }

    public riddle get_current_riddle(){
        return  current_level.riddles_list.get(index);
    }
    public boolean is_riddle_end(){
        try {

            if (current_level.riddles_list.size() - 1 <= index)
                return true;
            else
                return false;

        }catch (Exception e)
        {
            return  false;
        }
    }

    public game_engine(Context applicationContext) {
       //  rand = new Random();
        this.applicationContext =applicationContext;
        SharedPreferences prefs = this.applicationContext.getSharedPreferences(my_app.MY_SETTING_SAVED, MODE_PRIVATE);
        stop_time_points = prefs.getInt("stopTime",3);



        whoo = MediaPlayer.create(applicationContext, R.raw.whoo_fast);
        whoo2 = MediaPlayer.create(applicationContext, R.raw.whoo_2);

        correct = MediaPlayer.create(applicationContext, R.raw.extra_score);
        incorrect = MediaPlayer.create(applicationContext, R.raw.incorrect);
        incorrect3 = MediaPlayer.create(applicationContext, R.raw.incorrect3);

        click = MediaPlayer.create(applicationContext, R.raw.click);
        win = MediaPlayer.create(applicationContext, R.raw.win);
        lose = MediaPlayer.create(applicationContext, R.raw.lose2);
        bubble_pop = MediaPlayer.create(applicationContext, R.raw.bubble_pop);
        extra_score = MediaPlayer.create(applicationContext, R.raw.extra_score);
        Coin_Echo = MediaPlayer.create(applicationContext, R.raw.coin_echo);


    }


   public void play_whoo(){
        if(my_app.sound_effect)
       whoo.start();
    }

    public void play_whoo2(){
        if(my_app.sound_effect)
            whoo2.start();
    }

    public void play_correct(){
        if(my_app.sound_effect)
            correct.start();
    }

    public void play_incorrect(){
        if(my_app.sound_effect)
            incorrect.start();
    }

    public void play_stoped(){
        if(my_app.sound_effect)
            incorrect3.start();
    }



    public void play_click(){
        if(my_app.sound_effect)
            click.start();
    }

    public void play_win(){
        if(my_app.sound_effect)
            win.start();
    }
    public void play_lose(){
        if(my_app.sound_effect)
            lose.start();
    }
    public void play_bubble_pop(){
        if(my_app.sound_effect)
            bubble_pop.start();
    }

    public void play_no_connection(){
        if(my_app.sound_effect)
            bubble_pop.start();
    }


    public void play_extra_score(){
        if(my_app.sound_effect)
            extra_score.start();
    }
    public void play_Coin_Echo(){
        if(my_app.sound_effect)
            Coin_Echo.start();
    }





    public  void reset_Stop_time_points() {

        SharedPreferences.Editor editor = this.applicationContext.getSharedPreferences(my_app.MY_SETTING_SAVED, MODE_PRIVATE).edit();
        editor.putInt("stopTime", 3);
        editor.commit();


    }

    public  int getStop_time_points() {
        return stop_time_points;
    }


    public  int add_Stop_time_points() {
        stop_time_points = stop_time_points +1;
        SharedPreferences.Editor editor = this.applicationContext.getSharedPreferences(my_app.MY_SETTING_SAVED, MODE_PRIVATE).edit();
        editor.putInt("stopTime", stop_time_points);
        editor.commit();

        return stop_time_points;
    }

    public  int sub_Stop_time_points() {
        if(stop_time_points > 0) {
            stop_time_points = stop_time_points - 1;
            SharedPreferences.Editor editor = this.applicationContext.getSharedPreferences(my_app.MY_SETTING_SAVED, MODE_PRIVATE).edit();
            editor.putInt("stopTime", stop_time_points);
            editor.commit();
        }
        return stop_time_points;
    }

    public void set_curtent_level(level_entity level){
        current_level = level;
       // current_level.riddles_list.clear();
    }


    public void load_riddles(){

         //current_level.riddles_list.clear();


        index = -1;
        if(current_level == null){
            return;
        }
        int levelnumber = current_level.getNum();


        current_level.riddles_list = my_app.mdb.get_riddle_List(current_level.riddle_count);

    }




    public void download_riddles(final Context context) {

        aquery = new AQuery(context);
        current_level.riddles_list =new ArrayList<riddle >();

        index = -1;
        if (current_level == null) {
            return;
        }
        int levelnumber = current_level.getNum();

        // current_level.riddles_list = my_app.mdb.get_riddle_List(levelnumber,1);

        String url = my_app.server_url + "servies/get_riddles.php?riddcount=" + current_level.getRiddle_count()
                + "&levelnum=" +current_level.getNum()
                + "&hardmode=" + my_app.get_game_sett_hardlevel() ;


                //  http://youstations.net/think_fast/

        Log.i("hex2Rgb",""+hex2Rgb("#FF00FF"));
        Log.i("Color.rgb",""+Color.rgb(255,0,255));

        aquery.ajax(url, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject object, AjaxStatus status) {
                if (object != null) {
                    try {
                        Log.i("json","fetch json Done");

                        JSONArray ridd_list = object.getJSONArray("ridd_list");

                            for (int i = 0; i < ridd_list.length(); i++) {
                                JSONObject r = (JSONObject) ridd_list.get(i);


                                current_level.riddles_list.add(new riddle(r.getInt("id"),r.getInt("rt"),r.getString("a1"),r.getString("a2"),r.getString("img"),r.getString("rtx"),(byte)r.getInt("st"),hex2Rgb(r.getString("c")),r.getInt("t"),(r.getInt("yn") == 1)?(true):(false),r.getInt("iml"),r.getInt("icl")));


                            }

                        Log.i("Riddle_downloaded_count"," " + current_level.riddles_list.size());


                        loaded_ridd.load_images();



                    } catch (JSONException e) {
                        Log.i("Len"," " + e.getMessage());
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                        loaded_ridd.conn_error(2);


                    }
                }else {
                    Log.i("Len", "not object");
                    loaded_ridd.conn_error(1);
                }
            }
        });
    }


    public static int hex2Rgb(String colorStr) {

        if(colorStr.length() <6)
            return 0;
        else
        return  Color.rgb(
                Integer.valueOf( colorStr.substring( 1, 3 ), 16 ),
                Integer.valueOf( colorStr.substring( 3, 5 ), 16 ),
                Integer.valueOf( colorStr.substring( 5, 7 ), 16 ) );

    }


    public   interface on_ridds_loaded  {
        public  void load_images();
        public  void conn_error(int state);

}
}

