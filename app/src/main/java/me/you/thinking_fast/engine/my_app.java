package me.you.thinking_fast.engine;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.view.View;

import me.you.thinking_fast.canvas.gameCanvas;
import me.you.thinking_fast.canvas.game_options;
import me.you.thinking_fast.data.dbAdapter;
import me.you.thinking_fast.game;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by younes_hp on 3/26/2017.
 */

public class my_app  {
    public  static Context context ;
    public  static int game_width =0;
    public  static int game_Height =0;
    public  static int center_width =0;
    public  static int center_Height =0;

    public  static int wheel_rid =0;
    public  static boolean stop_answering = false;
    public  static String MY_SETTING_SAVED = "settPrefsFile";
    public  static   game_engine game_eng;
    public  static  int vvv= 1;
    public static dbAdapter mdb;
    public static String game_state;
    public static Typeface mainFont,secandFont,font3;
    public static int sel_lvl;
    public static boolean change_man_img = false;
    public static boolean sound_effect = true;


    //  public static String server_url = "http://10.0.2.2/think_fast/";
    public static String server_url = "http://youstations.net/think_fast/";
    public static int progress;
    public static int current_activity;
    //public static String mainFont = "fonts/FrutigerLTArabic.ttf";
    //public static String mainFont = "fonts/HelveticaWorld-Bold.ttf";
   // public static String mainFont = "fonts/Far_Casablanca.ttf";
    //public static String mainFont = "fonts/DIN_NEXT.otf";


    public  void myapp() {


    }



    public static Boolean  Show_fail_Dialog(FragmentManager fm){
        //mStackLevel++;
        int mStackLevel = 0;
        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        FragmentTransaction ft = fm.beginTransaction();
        Fragment prev = fm.findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        game_options newFragment = game_options.newInstance(mStackLevel);
        newFragment.show(ft, "dialog");
               // showDialog(ft, "dialog",targetView,enty_ID,accid,custid,isDirectAdd);

        return true;

    }

    public static void setgame_eng(game_engine game_eng) {
        my_app.game_eng = game_eng;
    }

    public static int get_game_sett_hardlevel() {
        SharedPreferences prefs = context.getSharedPreferences(my_app.MY_SETTING_SAVED, MODE_PRIVATE);
        return  prefs.getInt("level", 0);
    }


    public static boolean get_game_sett_play_soundEff() {
        SharedPreferences prefs = context.getSharedPreferences(my_app.MY_SETTING_SAVED, MODE_PRIVATE);
        return prefs.getBoolean("sound",true);
    }









}
