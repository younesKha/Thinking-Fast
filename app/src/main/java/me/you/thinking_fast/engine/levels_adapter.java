package me.you.thinking_fast.engine;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.you.thinking_fast.R;
import me.you.thinking_fast.custom.circuler_rate_view;
import me.you.thinking_fast.custom.ticket_shap;
import me.you.thinking_fast.game;

/**+
 */

public class levels_adapter extends BaseAdapter {

    private final int hard_lvl;
    private final LayoutInflater mInflater;

    List<level_entity> level_list= new ArrayList<level_entity>();
    Context context;
    private Paint WinPaint;
    private ListView lv;

    public levels_adapter(Context context,ListView lv){
        this.context = context;
        this.lv = lv;
        hard_lvl = my_app.get_game_sett_hardlevel();

        level_list = my_app.mdb.get_levels_List(hard_lvl);



        WinPaint =new Paint(Paint.ANTI_ALIAS_FLAG);
        mInflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }



    public void show_item(int i){

            level_list.get(i).show_options = true;

       // notifyDataSetChanged();

    }



    public void hide_item(int i){


        level_list.get(i).show_options = false;

        // notifyDataSetChanged();

    }

public void hide_all_action_par(){

    for(int i=0;i<level_list.size();i++){
        level_list.get(i).show_options = false;
    }
//   notifyDataSetChanged();

}
    @Override
    public int getCount() {
        return level_list.size();
    }

    @Override
    public Object getItem(int i) {
        return level_list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return level_list.get(i).getNum();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {


        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.levels_tickets, null);
            holder = new ViewHolder();
            holder.bak_liner_level = (LinearLayout)convertView.findViewById(R.id.bak_liner_level);
            holder.lvl_action  = (LinearLayout)convertView.findViewById(R.id.lvl_action);
            holder.ctitle = (TextView)convertView.findViewById(R.id.currnt_title);
            holder.lvl_num_numirc = (TextView)convertView.findViewById(R.id.lvl_num_numirc);
            holder.lvl_img = (ImageView)convertView.findViewById(R.id.lvl_img);
            holder.lvl_btn = (Button)convertView.findViewById(R.id.lvl_replay_8);
            holder.lvl_pers_circl = (circuler_rate_view)convertView.findViewById(R.id.lvl_pers);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }



        holder.ctitle.setTypeface(my_app.mainFont);

        holder.lvl_num_numirc.setTypeface(my_app.mainFont);
        holder.lvl_btn.setTypeface(my_app.mainFont);

        if  (level_list.get(i).show_options)
            holder.lvl_action.setVisibility(View.VISIBLE);
        else
            holder.lvl_action.setVisibility(View.GONE);



        GradientDrawable gd = new GradientDrawable();

        // Set the color array to draw gradient
        gd.setColors(new int[]{
                level_list.get(i).getColor1(),
                level_list.get(i).getColor2(),

        });

        // Set the GradientDrawable gradient type linear gradient
        gd.setGradientType(GradientDrawable.LINEAR_GRADIENT);

        // Set GradientDrawable shape is a rectangle
        gd.setShape(GradientDrawable.RECTANGLE);
        gd.setCornerRadius(28);
        gd.setOrientation(GradientDrawable.Orientation.RIGHT_LEFT);
        // Set 3 pixels width solid blue color border
      //  gd.setStroke(3, Color.BLUE);

        // Set GradientDrawable width and in pixels
        gd.setSize(450, 150); // Width 450 pixels and height 150 pixels

        // Set GradientDrawable as ImageView source image






        float xxx_pers;
        if(hard_lvl == 1)
         xxx_pers = level_list.get(i).getMan_pers();
        else
            xxx_pers = level_list.get(i).getChild_pers();



