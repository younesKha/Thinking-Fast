package me.you.thinking_fast.engine;

/**
 * Created by younes_hp on 11/23/2017.
 */

public class player {

    private String name ;
    private  long player_id;
    private  float        child_pers;
    private  float man_pers;
    private  int child_curr_lvl;
    private  int  man_curr_lvl;




    public player(String name, long player_id, float child_pers, float man_pers, int child_curr_lvl, int man_curr_lvl) {
        this.name = name;
        this.player_id = player_id;
        this.child_pers = child_pers;
        this.man_pers = man_pers;
        this.child_curr_lvl = child_curr_lvl;
        this.man_curr_lvl = man_curr_lvl;
    }



    public String getName() {
        return name;
    }

    public long getPlayer_id() {
        return player_id;
    }

    public float getChild_pers() {
        return child_pers;
    }

    public float getMan_pers() {
        return man_pers;
    }

    public int getChild_curr_lvl() {
        return child_curr_lvl;
    }

    public int getMan_curr_lvl() {
        return man_curr_lvl;
    }


}
