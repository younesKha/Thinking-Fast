package me.you.thinking_fast;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;

import java.util.Locale;

import me.you.thinking_fast.canvas.startCanvas;
import me.you.thinking_fast.custom.start_btn;
import me.you.thinking_fast.data.dbAdapter;
import me.you.thinking_fast.engine.core;
import me.you.thinking_fast.engine.game_engine;
import me.you.thinking_fast.engine.my_app;

import static me.you.thinking_fast.engine.core.START_ACT;

public class start extends AppCompatActivity {
    public static Activity handler;

    private startCanvas startC;
    private boolean completed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        getWindow().getDecorView().setSystemUiVisibility(
                // View.SYSTEM_UI_FLAG_IMMERSIVE
                          View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                  //     | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                //| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
              //  | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
               | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
               );



        set_locale("ar");

        if(  my_app.mdb  == null)
        my_app.mdb = new dbAdapter(getApplicationContext());
        my_app.mdb.open();


        handler = this;

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        my_app.context= getApplicationContext();

        my_app.game_width =size.x;
        my_app.game_Height = size.y;


        my_app.center_width =size.x/2;
        my_app.center_Height = size.y/2;
         completed =  my_app.mdb.is_all_lvl_complete();

        if( my_app.mainFont  == null) my_app.mainFont = Typeface.createFromAsset(getAssets(),"fonts/hacen_liner.ttf");
        if( my_app.secandFont  == null) my_app.secandFont =Typeface.createFromAsset(getAssets(),"fonts/NeoSansArabic.ttf");
        if( my_app.font3  == null)  my_app.font3 =Typeface.createFromAsset(getAssets(),"fonts/DIN_NEXT.otf");

        my_app.sound_effect =my_app.get_game_sett_play_soundEff();

            setContentView(R.layout.activity_start);

            startC =  (startCanvas)findViewById(R.id.startCanves);
            startC.mstartbrn.set_onAnimation_end_listiner(new start_btn.onAnimation_end_listiner() {
                @Override
                public void onAnimation_end_listiner() {


                    my_app.game_eng.play_click();
                    Intent intent;
                    if (my_app.progress  == 0 || completed) {
                        intent = new Intent(getApplicationContext(), levels.class);
                        my_app.current_activity = core.LEVELS_ACT;
                    }else
                    {
                        intent = new Intent(getApplicationContext(), transtion_activity.class);
                        intent.putExtra("game_state", "NEXT_LVL");
                        intent.putExtra("LVL_NUM", 0);
                        my_app.current_activity = core.LOADING_ACT;
                    }


                    startC.startgame();
                    startActivity(intent);

                    if (my_app.progress  == 0 || completed)
                        overridePendingTransition(R.anim.in_rout_left,R.anim.out_rout_left);
                    else
                        overridePendingTransition(R.anim.in_rout_right,R.anim.out_rout_right);




                }
            });






        if (my_app.game_eng == null)
        my_app.game_eng = new game_engine(getApplicationContext());



        if(my_app.get_game_sett_hardlevel() == 0) {
        final Handler h = new Handler();
        h.postDelayed(new Runnable() {
            private long time = 0;

            @Override
            public void run() {
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    Fragment prev = fm.findFragmentByTag("wellcome");
                    if (prev != null) {
                        ft.remove(prev);
                    }
                    ft.addToBackStack(null);

                    // Create and show the dialog.
                    wel_come_settings newFragment = wel_come_settings.newInstance();

                    newFragment.show(ft, "wellcome");


                    newFragment.setM_setondismiss(new wel_come_settings.setondismiss() {
                        @Override
                        public void dismiss_done() {
                            startC.resume();
                        }
                    });
            }
        }, 2000);
        }

    }

    public  void set_locale(String lang){
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getApplicationContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startC.onPressBack();
    }

    @Override
    protected void onResume() {


        if(my_app.current_activity == core.LEVELS_ACT)
        overridePendingTransition(R.anim.in_rout_right,R.anim.out_rout_right);
        else if(my_app.current_activity == core.LOADING_ACT || my_app.current_activity == core.GAME_ACT)
            overridePendingTransition(R.anim.in_rout_left,R.anim.out_rout_left);
        else if(my_app.current_activity == core.SETT_ACT || my_app.current_activity == core.INFO_ACT || my_app.current_activity == core.SHARE_ACT )
            overridePendingTransition(0,R.anim.slide_out_down);

        my_app.game_eng.play_whoo();


        my_app.current_activity = START_ACT;




       if(my_app.change_man_img) {
           startC.resume();
           completed = my_app.mdb.is_all_lvl_complete();
           my_app.change_man_img = false;
       }

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        super.onResume();

    }
}
