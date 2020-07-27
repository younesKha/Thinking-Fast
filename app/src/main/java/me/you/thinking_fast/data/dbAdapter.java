package me.you.thinking_fast.data;

/**
 * Created by younes_hp on 6/3/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import me.you.thinking_fast.engine.level_entity;
import me.you.thinking_fast.engine.my_app;
import me.you.thinking_fast.engine.player;
import me.you.thinking_fast.engine.riddle;

import static android.content.Context.MODE_PRIVATE;
import static me.you.thinking_fast.engine.core.ANSWER_1;
import static me.you.thinking_fast.engine.core.ANSWER_2;
import static me.you.thinking_fast.engine.core.RTYPE_IMAGE;
import static me.you.thinking_fast.engine.core.RTYPE_SHAP;
import static me.you.thinking_fast.engine.core.RTYPE_TEXT;
import static me.you.thinking_fast.engine.core.RTYPE_TEXTANDIMAGE;
import static me.you.thinking_fast.engine.core.SHAP_CIRCLE;
import static me.you.thinking_fast.engine.core.SHAP_RECT;
import static me.you.thinking_fast.engine.core.SHAP_TRINGLE;

public class dbAdapter {
    public static final int DB_RES_DONE = 1;
    public static final int DB_RES_ERROR = 0;
    public static final int DB_RES_DATACONNECTED = 2;

    private static final int DATABASE_VERSION = 99;
    private static final String DATABASE_NAME = "TFDatabase.db";
    // TABLES
    private static SQLiteDatabase db;
    private final Context context;
    private myDbHelper dbHelper;

    public dbAdapter(Context _context) {
        context = _context;
        dbHelper = new myDbHelper(context, DATABASE_NAME, null,DATABASE_VERSION);
    }

    public dbAdapter open() throws SQLException {
        db = dbHelper.getWritableDatabase();
        Log.i("DBLoc", db.getPath());

        return this;
    }

    public void close() {
        db.close();
    }

    public int level_done(int lvl_num,float pers,int hard_lvl ){
try {
        Log.i("level_done",lvl_num + "-"+ pers + "-" + hard_lvl);

    if (hard_lvl == 1) {
        db.execSQL("update levels set man_pers=" + pers + " where num=" + lvl_num);
        db.execSQL("update levels set man_pers= -1 where num=" + (lvl_num +1) + " and man_pers =0");

    }else
    {
        db.execSQL("update levels set child_pers=" + pers +  " where num=" + lvl_num);
        db.execSQL("update levels set child_pers= -1 where num=" + (lvl_num +1)+ " and child_pers =0");
    }

    // update total pers
    if (hard_lvl == 1) {
        Cursor res = db.rawQuery("select sum(man_pers)/count(*) from levels where man_pers > 1 " , null);
        res.moveToFirst();

        update_player_man( res.getFloat(0),lvl_num);


    }else
    {
        Cursor res = db.rawQuery("select sum(child_pers)/count(*) from levels where child_pers > 1 " , null);
        res.moveToFirst();

        update_player_child( res.getFloat(0),lvl_num);
    }


	} catch (SQLException e) {
    Log.e("level_done",e.getMessage());
        return  0;
    }
        return 1;
    }


    public level_entity get_levels(int num){

        Cursor res =  db.rawQuery( "select num,color1,color2,textcolor,drop_count,need_points_count ,extra_fail_points,man_pers,child_pers,time_ms" +

                " from levels where num = " + num, null );
        res.moveToFirst();
        while (!res.isAfterLast()) {
            return new level_entity(res.getInt(0),res.getInt(1),res.getInt(2),res.getInt(3),res.getInt(5),res.getInt(6),res.getInt(4),res.getFloat(7),res.getFloat(8),res.getInt(9));

        }
        return null;
    }

    public float get_level_pers(int num){
        Cursor res ;
        int hl =  my_app.get_game_sett_hardlevel();
        if (hl ==1 )
        res =  db.rawQuery( "select man_pers from levels where num = " + num, null );
        else
        res =  db.rawQuery( "select child_pers from levels where num = " + num, null );

        res.moveToFirst();
        return res.getFloat(0);


    }

    public int get_current_levels(){

       int hl =  my_app.get_game_sett_hardlevel();
        Cursor res ;
        if (hl == 1) {
             res = db.rawQuery("select num from levels where man_pers = -1", null);
        }
       else
        {
                 res =  db.rawQuery( "select num from levels where child_pers = -1" , null );
            }

        res.moveToFirst();
        while (!res.isAfterLast()) {
            return res.getInt(0);
        }
        return -1;
    }


    public player get_player( ){
        player p = null;
        Cursor res =  db.rawQuery( "select name,player_id,child,man,child_curr_lvl,man_curr_lvl from player " , null );
        res.moveToFirst();


            Log.e("update_player_man ","ENTER");
            p = new player(res.getString(0),res.getLong(1),res.getFloat(2),res.getFloat(3),res.getInt(4),res.getInt(5));




        return p;

    }


    public void update_player_man(float pers,int lvl){
        try {
            db.execSQL("update player set " +
                    "man=" +pers +
                    ",man_curr_lvl=" + lvl) ;

        } catch (SQLException e) {
            Log.e("update_player_man ",e.getMessage());

        }
    }


    public void update_player_child(float pers,int lvl){
        try {
            db.execSQL("update player set " +
                    "child=" +pers +
                    ",child_curr_lvl=" + lvl) ;

        } catch (SQLException e) {
            Log.e("update_player_child",e.getMessage());

        }
    }

    public ArrayList<level_entity> get_levels_List(int howMuchHard){
        ArrayList<level_entity>LL =new ArrayList<level_entity >();
        Cursor res;
        if(howMuchHard == 1) {
             res = db.rawQuery("select num,color1,color2,textcolor,drop_count,need_points_count,extra_fail_points,man_pers,child_pers,time_ms" +
                    " from levels where man_pers != 0 order by num desc", null);
        }else
        {
             res = db.rawQuery("select num,color1,color2,textcolor,drop_count,need_points_count,extra_fail_points,man_pers,child_pers,time_ms" +
                    " from levels where child_pers != 0 order by num desc", null);

        }
        res.moveToFirst();
        while (!res.isAfterLast()) { 
            LL.add(new level_entity(res.getInt(0),res.getInt(1),res.getInt(2),res.getInt(3),res.getInt(5),res.getInt(6),res.getInt(4),res.getFloat(7),res.getFloat(8),res.getInt(9)));
            res.moveToNext();
        }
        return LL;
    }


    public ArrayList<riddle> get_riddle_List(int ridd_cont){
        ArrayList<riddle>LL =new ArrayList<riddle >();


        Random rand = new Random();


        Cursor res =  db.rawQuery("select id,ridd_type,answer1,answer2,img,ridd_text,shap_type,color,time,yes_no_qus ,is_man_lvl,is_child_lvl " +
                " from riddles order BY RANDOM() LIMIT " + ridd_cont , null );
        res.moveToFirst();
        while (!res.isAfterLast()) {



            LL.add(new riddle(res.getInt(0),res.getInt(1),res.getString(2),res.getString(3),res.getString(4),res.getString(5),Byte.parseByte(String.valueOf(res.getInt(6))),res.getInt(7),res.getInt(8),(res.getInt(9)==1)?true:false ,res.getInt(10),res.getInt(11)));
            res.moveToNext();
        }
        return LL;
    }

    public int add_image(String fname,byte[] file){

        try {

            ContentValues rid = new ContentValues();
            rid.put("fname", fname);
            rid.put("file",   file);
            db.insert("images", null, rid);
            Log.i("Save", fname + " " + file);
            return  1;
        }catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        }
    }



    public Bitmap get_image(String Image){
        byte[] img = null;
        try {
            Cursor res =  db.rawQuery( "select file from images where fname = '" + Image+ "'", null );

            Log.i("Count:", ""+ res.getCount());

            if(res.moveToNext()) {
                Log.i("Blob:", ""+ res.getBlob(0));

                img = res.getBlob(0);
            }else
                Log.i("img.length:", "no rows");

            Log.i("img.length:", ""+ img.length);

            Log.i("Filename:", ""+ Image);
            Bitmap myBitmap = BitmapFactory.decodeByteArray(img,0,img.length);
            return  myBitmap;

        } catch(Exception e) {
            Log.i("get_image","imageName"+ Image +"||sqlmsg:" + e.getMessage());
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
    return null;

    }

    public boolean is_not_downloaded(String img) {
            Cursor res = db.rawQuery("select * from images where fname = '" + img + "'", null);

        //    Log.i("Count:", "" + res.getCount());

            if (res.getCount()==1) return false;
            else return true;
    }

    public boolean is_all_lvl_complete() {
        Cursor res = null;
        if(my_app.get_game_sett_hardlevel() ==1)
            res = db.rawQuery("select * from levels where man_pers = 0", null);
        else
         res = db.rawQuery("select * from levels where child_pers = 0", null);

        //    Log.i("Count:", "" + res.getCount());
        if (res.getCount()>=1) return false;
        else return true;
    }

    public int get_ridd_count() {

        Cursor res = db.rawQuery("select count(*) from riddles ", null);

        res.moveToFirst();

         return res.getInt(0);
    }
    public void save_offline(List<riddle> riddle_list) {


        for(int i=0;i<riddle_list.size();i++){

            ContentValues rid = new ContentValues();
                try {
                    rid.put("id", riddle_list.get(i).getId());
                    rid.put("ridd_type", riddle_list.get(i).getType());
                    rid.put("answer1", riddle_list.get(i).getAnswer1());
                    rid.put("answer2", riddle_list.get(i).getAnswer2());
                    rid.put("img", riddle_list.get(i).getImg());
                    rid.put("ridd_text", riddle_list.get(i).getRiddleText());
                    rid.put("shap_type", riddle_list.get(i).getShap());
                    rid.put("color", riddle_list.get(i).getColor());
                    rid.put("time", riddle_list.get(i).getReq_time());
                    rid.put("is_man_lvl", riddle_list.get(i).getIs_man_lvl());
                    rid.put("is_child_lvl", riddle_list.get(i).getIs_child_lvl());
                    rid.put("answer_times", riddle_list.get(i).getReq_time());
                    rid.put("yes_no_qus", (riddle_list.get(i).is_yes_no_qus()==true)?(1):(0));
                    db.insert("riddles", null, rid);
                    Log.i("SQLException","Done .. " +riddle_list.get(i).getId());

                }catch (Exception e){
                    Log.i("SQLException",e.getMessage());
                }

        }



    //    current_level.riddles_list.add(new riddle(r.getInt("id"),r.getInt("ridd_type"),ans1,ans2,rightAnsw,r.getString("img"),r.getString("ridd_text"),(byte)r.getInt("shap_type"),hex2Rgb(r.getString("color")),r.getInt("time"),(r.getInt("yes_no_ridd") == 1)?(true):(false)));


    }

    //=============================Helper======================
    private static class myDbHelper extends SQLiteOpenHelper {


        private static final String CREATE_PLAYER_TABLE = "create table player "
                + "(player_id integer , "
                + "name  string ,"
                + "child float ,"
                + "man float,"
                + "child_curr_lvl  integer ,"
                + "man_curr_lvl  integer );";

        private static final String CREATE_LEVELS_TABLE = "create table levels "
                + "(num integer primary key, "
                + "color1  integer ,"
                + "color2  integer ,"
                + "textcolor  integer ,"

                + "drop_count  integer ,"
                + "need_points_count  integer ,"
                + "extra_fail_points  integer ,"
                + "time_ms  integer  ,"
                + "child_pers  float  ,"
                + "man_pers  float );";

        private static final String CREATE_RIDDLE_TABLE = "create table riddles"
             +  "(id integer primary key, "
                + "ridd_type  integer  ,"
                + "answer1  text,"
                + "answer2  text,"
                + "img  text,"
                + "ridd_text  text,"
                + "shap_type  integer,"
                + "color  integer,"
                + "time  integer,"
                + "is_man_lvl integer,"
                + "is_child_lvl integer,"
                + "answer_times integer,"
                + "yes_no_qus  integer);";


        private static final String CREATE_IMAGE_TABLE = "create table images"
                +  "(fname primary key, "
                + "file  blob);";
        private final Context context;


        public myDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
            this.context =context ;
        }





        private void load_riddle(SQLiteDatabase _db) {

///////////////   riddles    ///////////////////////////////////////////////////////////////////////////////



            //public static final byte RTYPE_TEXT = 1;
            //public static final byte RTYPE_SHAP = 2;
            //public static final byte RTYPE_IMAGE = 3;
            //public static final byte RTYPE_TEXTANDIMAGE = 4;
//--------------------------------------------------------------------------------
            //public static final byte SHAP_NONE  = 0;
            //public static final byte SHAP_CIRCLE  = 1;
            //public static final byte SHAP_TRINGLE  = 2;
            //public static final byte SHAP_RECT  = 3;
//--------------------------------------------------------------------------------


            ContentValues rid = new ContentValues();
/*
            rid.put("id", 1);
            rid.put("ridd_type",   RTYPE_TEXTANDIMAGE);
            rid.put("answer1",  "قرد");
            rid.put("answer2", "دائرة");
            rid.put("right_answer", ANSWER_1);
            rid.put("img", "a4");
            rid.put("ridd_text", "ما هذا الشيء");
            rid.put("shap_type",0 );
            rid.put("color", 0);
            rid.put("time", 2000);
            rid.put("level_num", 1);
            rid.put("hard_level", 1);
            rid.put("answer_times", 0);
            rid.put("is_answered_right", 0);
            _db.insert("riddles", null, rid);

            rid.put("id", 3);
            rid.put("ridd_type",   RTYPE_TEXTANDIMAGE);
            rid.put("answer1",  "قرد");
            rid.put("answer2", "دائرة");
            rid.put("right_answer", ANSWER_1);
            rid.put("img", "a2");
            rid.put("ridd_text", "Is this Dog ?");
            rid.put("shap_type",0 );
            rid.put("color", 0);
            rid.put("time", 2000);
            rid.put("level_num", 1);
            rid.put("hard_level", 1);
            rid.put("answer_times", 0);
            rid.put("is_answered_right", 0);
            _db.insert("riddles", null, rid);

            rid.put("id", 3);
            rid.put("ridd_type",   RTYPE_SHAP);
            rid.put("answer1",  "مربع");
            rid.put("answer2", "دائرة");
            rid.put("right_answer", ANSWER_1);
            rid.put("img", "");
            rid.put("ridd_text", "");
            rid.put("shap_type",SHAP_RECT );
            rid.put("color", Color.rgb(102, 102, 255));
            rid.put("time", 2000);
            rid.put("level_num", 1);
            rid.put("hard_level", 1);
            rid.put("answer_times", 0);
            rid.put("is_answered_right", 0);
            _db.insert("riddles", null, rid);


            rid.put("id", 4);
            rid.put("ridd_type",   RTYPE_SHAP);
            rid.put("answer1",  "مربع");
            rid.put("answer2", "دائرة");
            rid.put("right_answer", ANSWER_2);
            rid.put("img", "");
            rid.put("ridd_text", "");
            rid.put("shap_type",SHAP_CIRCLE );
            rid.put("color", Color.rgb(102, 102, 255));
            rid.put("time", 2000);
            rid.put("level_num", 1);
            rid.put("hard_level", 1);
            rid.put("answer_times", 0);
            rid.put("is_answered_right", 0);
            _db.insert("riddles", null, rid);


            rid.put("id", 5);
            rid.put("ridd_type",   RTYPE_TEXT);
            rid.put("answer1",  "صحيح");
            rid.put("answer2", "خطــا");
            rid.put("right_answer", ANSWER_2);
            rid.put("img", "");
            rid.put("ridd_text", "34 < 6 + 34");
            rid.put("shap_type",0 );
            rid.put("color", Color.rgb(102, 102, 255));
            rid.put("time", 5000);
            rid.put("level_num", 1);
            rid.put("hard_level", 1);
            rid.put("answer_times", 0);
            rid.put("is_answered_right", 0);
            _db.insert("riddles", null, rid);

            rid.put("id", 6);
            rid.put("ridd_type", RTYPE_IMAGE);
            rid.put("answer1",  "مرأة");
            rid.put("answer2", "رجل");
            rid.put("right_answer", ANSWER_1);
            rid.put("img", "black_girl");
            rid.put("ridd_text", "");
            rid.put("shap_type",0 );
            rid.put("color",0);
            rid.put("time", 2000);
            rid.put("level_num", 1);
            rid.put("hard_level", 1);
            rid.put("answer_times", 0);
            rid.put("is_answered_right", 0);
            _db.insert("riddles", null, rid);
*/

        }


        // Called when no database exists in
        // disk and the helper class needs
        // to create a new one.
        @Override
        public void onCreate(SQLiteDatabase _db) {




            _db.execSQL(CREATE_LEVELS_TABLE);
            _db.execSQL(CREATE_RIDDLE_TABLE);
            _db.execSQL(CREATE_IMAGE_TABLE);
            try {
                _db.execSQL(CREATE_PLAYER_TABLE);
            }catch (Exception c){

            }



            ContentValues Plyer = new ContentValues();

            Random rand = new Random();

            int  n = rand.nextInt(5000000) + 1;
            Plyer.put("player_id", n);
            Plyer.put("name",  "player"+ n );
            Plyer.put("child",50);
            Plyer.put("man",50);

            Plyer.put("child_curr_lvl", 0);
            Plyer.put("man_curr_lvl", 0);

            _db.insert("player", null, Plyer);



            ContentValues lvl = new ContentValues();

            //=====================
            lvl.put("num",1);
            lvl.put("color1",    Color.rgb(154, 204, 205));
            lvl.put("color2",    Color.rgb(210, 222, 234));
            lvl.put("textcolor", Color.rgb(255, 51, 51));

            lvl.put("drop_count", 1);
            lvl.put("need_points_count", 5);
            lvl.put("extra_fail_points", 3);

            lvl.put("time_ms", 3000);
            lvl.put("child_pers", -1);
            lvl.put("man_pers", -1);

            _db.insert("levels", null, lvl);
            //======================
            lvl.put("num", 2);
            lvl.put("color1",   Color.rgb(54, 104, 154) );
            lvl.put("color2",    Color.rgb(120, 185, 252));
            lvl.put("textcolor", Color.rgb(0, 0, 0));

            lvl.put("drop_count",1);
            lvl.put("need_points_count", 6);
            lvl.put("extra_fail_points", 3);

            lvl.put("time_ms", 3000);
            lvl.put("child_pers", 0);
            lvl.put("man_pers", 0);


            _db.insert("levels", null, lvl);


            //======================

            lvl.put("num",3);
            lvl.put("color1",    Color.rgb(10, 97, 127));
            lvl.put("color2",    Color.rgb(43, 171, 217));
            lvl.put("textcolor", Color.rgb(252, 217, 134));

            lvl.put("drop_count", 2);
            lvl.put("need_points_count", 7);
            lvl.put("extra_fail_points", 2);

            lvl.put("time_ms", 2500);
            lvl.put("child_pers", 0);
            lvl.put("man_pers", 0);


            _db.insert("levels", null, lvl);

            //=========6=============
            lvl.put("num",4);
            lvl.put("color1",    Color.rgb(20, 133, 153));
            lvl.put("color2",    Color.rgb(31, 177, 167));
            lvl.put("textcolor", Color.rgb(181, 255, 245));

            lvl.put("drop_count", 2);
            lvl.put("need_points_count", 10);
            lvl.put("extra_fail_points", 3);

            lvl.put("time_ms", 2000);
            lvl.put("child_pers", 0);
            lvl.put("man_pers", 0);

            _db.insert("levels", null, lvl);

            //======================
            lvl.put("num", 5);
            lvl.put("color1",   Color.rgb(255, 204, 0) );
            lvl.put("color2",    Color.rgb(255, 255, 153));
            lvl.put("textcolor", Color.rgb(102, 102, 255));

            lvl.put("drop_count", 1);
            lvl.put("need_points_count", 8);
            lvl.put("extra_fail_points", 2);
            lvl.put("time_ms",3000);
            lvl.put("child_pers", 0);
            lvl.put("man_pers", 0);


            _db.insert("levels", null, lvl);
            //======================
            lvl.put("num",6);
            lvl.put("color1",    Color.rgb(253, 192, 16));
            lvl.put("color2",    Color.rgb(229, 247, 60));
            lvl.put("textcolor", Color.rgb(255, 0, 20));
            lvl.put("drop_count", 2);
            lvl.put("need_points_count",10);
            lvl.put("extra_fail_points", 3);

            lvl.put("time_ms", 3000);
            lvl.put("child_pers", 0);
            lvl.put("man_pers", 0);
            _db.insert("levels", null, lvl);

            //=========8=============
            lvl.put("num",7);
            lvl.put("color1",    Color.rgb(195, 12, 98));
            lvl.put("color2",    Color.rgb(255, 111, 0));
            lvl.put("textcolor", Color.rgb(255, 254, 0));
            lvl.put("drop_count", 1);
            lvl.put("need_points_count",10);
            lvl.put("extra_fail_points", 2);

            lvl.put("time_ms", 2500);
            lvl.put("child_pers",0);
            lvl.put("man_pers", 0);

            _db.insert("levels", null, lvl);

            //=========9============
            lvl.put("num",8);
            lvl.put("color1",    Color.rgb(223, 0, 1));
            lvl.put("color2",    Color.rgb(255, 0, 97));
            lvl.put("textcolor", Color.rgb(255, 228, 140));
            lvl.put("drop_count", 1);
            lvl.put("need_points_count",12);
            lvl.put("extra_fail_points", 2);

            lvl.put("time_ms", 2000);
            lvl.put("child_pers", 0);
            lvl.put("man_pers", 0);

            _db.insert("levels", null, lvl);


            //=====================
            lvl.put("num",9);
            lvl.put("color1",    Color.rgb(124, 197, 0));
            lvl.put("color2",    Color.rgb(206, 255, 128));
            lvl.put("textcolor", Color.rgb(0, 83, 0));
            lvl.put("drop_count", 1);
            lvl.put("need_points_count", 10);
            lvl.put("extra_fail_points", 3);

            lvl.put("time_ms", 3000);
            lvl.put("child_pers", 0);
            lvl.put("man_pers", 0);

            _db.insert("levels", null, lvl);
            //=========11=============
            lvl.put("num",10);
            lvl.put("color1",    Color.rgb(123, 135, 0));
            lvl.put("color2",    Color.rgb(207, 214, 56));
            lvl.put("textcolor", Color.rgb(100, 112, 0));
            lvl.put("drop_count", 1);
            lvl.put("need_points_count", 10);
            lvl.put("extra_fail_points", 3);

            lvl.put("time_ms", 3000);
            lvl.put("child_pers", 0);
            lvl.put("man_pers", 0);

            _db.insert("levels", null, lvl);
            //=====================
            lvl.put("num",11);
            lvl.put("color1",    Color.rgb(144, 61, 151));
            lvl.put("color2",    Color.rgb(219, 69, 251));
            lvl.put("textcolor", Color.rgb(12, 56, 111));
            lvl.put("drop_count", 1);
            lvl.put("need_points_count", 12);
            lvl.put("extra_fail_points", 2);

            lvl.put("time_ms", 2500);
            lvl.put("child_pers", 0);
            lvl.put("man_pers", 0);
            _db.insert("levels", null, lvl);
            //=====================
            lvl.put("num", 12);
            lvl.put("color1",  Color.rgb(0, 0, 51)  );
            lvl.put("color2",  Color.rgb(102, 0, 153)  );
            lvl.put("textcolor", Color.rgb(204, 153, 255));

            lvl.put("drop_count", 1);
            lvl.put("need_points_count", 14);
            lvl.put("extra_fail_points", 2);

            lvl.put("time_ms", 2000);
            lvl.put("child_pers", 0);
            lvl.put("man_pers", 0);


            _db.insert("levels", null, lvl);

            //=========13=============
            lvl.put("num",13);
            lvl.put("color1",    Color.rgb(217, 0, 209));
            lvl.put("color2",    Color.rgb(0, 99, 179));
            lvl.put("textcolor", Color.rgb(0, 255, 255));
            lvl.put("drop_count", 1);
            lvl.put("need_points_count", 14);
            lvl.put("extra_fail_points", 2);

            lvl.put("time_ms", 2000);
            lvl.put("child_pers", 0);
            lvl.put("man_pers", 0);
            _db.insert("levels", null, lvl);

            //=========14=============
            lvl.put("num",14);
            lvl.put("color1",    Color.rgb(0, 40, 100));
            lvl.put("color2",    Color.rgb(141, 51, 0));
            lvl.put("textcolor", Color.rgb(208, 190, 201));
            lvl.put("drop_count", 1);
            lvl.put("need_points_count", 14);
            lvl.put("extra_fail_points", 2);

            lvl.put("time_ms", 2000);
            lvl.put("child_pers", 0);
            lvl.put("man_pers", 0);
            _db.insert("levels", null, lvl);

            //=========15=============
            lvl.put("num",15);
            lvl.put("color1",    Color.rgb(0, 25, 54));
            lvl.put("color2",    Color.rgb(0, 40, 100));
            lvl.put("textcolor", Color.rgb(255, 219, 0));
            lvl.put("drop_count", 1);
            lvl.put("need_points_count", 14);
            lvl.put("extra_fail_points", 2);

            lvl.put("time_ms", 2000);
            lvl.put("child_pers", 0);
            lvl.put("man_pers", 0);
            _db.insert("levels", null, lvl);

            //=========16=============
            lvl.put("num",16);
            lvl.put("color1",    Color.rgb(0, 0, 0));
            lvl.put("color2",    Color.rgb(0, 25, 54));
            lvl.put("textcolor", Color.rgb(240, 255, 255));
            lvl.put("drop_count", 1);
            lvl.put("need_points_count", 14);
            lvl.put("extra_fail_points", 2);

            lvl.put("time_ms", 1500);
            lvl.put("child_pers", 0);
            lvl.put("man_pers", 0);

            _db.insert("levels", null, lvl);

            load_riddle(_db);

        }


        // Called when there is a database version mismatch meaning that
        // the version of the database on disk needs to be upgraded to
        // the current version.
        @Override
        public void onUpgrade(SQLiteDatabase _db, int _oldVersion,
                              int _newVersion) {



            SharedPreferences.Editor editor = context.getSharedPreferences(my_app.MY_SETTING_SAVED, MODE_PRIVATE).edit();
            editor.putInt("stopTime", 3);
            editor.commit();

            // Log the version upgrade.
            Log.w("TaskDBAdapter", "Upgrading from version " + _oldVersion+ " to " + _newVersion
                    + ", which will destroy all old data");
            // Upgrade the existing database to conform to the new version.
            // Multiple previous versions can be handled by comparing
            // _oldVersion and _newVersion values.
            // The simplest case is to drop the old table and create a
            // new one.

            _db.execSQL("DROP TABLE IF EXISTS levels" );
            _db.execSQL("DROP TABLE IF EXISTS riddles" );
            _db.execSQL("DROP TABLE IF EXISTS images" );
            _db.execSQL("DROP TABLE IF EXISTS player" );

            //  _db.execSQL("DROP TABLE IF EXISTS " + ACCOUNTS_TABLE);
          //  _db.execSQL("DROP TABLE IF EXISTS " + CURRENCY_TABLE);
          //  _db.execSQL("DROP TABLE IF EXISTS " + ENTRY_TABLE);
          //  _db.execSQL("DROP TABLE IF EXISTS " + CUSTOMERS_TABLE);



            // Create a new one.
            onCreate(_db);
        }
    }



}

