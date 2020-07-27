package me.you.thinking_fast;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import me.you.thinking_fast.custom.PlayGifView;
import me.you.thinking_fast.engine.my_app;

public class conn_error_act extends AppCompatActivity {
    public static Activity handler;
    private Button btn_try_conn;
    private Button btnPlay_offline;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        setContentView(R.layout.act_connection_error);
            my_app.game_eng.play_bubble_pop();

        btn_try_conn =(Button)findViewById(R.id.btn_try_conn);
        btnPlay_offline =(Button)findViewById(R.id.btn_play_off_line);





        btnPlay_offline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), transtion_activity.class);
                intent.putExtra("game_state", "OFFLINE");
                intent.putExtra("LVL_NUM",1);

                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });


        btn_try_conn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), transtion_activity.class);
                intent.putExtra("game_state", my_app.game_state);
                intent.putExtra("LVL_NUM",my_app.sel_lvl);


                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });



       if( my_app.mdb.get_ridd_count()  >50){



           final Handler h = new Handler();
           h.postDelayed(new Runnable() {
               Animation anim1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.right_in_slow);

               @Override
               public void run() {
                   btnPlay_offline.setVisibility(View.VISIBLE);
                   btnPlay_offline.setVisibility(View.VISIBLE);
                   btnPlay_offline.startAnimation(anim1);
               }
           }, 2000);

       }else
       {
           btnPlay_offline.setVisibility(View.INVISIBLE);

       }

    }


    @Override
    protected void onResume() {
        super.onResume();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

    }
}