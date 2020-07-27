package me.you.thinking_fast;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import me.you.thinking_fast.canvas.gameCanvas;
import me.you.thinking_fast.custom.answer_area;
import me.you.thinking_fast.engine.core;
import me.you.thinking_fast.engine.my_app;

public class game extends AppCompatActivity {

    gameCanvas gameCan;
    boolean eng_loaded = false;
    static public Activity gameActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        my_app.current_activity = core.GAME_ACT;


        my_app.game_eng.play_Coin_Echo();
        getWindow().getDecorView().setSystemUiVisibility(
                // View.SYSTEM_UI_FLAG_IMMERSIVE
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        //     | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        //| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        //  | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        gameActivity = this;


        setContentView(R.layout.activity_game);
        gameCan = (gameCanvas)findViewById(R.id.gameCanvas);





        gameCan.display_riddle(my_app.game_eng.fetch_riddle(),true);




        gameCan.setAnswer_motionEnd(new answer_area.onAnswer_motionEnd() {
            @Override
            public void motion_end() {
                if (!my_app.game_eng.is_riddle_end()) {
                    gameCan.display_riddle(my_app.game_eng.fetch_riddle(),false);
                }
            }
        });








    }



    //   @Override
   public void onBackPressed() {
       gameCan.onBackPressed();
       super.onBackPressed();

   }



    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus)
        {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            Log.i("HasFoucs", "HasFoucs");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
                // Remember that you should never show the action bar if the
                // status bar is hidden, so hide that too if necessary.
                        // ActionBar actionBar = getActionBar();
                        // actionBar.hide();
    }
}
