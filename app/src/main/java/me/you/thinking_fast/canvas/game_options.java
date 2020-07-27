package me.you.thinking_fast.canvas;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.you.thinking_fast.R;

/**
 * Created by younes_hp on 3/31/2017.
 */

public class game_options extends DialogFragment {


    public static game_options newInstance(int num) {
        game_options f = new game_options();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("num", num);
        f.setArguments(args);



        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    //  style = R.style.AppTheme;
    setStyle(DialogFragment.STYLE_NO_TITLE, R.style.Alert_opt);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      //  return super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.fail_no_point_dial, container, false);
// Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.
        //     ActionBar actionBar = getActionBar();
        return v;
    }


}
