package me.you.thinking_fast;

import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.Locale;

import me.you.thinking_fast.engine.core;
import me.you.thinking_fast.engine.levels_adapter;
import me.you.thinking_fast.engine.my_app;

import static com.androidquery.util.AQUtility.getContext;

public class levels extends AppCompatActivity {

    private Animation show_option;
    private levels_adapter levAdep;
    private int opened_item = -1;
private View opened_view ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        set_locale("ar");

        show_option= AnimationUtils.loadAnimation(this, R.anim.slide_in_top);
        show_option.setDuration(500);
        show_option.setInterpolator(new AccelerateDecelerateInterpolator());

        setContentView(R.layout.activity_levels);

        ListView lv = (ListView) findViewById(R.id.lv_items);

        levAdep = new levels_adapter(getApplicationContext(),lv);
        lv.setAdapter(levAdep);

        levAdep.set_onStartPlayClicked(new levels_adapter.onStartPlayClicked() {
            @Override
            public void onStartPlayClickedListener(int num) {

                Intent intent = new Intent(getApplicationContext(), transtion_activity.class);
                intent.putExtra("game_state", "SELECT_LEVEL");
                intent.putExtra("LVL_NUM", num);

                startActivity(intent);
Log.i("sel_lvl"," " + num);


              overridePendingTransition(R.anim.in_rout_right,R.anim.out_rout_right);

            }
        });



        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("clicked",i + "_" +l + "");


                if (opened_item != -1)
                {
                    levAdep.hide_item(opened_item);
                    opened_view.findViewById(R.id.lvl_action).setVisibility(View.GONE);
                    opened_view.clearAnimation();
                }
               // levAdep.hide_all_action_par();
                levAdep.show_item(i);
                view.findViewById(R.id.lvl_action).setVisibility(View.VISIBLE);
                show_option.start();
                view.findViewById(R.id.lvl_action).setAnimation(show_option);

                opened_view = view;
                opened_item = i;
                my_app.game_eng.play_bubble_pop();
            }
        });




my_app.game_eng.play_whoo();
    }


    @Override
    public void onBackPressed() {


        if(my_app.current_activity == core.LEVELS_ACT)
        super.onBackPressed();
        else {
            Intent intent = new Intent(getApplicationContext(), start.class);
            startActivity(intent);
        }

    }

    @Override
    protected void onResume() {



        if(my_app.current_activity == core.GAME_ACT) {
            overridePendingTransition(R.anim.in_rout_left, R.anim.out_rout_left);
            my_app.game_eng.play_whoo();

        }
        super.onResume();

        View decorView = getWindow().getDecorView();
// Hide the status bar.
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

// Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.
        //     ActionBar actionBar = getActionBar();
        //  actionBar.hide();

    }



    public  void set_locale(String lang){
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getApplicationContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
    }
}
