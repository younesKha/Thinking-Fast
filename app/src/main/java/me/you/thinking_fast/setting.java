package me.you.thinking_fast;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import me.you.thinking_fast.engine.my_app;

public class setting extends AppCompatActivity {

    private Button btn_hard;
    private Button btn_easy;
    private ImageView level_Img;
    private SharedPreferences.Editor editor;
    private Switch SwSound;
    private TextView lvl_desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        setContentView(R.layout.activity_setting);

        my_app.game_eng.play_whoo();


        btn_easy =  (Button)findViewById(R.id.btn_easy);
        btn_hard =  (Button)findViewById(R.id.btn_hard);
        level_Img =  (ImageView)findViewById(R.id.iv_level);
        SwSound = (Switch)findViewById(R.id.switch_sound);

        lvl_desc= (TextView)findViewById(R.id.lvl_desc);




        SharedPreferences prefs =getSharedPreferences(my_app.MY_SETTING_SAVED, MODE_PRIVATE);
        int gLevel = prefs.getInt("level",1);
        Boolean IsSound = prefs.getBoolean("sound",true);
        editor = prefs.edit();


        if (gLevel == 1 ) {
            btn_easy.setSelected(false);
            btn_hard.setSelected(true);
            level_Img.setImageDrawable(getResources().getDrawable(R.mipmap.man));

            lvl_desc.setText(getResources().getText(R.string.man_desc));
        }else{
            btn_easy.setSelected(true);
            btn_hard.setSelected(false);
            level_Img.setImageDrawable(getResources().getDrawable(R.mipmap.child));
            lvl_desc.setText(getResources().getText(R.string.child_desc));

        }
        btn_easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_easy.setSelected(true);
                btn_hard.setSelected(false);
                editor.putInt("level", 2);
                editor.commit();
                level_Img.setImageDrawable(getResources().getDrawable(R.mipmap.child));
                lvl_desc.setText(getResources().getText(R.string.child_desc));

                my_app.change_man_img = true;
            }
        });

        btn_hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_easy.setSelected(false);
                btn_hard.setSelected(true);
                editor.putInt("level", 1);
                editor.commit();
                level_Img.setImageDrawable(getResources().getDrawable(R.mipmap.man));
                lvl_desc.setText(getResources().getText(R.string.man_desc));

                my_app.change_man_img = true;


            }
        });

        SwSound.setChecked(IsSound);
        my_app.sound_effect = IsSound;

        SwSound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                editor.putBoolean("sound", b);
                editor.commit();
                my_app.sound_effect = b;

            }
        });
    }


    @Override
    protected void onStop() {
        super.onStop();

        if(my_app.change_man_img)
        {


        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);


// Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.
        //     ActionBar actionBar = getActionBar();
        //  actionBar.hide();

    }
}