        if(xxx_pers == -1)
        {
            holder.ctitle.setVisibility(View.VISIBLE);
            holder.lvl_num_numirc.setVisibility(View.GONE);
            holder.ctitle.setTextColor(level_list.get(i).getTextColor());
            holder.ctitle.setText( "الجولة " +
                    context.getString(context.getResources().getIdentifier("_"+ level_list.get(i).getNum(), "string", context.getPackageName())));

             //   ColorFilter emphasize = new LightingColorFilter(level_list.get(i).getTextColor() , level_list.get(i).getTextColor() );
            //    WinPaint.setColorFilter(emphasize);
          //   lvl_img.setLayerPaint(WinPaint);
            holder.lvl_img.setColorFilter(level_list.get(i).getTextColor(), PorterDuff.Mode.MULTIPLY);
            ((ticket_shap)holder.bak_liner_level).setcolor(level_list.get(i).getColor2());


            holder.lvl_pers_circl.setVisibility(View.GONE);
            holder.lvl_btn.setVisibility(View.VISIBLE);
        //    lvl_btn.setTextSize(my_app.game_width/80);
            holder.lvl_btn.setText(" ابــدأ ");
            holder.lvl_img.setImageDrawable(context.getDrawable(R.drawable.play));

            ((ticket_shap)holder.bak_liner_level).setcolor(level_list.get(i).getColor2());
        }
        else if(xxx_pers == 0 ){//not complished


           // lvl_pers_circl.setVisibility(View.INVISIBLE);
            holder.lvl_pers_circl.setVisibility(View.VISIBLE);
            holder.lvl_pers_circl.set_rate(-1);
            holder.lvl_pers_circl.setCenterCircleColor( Color.rgb(127, 179, 235));
            holder.lvl_pers_circl.setTextColor( Color.rgb(127, 179, 235));
            holder.lvl_btn.setVisibility(View.INVISIBLE);
            holder.lvl_img.setImageDrawable(null);

            gd.setColors(new int[]{
                    Color.rgb(58, 120, 189),
                    Color.rgb(127, 179, 235),

            });

        }else{ // achived

            holder.ctitle.setVisibility(View.GONE);
            holder.lvl_num_numirc.setVisibility(View.VISIBLE);
            holder.lvl_num_numirc.setText(level_list.get(i).getNum() +"");
            holder.lvl_pers_circl.setCenterCircleColor(level_list.get(i).getColor2());
            holder.lvl_pers_circl.setTextColor(level_list.get(i).getTextColor());
            holder.lvl_pers_circl.set_rate(xxx_pers);
            holder.lvl_btn.setText("إعادة الجولة");
        //    lvl_btn.setTextSize(my_app.game_width/80);


            holder.lvl_pers_circl.setVisibility(View.VISIBLE);
            holder.lvl_btn.setVisibility(View.VISIBLE);


          //     ColorFilter emphasize = new LightingColorFilter(level_list.get(i).getColor1() , level_list.get(i).getColor1() );
         //    WinPaint.setColorFilter(emphasize);
             //lvl_img.setLayerPaint(WinPaint);
            holder.lvl_img.setColorFilter(level_list.get(i).getColor2(), PorterDuff.Mode.MULTIPLY);
            ((ticket_shap)holder.bak_liner_level).setcolor(level_list.get(i).getColor2());

            holder.lvl_img.setImageDrawable(context.getResources().getDrawable(R.drawable.win_shadow));


        }
        holder.bak_liner_level.setBackground(gd);


        holder.lvl_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                _onStartPlayClicked.onStartPlayClickedListener(level_list.get(i).getNum());
        }
        });

        convertView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.pup_up_in));

        return convertView;

    }

    public void set_onStartPlayClicked(onStartPlayClicked _onStartPlayClicked) {
        this._onStartPlayClicked = _onStartPlayClicked;
    }

    onStartPlayClicked _onStartPlayClicked;
    public interface onStartPlayClicked {
        void onStartPlayClickedListener(int num);
    }


    static class ViewHolder {
        LinearLayout bak_liner_level;
        LinearLayout lvl_action ;
        TextView  ctitle ;
        TextView  lvl_num_numirc;
        ImageView lvl_img;
        Button lvl_btn ;
        circuler_rate_view lvl_pers_circl ;

    }


}
