package me.you.thinking_fast;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import me.you.thinking_fast.custom.circuler_rate_view;
import me.you.thinking_fast.engine.core;
import me.you.thinking_fast.engine.my_app;


public  class End_dialogfrag extends DialogFragment {
    int mNum;


	private long enty_id =0;
	private long AccountID =0;
	private long CustID = 0;
	private Boolean IsDirectAdd;
	private View targetView;
	private InputMethodManager inputManager;
	private MediaPlayer mp;
    private Button retry_btn;
    private Button main_btn;
    private View v;
    private int endstate;
    private TextView incorrect_answer;
    private TextView speed_pers;
    private TextView complish_pers;
    private LinearLayout ccfc;
    private ImageView good_img;
    private Animation anim1,anim2,anim3,anim4,anim5,anim6,anim7,anim8,anim9;
    private Paint backPaint1;
    private TextView stopCo;
    private LinearLayout stop_container;

    public int plus_stop_points =0;
    private circuler_rate_view circ;
    private float old_current_lvl_pers;


    /**
     * Create a new instance of MyDialogFragment, providing "num"
     * as an argument.
     */
    public static End_dialogfrag newInstance(int endstate) {
		End_dialogfrag f = new End_dialogfrag();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("endstate", endstate);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        endstate = getArguments().getInt("endstate");
        // Pick a style based on the num.
        this.getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


            backPaint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
            backPaint1.setAlpha(220);

            //style = R.style.AppTheme;
            setStyle(DialogFragment.STYLE_NO_FRAME, R.style.Alert_opt);

    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new Dialog(getActivity(), getTheme()){
            @Override
            public void onBackPressed() {
                //do your stuff
            }
        };
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {



        anim5= AnimationUtils.loadAnimation(getActivity(), R.anim.shaking_in);
        anim5.setDuration(500);
        anim5.setInterpolator(new AccelerateDecelerateInterpolator());
        anim6= AnimationUtils.loadAnimation(getActivity(), R.anim.shaking_in);
        anim6.setDuration(500);
        anim6.setInterpolator(new AccelerateDecelerateInterpolator());

        if(endstate == core.END_NO_POINT) {
            v = inflater.inflate(R.layout.fail_no_point_dial, container, false);
            anim5.setInterpolator(new AccelerateDecelerateInterpolator());
            anim6.setInterpolator(new AccelerateDecelerateInterpolator());
            anim5.setStartOffset(500);
            anim6.setStartOffset(1000);
        }else if(endstate == core.END_NO_EXTRA) {
            v = inflater.inflate(R.layout.fail_no_extra_dial, container, false);
            anim5.setInterpolator(new AccelerateDecelerateInterpolator());
            anim6.setInterpolator(new AccelerateDecelerateInterpolator());
            anim5.setStartOffset(500);
            anim6.setStartOffset(1000);
        }else if(endstate == core.END_LEVEL_SUSSES){
            if(my_app.game_eng.current_level.getNum() != -1) // off line
            old_current_lvl_pers = my_app.mdb.get_level_pers(my_app.game_eng.current_level.getNum());
            v = inflater.inflate(R.layout.succsess_dial, container, false);

            good_img = (ImageView)v.findViewById(R.id.good_img);

            incorrect_answer = (TextView)v.findViewById(R.id.incorrect_answer);
            speed_pers = (TextView)v.findViewById(R.id.speed_pers);
            complish_pers = (TextView)v.findViewById(R.id.complish_pers);

            incorrect_answer.setTypeface(my_app.mainFont);
            speed_pers.setTypeface(my_app.mainFont);
            complish_pers.setTypeface(my_app.mainFont);

            // ccfc = (LinearLayout)v.findViewById(R.id.ccf);
            stopCo = (TextView)v.findViewById(R.id.stopCo);

            // ccfc.setBackgroundC
            //
            //
            //
            // olor(my_app.game_eng.current_level.getTextColor());
            stop_container= (LinearLayout)v.findViewById(R.id.stop_container);
           //  circ = (circuler_rate_view)v.findViewById(R.id.lvl_pers);
         //   circ.set_rate(80);
            anim1= AnimationUtils.loadAnimation(getActivity(), R.anim.pup_zoom_in);
            anim1.setDuration(1000);
            anim1.setInterpolator(new AccelerateDecelerateInterpolator());
            good_img.setAnimation(anim1);

            float get_speed_pers = my_app.game_eng.current_level.get_speed_pers();
            int false_answer_count = my_app.game_eng.current_level.get_false_answer_count();

            anim2= AnimationUtils.loadAnimation(getActivity(), R.anim.right_in);
            anim2.setDuration(1000);
            anim2.setStartOffset(500);
            anim2.setInterpolator(new AccelerateDecelerateInterpolator());
            incorrect_answer.setAnimation(anim2);

            incorrect_answer.setText(  "- الاجابات الخاطئة: "  +  false_answer_count);

            anim3= AnimationUtils.loadAnimation(getActivity(), R.anim.right_in);
            anim3.setDuration(1000);
            anim3.setStartOffset(1000);
            anim3.setInterpolator(new AccelerateDecelerateInterpolator());
            speed_pers.setAnimation(anim3);
            speed_pers.setText("- سرعة الرد :" + String.format(java.util.Locale.US,"%.1f", get_speed_pers)  + " %");

            float complish = (get_speed_pers - ( false_answer_count * (100 / my_app.game_eng.current_level.getRiddle_count() ) ));
            complish_pers.setText("- نسبة الانجاز : " + String.format(java.util.Locale.US,"%.1f", complish)     + "%");


            if((complish - 100) >= 5){
                if(old_current_lvl_pers == -1)
                plus_stop_points = (int) ((complish - 100)/5);
                else
                    plus_stop_points= 1;
            }

            anim4= AnimationUtils.loadAnimation(getActivity(), R.anim.right_in);
            anim4.setDuration(1000);
            anim4.setStartOffset(1500);
            anim4.setInterpolator(new AccelerateDecelerateInterpolator());
            complish_pers.setAnimation(anim4);

            stopCo.setText(my_app.game_eng.getStop_time_points() + "");


            anim8= AnimationUtils.loadAnimation(getActivity(), R.anim.right_in_slow);

            anim9= AnimationUtils.loadAnimation(getActivity(), R.anim.pup_up_in);
            anim9.setStartOffset(3000);

            if(plus_stop_points >0) {
                stop_container.setAnimation(anim9);
                anim5.setStartOffset(3000 + (plus_stop_points * 500));
                anim6.setStartOffset(3500 + (plus_stop_points * 500));
            }else {
                stop_container.setVisibility(View.INVISIBLE);
                anim5.setStartOffset(3000);
                anim6.setStartOffset(3500);
            }
            anim9.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {

                        anim8.setDuration(500);
                      stopCo.startAnimation(anim8);


                Log.i("onAnimationEnd","onAnimationEnd");
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            anim8.setAnimationListener(new Animation.AnimationListener() {

                @Override
                public void onAnimationStart(Animation animation) {
                    if(plus_stop_points > 0 ) {
                       my_app.game_eng.play_extra_score();
                        plus_stop_points= plus_stop_points-1;
                        my_app.game_eng.add_Stop_time_points();
                        stopCo.setText(my_app.game_eng.getStop_time_points() + "");
                    }

                }


                @Override
                public void onAnimationEnd(Animation animation) {
                    if(plus_stop_points > 0 ) {

                        stopCo.startAnimation(anim8);
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });


            /////////////////////save level complete ////////////////////////``23`1
            if(my_app.game_eng.current_level.getNum() != -1)
                my_app.mdb.level_done(my_app.game_eng.current_level.getNum(), complish, my_app.get_game_sett_hardlevel());
            ////////////////////////////////////////////////////////////////



            anim7= AnimationUtils.loadAnimation(getActivity(), R.anim.right_in);
            anim7.setDuration(1000);
            anim7.setStartOffset( 0);



        }



        retry_btn = (Button)v.findViewById(R.id.retry_btn);
        main_btn = (Button)v.findViewById(R.id.main_btn);

        retry_btn.setAnimation(anim5);
        main_btn.setAnimation(anim6);

        retry_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {


                if(endstate == core.END_LEVEL_SUSSES) {
                    Intent intent = new Intent(game.gameActivity, levels.class);

                    game.gameActivity.startActivity(intent);
                 //   game.gameActivity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                }else {

                    Intent intent = new Intent(game.gameActivity.getApplicationContext(), transtion_activity.class);
                    intent.putExtra("game_state", "REPLAY_LVL");
                    intent.putExtra("LVL_NUM", my_app.sel_lvl);

                    game.gameActivity.startActivity(intent);
                    game.gameActivity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
            }
        });

        main_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(game.gameActivity.getApplicationContext(), start.class);

                game.gameActivity.startActivity(intent);
              //  game.gameActivity.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

            }
        });



        getDialog().getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

		return v;
    }


    @Override
    public void onCancel(DialogInterface dialog) {
    	// TODO Auto-generated method stub

    //	getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

    	
    	//MyApplication.closeInput(getActivity());

    	Log.i("","CLOOoooooooOOse");
    	super.onCancel(dialog);

    }
    

}
