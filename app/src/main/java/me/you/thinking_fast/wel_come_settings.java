package me.you.thinking_fast;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import me.you.thinking_fast.engine.my_app;

import static me.you.thinking_fast.engine.core.RTYPE_TEXT;
import static me.you.thinking_fast.engine.core.RTYPE_TEXTANDIMAGE;


public  class wel_come_settings extends DialogFragment {
    int mNum;
    private View v;
    private Button btn_easy;
    private Button btn_hard;
    private ImageView level_Img;
    private SharedPreferences.Editor editor;
    private TextView lvl_desc;
    private LinearLayout save_sett;
    private Button save_sett_b;
    private LinearLayout well_sett_content;
    private Animation anim_out;
    private LinearLayout well_sett_content2;
    private Animation anim_in;



    private setondismiss m_setondismiss;
    private TextView chage_set;

    /**
     * Create a new instance of MyDialogFragment, providing "num"
     * as an argument.
     */
    public static wel_come_settings newInstance() {
		wel_come_settings f = new wel_come_settings();



        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        my_app.game_eng.play_whoo();


        // Pick a style based on the num.
        this.getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);



            //style = R.style.AppTheme;
            setStyle(DialogFragment.STYLE_NORMAL, R.style.wellcome_sett);


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

            v = inflater.inflate(R.layout.wel_come, container, false);

        btn_easy =  (Button)v.findViewById(R.id.btn_easy);
        btn_hard =  (Button)v.findViewById(R.id.btn_hard);
        level_Img =  (ImageView)v.findViewById(R.id.iv_level);
        lvl_desc= (TextView)v.findViewById(R.id.lvl_desc);
        save_sett =  (LinearLayout)v.findViewById(R.id.save_sett);
        save_sett_b =  (Button)v.findViewById(R.id.save_sett_b);
        well_sett_content =  (LinearLayout)v.findViewById(R.id.well_sett_content);
        well_sett_content2 =  (LinearLayout)v.findViewById(R.id.well_sett_content2);
        chage_set =  (TextView)v.findViewById(R.id.chage_set);

        chage_set.setTypeface(my_app.font3);


        SharedPreferences prefs =start.handler.getSharedPreferences(my_app.MY_SETTING_SAVED, Context.MODE_PRIVATE);
        editor = prefs.edit();
        btn_easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_easy.setSelected(true);
                btn_hard.setSelected(false);
                editor.putInt("level", 2);
                editor.commit();
                level_Img.setImageDrawable(getResources().getDrawable(R.mipmap.child));
                lvl_desc.setText(getResources().getText(R.string.child_desc));
                save_sett.setVisibility(View.VISIBLE);
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
                save_sett.setVisibility(View.VISIBLE);
                my_app.change_man_img = true;
            }
        });

        anim_out= AnimationUtils.loadAnimation(getActivity(), R.anim.slide_out_right);
        anim_in= AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_left);

        anim_out.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                well_sett_content2.startAnimation(anim_in);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        anim_in.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dismiss();

                    }
                }, 3000); // 1 se
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });



        save_sett_b.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View view) {
                save_sett_b.setSelected(true);
                well_sett_content.startAnimation(anim_out);

                save_sett_b.setOnClickListener(null);



          //      save_sett.setVisibility(View.GONE);

            }
        });
        getDialog().getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

		return v;
    }

    private  float setTextSizeForWidth(Paint textPaint,String text) {



            final float testTextSize = 20f;

            textPaint.setTextSize(testTextSize);
            Rect bounds = new Rect();
            textPaint.getTextBounds(text, 0, text.length(), bounds);



return testTextSize * (my_app.game_width) / (bounds.width());
    }


    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        m_setondismiss.dismiss_done();

    }
    @Override
    public void onCancel(DialogInterface dialog) {
    	// TODO Auto-generated method stub

    //	getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

    	
    	//MyApplication.closeInput(getActivity());

    	Log.i("","CLOOoooooooOOse");
    	super.onCancel(dialog);

    }


    public void setM_setondismiss(setondismiss m_setondismiss) {
        this.m_setondismiss = m_setondismiss;
    }


    public interface  setondismiss{
        public void dismiss_done();
    }


}
