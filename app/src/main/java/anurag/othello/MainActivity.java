package anurag.othello;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG="mystring";
    LinearLayout myLayout;
    LinearLayout myLinearLayout[];
    int n;
    int pass;
    boolean passwin;
    MyButton[][] buttons;
    int checkplayerlastindex;
    int checkplayerlastindexofj;
    boolean checkvalid=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        passwin=false;
        myLayout = (LinearLayout) findViewById(R.id.myLayout);
        checkplayerlastindex=-1;
        checkplayerlastindexofj=-1;
        n=8;
        pass=0;
        setupBoard();

    }

    public void setupBoard() {
        buttons = new MyButton[n][n];
        myLinearLayout = new LinearLayout[n];
        for (int i = 0; i < buttons.length; i++) {
            myLinearLayout[i] = new LinearLayout(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 0, 1f);
            params.setMargins(5, 5, 5, 5);
            myLinearLayout[i].setOrientation(LinearLayout.HORIZONTAL);
            myLinearLayout[i].setLayoutParams(params);
            myLayout.addView(myLinearLayout[i]);
        }
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                buttons[i][j]=new MyButton(this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1f);
                params.setMargins(5, 5, 5, 5);
                buttons[i][j].setOnClickListener(this);
                buttons[i][j].p=i;
                buttons[i][j].q=j;
                buttons[i][j].setBackground(ContextCompat.getDrawable(this,R.drawable.rectangle));
                buttons[i][j].setLayoutParams(params);
                myLinearLayout[i].addView(buttons[i][j]);
            }
        }
        buttons[n/2][n/2].setBackground(ContextCompat.getDrawable(this,R.drawable.circle));
        buttons[n/2][n/2].player=1;
        buttons[n/2][n/2].turn=1;
        buttons[n/2][(n/2)-1].setBackground(ContextCompat.getDrawable(this,R.drawable.mycircle));
        buttons[n/2][(n/2)-1].player=2;
        buttons[n/2][(n/2)-1].turn=1;
        buttons[(n/2)-1][(n/2)-1].setBackground(ContextCompat.getDrawable(this,R.drawable.circle));
        buttons[(n/2)-1][(n/2)-1].player=1;
        buttons[(n/2)-1][(n/2)-1].turn=1;
        buttons[(n/2)-1][(n/2)].setBackground(ContextCompat.getDrawable(this,R.drawable.mycircle));
        buttons[(n/2)-1][(n/2)].player=2;
        buttons[(n/2)-1][(n/2)].turn=1;
    }

    @Override
    public void onClick(View view) {
        MyButton button = (MyButton) view;
        checkvalid=false;
        checkplayerlastindex=-1;
        if(button.turn==0) {
            if (pass == 0) {
                button.player = 1;
                checkmove(button);
                    if (checkvalid) {
                        button.turn = 1;
                        pass = 1;
                        checkwin();
                        passwin=false;
                        button.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.circle));
                    }
                    else {
                        boolean ans=checkpass(button);
                        if(ans)
                        {
                            if(passwin)
                            {
                                checkpassWin();
                            }
                            passwin=true;
                            button.player=0;
                            pass=1;
                            Toast.makeText(MainActivity.this, "PASS", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            button.player = 0;
                            passwin=false;
                            Toast.makeText(MainActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        }
                    }
            } else {
                button.player = 2;
                checkmove(button);
                    if (checkvalid) {
                        button.turn = 1;
                        pass = 0;
                        checkwin();
                        passwin=false;
                        button.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.mycircle));
                    }
                    else {
                        boolean ans=checkpass(button);
                        if(ans)
                        {
                            if(passwin)
                            {
                                checkpassWin();
                            }
                            passwin=true;
                           button.player=0;
                            pass=0;
                            Toast.makeText(MainActivity.this, "PASS", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            button.player = 0;
                            passwin=false;
                            Toast.makeText(MainActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        }
                    }
            }
            spread(button);
        }
        else
        {
            return;
        }

    }
    public void checkpassWin()
    {
        int countblack=0;
        int countred=0;
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<n;j++)
            {
                buttons[i][j].turn=1;
                if(buttons[i][j].player==1)
                {
                    countred++;
                }
                if(buttons[i][j].player==2)
                {
                    countblack++;
                }
            }
        }
        if(countblack>countred)
        {
            Toast.makeText(this,"BLACK WINS",Toast.LENGTH_SHORT).show();
        }
        if(countblack<countred)
        {
            Toast.makeText(this,"RED WINS",Toast.LENGTH_SHORT).show();
        }
        if(countblack==countred)
        {
            Toast.makeText(this,"DRAW",Toast.LENGTH_SHORT).show();
        }
    }
    public void checkwin()
    {
        int countblack=0;
        int countred=0;
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<n;j++)
            {
                if(buttons[i][j].player==0)
                {
                    return;
                }
                if(buttons[i][j].player==1)
                {
                    countred++;
                }
                if(buttons[i][j].player==2)
                {
                    countblack++;
                }
            }
        }
        if(countblack>countred)
        {
            Toast.makeText(this,"BLACK WINS",Toast.LENGTH_SHORT).show();
        }
        if(countblack<countred)
        {
            Toast.makeText(this,"RED WINS",Toast.LENGTH_SHORT).show();
        }
        if(countblack==countred)
        {
            Toast.makeText(this,"DRAW",Toast.LENGTH_SHORT).show();
        }

    }
    public boolean checkpass(MyButton button)
    {
        int a1[]={-1,-1,-1,0,1,1,1,0};
        int a2[]={-1,0,1,1,1,0,-1,-1};
        for(int x=0;x<n;x++) {
            for (int y = 0; y < n; y++) {
                if(buttons[x][y].player==button.player) {
                    Log.i(TAG,"called");
                    for (int l = 0; l < 8; l++) {
                        int i = buttons[x][y].p + a1[l];
                        int j = buttons[x][y].q + a2[l];
                        if ((i >= 0 && j >= 0) && (i < n && j < n)) {
                            if (buttons[i][j].player != button.player&&buttons[i][j].player!=0) {
                                Log.i(TAG,"called2");
                                i = i + a1[l];
                                j = j + a2[l];
                                while ((i >= 0 && j >= 0) && (i < n && j < n)) {
                                    if (buttons[i][j].player == 0) {
                                        Log.i(TAG,"called3");
                                        return false;
                                    }
                                    if(buttons[i][j].player==button.player)
                                    {
                                        Log.i(TAG,"called4");
                                        break;
                                    }
                                    i = i + a1[l];
                                    j = j + a2[l];
                                }
                            }
                        }
                    }
                }
            }
        }
        Log.i(TAG,"called5");
        return true;
    }
    public void checkmove(MyButton button)
    {
        int a1[]={-1,-1,-1,0,1,1,1,0};
        int a2[]={-1,0,1,1,1,0,-1,-1};
        for(int l=0;l<8;l++)
        {
            int i=button.p+a1[l];
            int j=button.q+a2[l];
            if((i>=0&&j>=0)&&(i<n&&j<n))
            {
                 if(buttons[i][j].player!=button.player&&buttons[i][j].player!=0)
                {
                    i=i+a1[l];
                    j=j+a2[l];
                   while((i>=0&&j>=0)&&(i<n&&j<n)) {
                           if (buttons[i][j].player == button.player) {
                               checkvalid= true;
                               break;
                           }
                           else if(buttons[i][j].player==0)
                           {
                               checkvalid=false;
                               break;
                           }
                       i=i+a1[l];
                       j=j+a2[l];
                   }
                }
                if(checkvalid==true)
                {
                    break;
                }
            }
        }
    }
    public void spread(MyButton button)
    {
        for(int i=button.q+1;i<n;i++)
        {
            if(button.player==buttons[button.p][i].player)
            {
                checkplayerlastindex=i;
                break;
            }
            else if(buttons[button.p][i].player==0)
            {
                break;
            }
        }
        if(checkplayerlastindex>=0)
        {
            for(int i=button.q+1;i<checkplayerlastindex;i++)
            {
                if(button.player==1) {
                    buttons[button.p][i].player=1;
                    buttons[button.p][i].setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.circle));
                }
                else if(button.player==2)
                {
                    buttons[button.p][i].player=2;
                    buttons[button.p][i].setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.mycircle));
                }
            }
            checkplayerlastindex=-1;
        }
        for(int i=button.q-1;i>=0;i--)
        {
            if(button.player==buttons[button.p][i].player)
            {
                checkplayerlastindex=i;
                break;
            }
            else if(buttons[button.p][i].player==0)
            {
                break;
            }
        }
        if(checkplayerlastindex>=0)
        {
            for(int i=checkplayerlastindex+1;i<button.q;i++)
            {
                if(button.player==1) {
                    buttons[button.p][i].player=1;
                    buttons[button.p][i].setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.circle));
                }
                else if(button.player==2)
                {
                    buttons[button.p][i].player=2;
                    buttons[button.p][i].setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.mycircle));
                }
            }
            checkplayerlastindex=-1;
        }

        for(int j=button.p+1;j<n;j++)
        {
            if(button.player==buttons[j][button.q].player)
            {
                checkplayerlastindex=j;
                break;
            }
            else if(buttons[j][button.q].player==0)
            {
                break;
            }
        }
        if(checkplayerlastindex>=0)
        {
            for(int i=button.p+1;i<checkplayerlastindex;i++)
            {
                if(button.player==1) {
                    buttons[i][button.q].player=1;
                    buttons[i][button.q].setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.circle));
                }
                else if(button.player==2)
                {
                    buttons[i][button.q].player=2;
                    buttons[i][button.q].setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.mycircle));
                }
            }
            checkplayerlastindex=-1;
        }
        for(int j=button.p-1;j>=0;j--)
        {
            if(button.player==buttons[j][button.q].player)
            {
                checkplayerlastindex=j;
                break;
            }
            else if(buttons[j][button.q].player==0)
            {
                break;
            }
        }
        if(checkplayerlastindex>=0)
        {
            for(int i=button.p-1;i>checkplayerlastindex;i--)
            {
                if(button.player==1) {
                    buttons[i][button.q].player=1;
                    buttons[i][button.q].setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.circle));
                }
                else if(button.player==2)
                {
                    buttons[i][button.q].player=2;
                    buttons[i][button.q].setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.mycircle));
                }
            }
            checkplayerlastindex=-1;
        }
        for(int i=button.p+1,j=button.q+1;i<n;i++,j++)
        {
            if(j<n)
            {
                if(button.player==buttons[i][j].player)
                {
                    checkplayerlastindex=i;
                    checkplayerlastindexofj=j;
                    break;
                }
                if(buttons[i][j].player==0)
                {
                    break;
                }
            }
            else
            {
                break;
            }
        }
        if(checkplayerlastindex>=0) {
            for(int i=button.p+1,j=button.q+1;i<checkplayerlastindex;i++,j++) {
                if (j < checkplayerlastindexofj) {
                    if(button.player==1) {
                        buttons[i][j].player=1;
                        buttons[i][j].setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.circle));
                    }
                    else if(button.player==2)
                    {
                        buttons[i][j].player=2;
                        buttons[i][j].setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.mycircle));
                    }
                }
            }
            checkplayerlastindexofj=-1;
            checkplayerlastindex=-1;
        }
        for(int i=button.p+1,j=button.q-1;i<n;i++,j--)
        {
            if(j>=0)
            {
                if(button.player==buttons[i][j].player)
                {
                    checkplayerlastindex=i;
                    checkplayerlastindexofj=j;
                    break;
                }
                if(buttons[i][j].player==0)
                {
                    break;
                }
            }
            else
            {
                break;
            }
        }
        if(checkplayerlastindex>=0) {
            for(int i=button.p+1,j=button.q-1;i<checkplayerlastindex;i++,j--) {
                if (j > checkplayerlastindexofj) {
                    if(button.player==1) {
                        buttons[i][j].player=1;
                        buttons[i][j].setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.circle));
                    }
                    else if(button.player==2)
                    {
                        buttons[i][j].player=2;
                        buttons[i][j].setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.mycircle));
                    }
                }
            }
            checkplayerlastindexofj=-1;
            checkplayerlastindex=-1;
        }
        for(int i=button.p-1,j=button.q-1;i>=0;i--,j--)
        {
            if(j>=0)
            {
                if(button.player==buttons[i][j].player)
                {
                    checkplayerlastindex=i;
                    checkplayerlastindexofj=j;
                    break;
                }
                if(buttons[i][j].player==0)
                {
                    break;
                }
            }
            else
            {
                break;
            }
        }
        if(checkplayerlastindex>=0) {
            for(int i=button.p-1,j=button.q-1;i>checkplayerlastindex;i--,j--) {
                if (j > checkplayerlastindexofj) {
                    if(button.player==1) {
                        buttons[i][j].player=1;
                        buttons[i][j].setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.circle));
                    }
                    else if(button.player==2)
                    {
                        buttons[i][j].player=2;
                        buttons[i][j].setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.mycircle));
                    }
                }
            }
            checkplayerlastindexofj=-1;
            checkplayerlastindex=-1;
        }
        for(int i=button.p-1,j=button.q+1;i>=0;i--,j++)
        {
            if(j<n)
            {
                if(button.player==buttons[i][j].player)
                {
                    checkplayerlastindex=i;
                    checkplayerlastindexofj=j;
                    break;
                }
                if(buttons[i][j].player==0)
                {
                    break;
                }
            }
            else
            {
                break;
            }
        }
        if(checkplayerlastindex>=0) {
            for(int i=button.p-1,j=button.q+1;i>checkplayerlastindex;i--,j++) {
                if (j < checkplayerlastindexofj) {
                    if(button.player==1) {
                        buttons[i][j].player=1;
                        buttons[i][j].setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.circle));
                    }
                    else if(button.player==2)
                    {
                        buttons[i][j].player=2;
                        buttons[i][j].setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.mycircle));
                    }
                }
            }
            checkplayerlastindexofj=-1;
            checkplayerlastindex=-1;
        }

    }
}
