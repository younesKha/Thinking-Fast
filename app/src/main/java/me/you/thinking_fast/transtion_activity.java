package me.you.thinking_fast;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Locale;

import me.you.thinking_fast.canvas.startCanvas;
import me.you.thinking_fast.custom.trans_loading_ridd_view;
import me.you.thinking_fast.engine.core;
import me.you.thinking_fast.engine.game_engine;
import me.you.thinking_fast.engine.level_entity;
import me.you.thinking_fast.engine.my_app;
import me.you.thinking_fast.engine.riddle;

public class transtion_activity extends AppCompatActivity {
    public static Activity handler;

    private startCanvas startC;
    private ValueAnimator anim;
    private level_entity sel_lvl_obj;
    private trans_loading_ridd_view loading_ridd_view;

    public int CurrProgress_pers = 0;
    private TextView pls_msg;
    private engineLoad load_engine;
    private boolean is_all_lvl_complete = false;
    private LinearLayout btns_comp;
    private AdView mAdView;
    private ImageView Cat_loading;
    private boolean motion_peruod_end= false;
    private boolean loading_end = false;
    private boolean Stop_loading = false;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        set_locale("ar");
        handler = this;
        setContentView(R.layout.transtion_act);
        my_app.game_eng.play_whoo();


        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        Intent intent = getIntent();


        pls_msg =(TextView)findViewById(R.id.pls_msg);
        btns_comp =  (LinearLayout)findViewById(R.id.btns_comp);
        pls_msg.setTypeface(my_app.mainFont);
        loading_ridd_view =(trans_loading_ridd_view)findViewById(R.id.activity_trans);
        Cat_loading =(ImageView)findViewById(R.id.cat_loading);


        //define tread to download riddle
         load_engine = new engineLoad();



        String game_state = (String)intent.getExtras().get("game_state");
        int sel_lvl = (int)intent.getExtras().get("LVL_NUM");

        if(!game_state.equals("OFFLINE")) {
            my_app.game_state = game_state;
            my_app.sel_lvl = sel_lvl;
        }


        Log.i("game_state",game_state);
            if(game_state.equals("NEXT_LVL")){
              //  is_all_lvl_complete = my_app.mdb.is_all_lvl_complete_man();


                    loading_ridd_view.set_progress_persent(50);


                    sel_lvl_obj = my_app.mdb.get_levels(my_app.mdb.get_current_levels());
                    my_app.sel_lvl = sel_lvl_obj.getNum();

                }
            else if(game_state.equals("REPLAY_LVL")){
                sel_lvl_obj = my_app.mdb.get_levels(sel_lvl);

            }else if(game_state.equals("SELECT_LEVEL")){
                sel_lvl_obj = my_app.mdb.get_levels(sel_lvl);

            }else if(game_state.equals("OFFLINE")){

              //  trans_msg.setText("تحميل المرحلة " + getString(getResources().getIdentifier("_"+ lvl.getNum(), "string", getPackageName())));
              //  lvl = my_app.mdb.get_levels(sel_lvl);

            }
        my_app.game_eng.set_curtent_level(sel_lvl_obj);





        if(game_state.equals("OFFLINE")) {
            loading_ridd_view.set_color(Color.BLACK, getColor(R.color.green_1),Color.BLUE);

            pls_msg.setTextColor(Color.MAGENTA);


            my_app.game_eng.set_curtent_level(new level_entity(-1,  Color.rgb(47, 90, 177)
                    ,Color.rgb(242, 242, 242) ,Color.rgb(204, 57, 73),15,2,2,0,0,3000));

            my_app.game_eng.load_riddles();

            Intent intent1 = new Intent(getApplicationContext(), game.class);
            startActivity(intent1);
            my_app.current_activity = core.GAME_ACT;

            //   overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);


        }else if(is_all_lvl_complete) {
            loading_ridd_view.set_color(Color.GREEN, getColor(R.color.blow_light2),Color.BLUE);

            pls_msg.setTextColor(Color.WHITE);
        }
        else {
            loading_ridd_view.set_color(sel_lvl_obj.getColor1(), sel_lvl_obj.getColor2(), sel_lvl_obj.getTextColor());
            ColorFilter emphasize = new LightingColorFilter(sel_lvl_obj.getColor1(),sel_lvl_obj.getColor1() );

            Cat_loading.setColorFilter(emphasize);
            pls_msg.setTextColor(sel_lvl_obj.getTextColor());

            final Handler h = new Handler();
            h.postDelayed(new Runnable()
            {
                private long time = 0;

                @Override
                public void run()
                {
                    load_engine.start();
                }
            }, 500);


        }

