package anurag.othello;

import android.content.Context;
import android.widget.Button;

/**
 * Created by anurag on 19-06-2017.
 */
public class MyButton extends Button {
    int turn;
    int player;
    int p;
    int q;
    public MyButton(Context context) {
        super(context);
        turn=0;
        player=0;
        p=0;
        q=0;
    }
}
