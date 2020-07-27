package me.you.thinking_fast;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import me.you.thinking_fast.engine.my_app;

public class about extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        setContentView(R.layout.activity_about);
        my_app.game_eng.play_whoo();



        Button FB_link = (Button)findViewById(R.id.FB_link);
        Button SE_link = (Button)findViewById(R.id.SE_link);


        FB_link.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub

                startActivityForResult(getOpenFacebookIntent(getApplicationContext()),1);
                overridePendingTransition(android.R.anim.slide_in_left,R.anim.scale_out);


            }
        });

        SE_link.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub

                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"yyk_2000@hotmail.com"});
                i.putExtra(Intent.EXTRA_SUBJECT,getResources().getString( R.string.app_name));
                i.putExtra(Intent.EXTRA_TEXT   , "#");
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                    overridePendingTransition(android.R.anim.slide_in_left,R.anim.scale_out);

                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(about.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
                overridePendingTransition(android.R.anim.slide_in_left,R.anim.scale_out);


            }
        });
    }



    public  Intent getOpenFacebookIntent(Context context) {

        try {
            String uri = "fb://profile/100001640134405";
            context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
            return new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/younes.khafaja"));

        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);


    }
}