        my_app.game_eng.set_onLoaded_ridd(new game_engine.on_ridds_loaded() {



            @Override
            public void load_images() {
                boolean noimage = true;
                CurrProgress_pers = 40;
                loading_ridd_view.set_progress_persent(CurrProgress_pers);



                List<riddle> riddlist = my_app.game_eng.get_riddle_list();
                float imgsRidCount = my_app.game_eng.current_level.get_imgsRidCount();
                Log.i("Size"," "+riddlist.size());

                float addProgress_size =  CurrProgress_pers/imgsRidCount ;

                Log.i("ressss",addProgress_size + "__" + imgsRidCount);
                for(int i=0;i < riddlist.size();i++) {
                    if (riddlist.get(i).getType() ==3 || riddlist.get(i).getType() ==4 ){
                        noimage = false;
                        String url = my_app.server_url + "imgs/" + riddlist.get(i).getImg();
                        new loading_image().execute(url, riddlist.get(i).getImg(), "" +addProgress_size);

                    }

                    if(noimage ){
                        loading_ridd_view.set_progress_persent(0);

                    }
                }
            }

            @Override
            public void conn_error(int state) {

                if (!Stop_loading)
                {       Intent intent = new Intent(getApplicationContext(), conn_error_act.class);
                startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                }
            }
        });



        loading_ridd_view.setProgressEndListener(new trans_loading_ridd_view.OnProgressEndListener() {
            @Override
            public void onProgressEndListener() {
                loading_end = true;
                Cat_loading.setImageDrawable(getResources().getDrawable(R.drawable.empty));
                       pls_msg.setText(" ");
                start_game();
            }
        });



        final Handler h = new Handler();
        h.postDelayed(new Runnable()
        {
            private long time = 0;

            @Override
            public void run()
            {
             motion_peruod_end = true;
                start_game();
            }
        }, 5000);

    }

    private void start_game() {
        if( motion_peruod_end && loading_end && !Stop_loading ){
            motion_peruod_end = false;
            loading_end = false;
            my_app.mdb.save_offline(my_app.game_eng.get_riddle_list());
            Log.i("activity", "start game activity ..");
            Intent intent = new Intent(getApplicationContext(), game.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    }


       @Override
    public void onBackPressed() {
       super.onBackPressed();
           Stop_loading =true;
   }





    public  void set_locale(String lang){
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getApplicationContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Intent intent = new Intent(getApplicationContext(), start.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }

    class engineLoad extends Thread{

        @Override
        public void run(){
            //   Looper.prepare();
            synchronized(this) {
            //   game_eng = new game_engine(0,core.GAMEMODE_PLAY);
                my_app.game_eng.set_curtent_level(sel_lvl_obj);
                // my_app.game_eng.load_riddles();
                    my_app.game_eng.download_riddles(getApplicationContext());
                //  eng_loaded = true;
                //  Looper.loop();
                notify();
            }
        }
    }



    class loading_image extends AsyncTask<String,Boolean,String> {

        String url = "";
        Bitmap bm = null;
        float add_prog_val = 0;
        public loading_image() {
        }

        @Override
        protected void onPreExecute() {
            //     if( !isConnectingToInternet()) {
            //        stoped = true ;
            //        Log.e("NetWork","no enter net connection ..");
            //     }

        }

        @Override
        protected String doInBackground(String... url) {

            add_prog_val = Float.parseFloat(url[2]);
      if(my_app.mdb.is_not_downloaded(url[1])) {
                publishProgress(true);

                getBitmapFromURL(url[0],url[1]);
                publishProgress(false);
            }
            return "Done";
        }


        @Override
        protected void onProgressUpdate(Boolean... res) {
            if(res[0]){
                Log.i("Loading","Loading image");
            }else
                Log.i("Loading","Loading finshed");
        }


        @Override
        protected void onPostExecute(String res) {
        //    img_view.setImageBitmap(bm);
            if(res.equals("Done")) {
                Log.i("Done", res + add_prog_val);
                CurrProgress_pers -= add_prog_val;
                loading_ridd_view.set_progress_persent(CurrProgress_pers);

                super.onPostExecute(res);
                cancel(false);
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            Log.i("Loading","Cancel");

        }
    }

    // convert from bitmap to byte array
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    public boolean getBitmapFromURL(String src,String fname ) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();

            //byte[] image = new byte[input.available()];
            //input.read(image);


                my_app.mdb.add_image(fname,IOUtils.toByteArray(input));
           // input.close();
            return true;
            //Bitmap myBitmap = BitmapFactory.decodeStream(input);
           // return myBitmap;

        } catch (Exception e) {
            // TODO: handle exceptionfloat
            e.printStackTrace();
            return false;
        }
    }

}
