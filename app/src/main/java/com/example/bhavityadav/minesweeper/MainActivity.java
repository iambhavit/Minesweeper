package com.example.bhavityadav.minesweeper;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    int countBomb;
    Handler handler;
    Timer timer;
    int no_flag;
    int count;
    boolean gameOver;
    int seconds,min,hour;
    int bomb;
    String s="Easy";
    int arrRow[]={-1,-1,-1,0,0,1,1,1};
    int arrCol[]={-1,0,1,-1,1,-1,0,1};
    public static final int DEFAULT = 0;
    public static final int Bomb = -10;
    int k;
    Timer t;
    TextView tv;
    int size=10;
    int num=size;
    MineButton btn[][];
    TextView tv2;
    LinearLayout rootLayout;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rootLayout = findViewById(R.id.root);
        //tv=findViewById(R.id.tv);

        tv2=findViewById(R.id.tv2);
        inIt();

    }

    private void inIt() {

        min=0;
        count=0;
        seconds=0;
        tv2.setText("00:00");
        if(handler!=null)
        handler.removeCallbacks(runnable);
        countBomb=0;
        if(s.equals("Easy"))
            num=size;
        if(s.equals("Medium"))
            num=size*2;
        if(s.equals("Hard"))
            num=size*2+size/2;
        tv=findViewById(R.id.tv1);
        rootLayout.removeAllViews();
        k=0;
        btn = new MineButton[size][size];
        no_flag=0;
        bomb=0;
        gameOver=false;
        setUpBoard();
    }

    Runnable runnable;
    private void setUpBoard() {
        for (int i = 0; i < size; i++) {
            LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1);
            LinearLayout layout = new LinearLayout(this);
            layout.setLayoutParams(parms);
            layout.setOrientation(LinearLayout.HORIZONTAL);
            for (int j = 0; j < size&&k<size*size;k++, j++) {
                MineButton button = new MineButton(this);
                LinearLayout.LayoutParams btnparms = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
                button.setLayoutParams(btnparms);
                button.setBackgroundResource(R.drawable.button);
                button.setOnClickListener(this);
                button.setOnLongClickListener(this);
                btn[i][j] = button;
                button.setId(k);
                layout.addView(button);
            }
            rootLayout.addView(layout);
        }
        int value;
        for (int k = 0; k < num; k++) {
            Random randomGen=new Random();
            int row=randomGen.nextInt(size);
            int col=randomGen.nextInt(size);
            if(btn[row][col].getValue()!=Bomb){
                countBomb++;
                btn[row][col].setValue(Bomb);
                    for (int i = 0, j = 0; i < arrRow.length && j < arrCol.length; i++, j++) {
                        if(row+arrRow[i]<size&&col+arrCol[j]<size&&row+arrRow[i]>-1&&col+arrCol[j]>-1) {

                            MineButton currentButton = btn[row + arrRow[i]][col + arrCol[j]];
                            if (currentButton.getValue() != Bomb) {
                                value = 0;
                                try {
                                    value = btn[row + arrRow[i]][col + arrCol[j]].getValue();
                                    value++;
                                    btn[row + arrRow[i]][col + arrCol[j]].setValue(value);
                                } catch (Exception e) {
                                    Toast.makeText(this, "eroor", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }

            }
        }
        if(countBomb<10)
            tv.setText("0"+String.valueOf(countBomb));
        else
            tv.setText(String.valueOf(countBomb));
    }
    @Override
    public void onClick(View view) {
        MineButton btn=(MineButton)view;
        if(count==0) {
            handler = new Handler();
            runnable = new Runnable() {
                @Override
                public void run() {
                    seconds++;
                    if(seconds==60){
                        seconds=0;
                        min++;
                    }
                    if(min<10&&seconds<10)
                        tv2.setText("0"+String.valueOf(min)+":"+"0"+String.valueOf(seconds));
                    if(min<10) {
                        if(seconds<10)
                            tv2.setText("0" +String.valueOf(min)+":"+"0"+String.valueOf(seconds));
                        else
                        tv2.setText("0" + String.valueOf(min) + ":" + String.valueOf(seconds));
                    }
                    if(min>9&&seconds>9)
                        tv2.setText(String.valueOf(min)+":"+String.valueOf(seconds));
                    handler.postDelayed(this, 1000);
                }
            };
            handler.post(runnable);
        }
        count++;
        if(gameOver)
            Toast.makeText(this,"Won",Toast.LENGTH_SHORT).show();
        else {
            if (btn.getValue() == -10) {
                btn.setInfo("B");
                btn.setEnabled(false);
                revealAll();
            } else {
                if (btn.getValue() == DEFAULT) {
                    reveal(btn);
                    btn.setEnabled(false);
                }
                btn.setInfo(String.valueOf(btn.getValue()));
                setBackground(btn);
                btn.setEnabled(false);
                if(allButtonRevealed()){
                    //t.cancel();
                    Toast.makeText(this, tv2.getText().toString(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(this, "Won", Toast.LENGTH_SHORT).show();
                    revealAll();
                }
            }
        }
    }

    private void setBackground(MineButton btn) {
        switch (btn.getValue()){
            case 0:btn.setBackgroundResource(R.drawable.a0);
            break;
            case 1:btn.setBackgroundResource(R.drawable.a1);
            break;
            case 2:btn.setBackgroundResource(R.drawable.a2);
                break;
            case 3:btn.setBackgroundResource(R.drawable.a3);
                break;
            case 4:btn.setBackgroundResource(R.drawable.a4);
                break;
            case 5:btn.setBackgroundResource(R.drawable.a5);
                break;
            case 6:btn.setBackgroundResource(R.drawable.a6);
                break;
            case 7:btn.setBackgroundResource(R.drawable.a7);
                break;
            case 8:btn.setBackgroundResource(R.drawable.a8);
                break;
            case -10:btn.setBackgroundResource(R.drawable.bomb1);
        }
    }

    //int row1,col1;
    private void reveal(MineButton btn1) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                MineButton currentButton = btn[i][j];
                if(btn1.getId()==currentButton.getId()){
                  //  row1=i;
                    //col1=j;
                    revealNeighbour(i,j,0,0);

                }
            }
        }
    }

    private void revealNeighbour(int row, int col, int i ,int j) {


        if (i >= arrRow.length && j >= arrCol.length) {
            return;
        }
        if (row + arrRow[i] < size && col + arrCol[j] < size && row + arrRow[i] > -1 && col + arrCol[j] > -1) {
            MineButton currentButton = btn[row + arrRow[i]][col + arrCol[j]];
            if (!currentButton.isEnabled())
            {
                revealNeighbour(row,col,i + 1, j + 1);
                return;
            }
            if (currentButton.getValue() != Bomb) {
                currentButton.setInfo(String.valueOf(currentButton.getValue()));
                currentButton.setEnabled(false);
                setBackground(currentButton);
            }
            if (currentButton.getValue() == DEFAULT) {
                revealNeighbour(row+arrRow[i],col+arrCol[j],0, 0);
            }
        }
        revealNeighbour(row,col,i + 1, j + 1);

    }
    private void revealAll() {
        handler.removeCallbacks(runnable);
        for (int i = 0; i < size; i++){
            for(int j=0;j<size;j++){
                MineButton currentButton=btn[i][j];
                if(currentButton.getValue()==Bomb){
                    currentButton.setInfo("B");
                }
                else
                currentButton.setInfo(String.valueOf(currentButton.getValue()));
                currentButton.setEnabled(false);
                setBackground(currentButton);
            }
        }

    }
    public void reset(View v){
        inIt();
    }

    @Override
    public boolean onLongClick(View view) {
        MineButton btn1=(MineButton)view;
        if(btn1.getInfo().equals("F")) {
            btn1.setBackgroundResource(R.drawable.facing);
            btn1.setInfo("");
            if (btn1.getValue() == -10)
            bomb--;

            String s=tv.getText().toString();
            int n=Integer.parseInt(s);
            if(n<countBomb) {
                n++;
                if (n < 10)
                    tv.setText("0" + String.valueOf(n));
                else
                    tv.setText(String.valueOf(n));
            }
            no_flag--;
        }
        else {
            if(no_flag<countBomb) {
                btn1.setInfo("F");
                String s = tv.getText().toString();
                int n = Integer.parseInt(s);
                n--;
                if (n > -1) {
                    if (n < 10)
                        tv.setText("0" + String.valueOf(n));
                    else
                        tv.setText(String.valueOf(n));
                }
                btn1.setBackgroundResource(R.drawable.flagged);
                no_flag++;
            }
            if (btn1.getValue() == -10)
                bomb++;
            if (bomb==num && allButtonRevealed()) {
                gameOver = true;
                //t.cancel();
                Toast.makeText(this, tv2.getText().toString(), Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "Won", Toast.LENGTH_SHORT).show();
                revealAll();
            }
        }
        return true;
    }

    private boolean allButtonRevealed() {
        boolean over = true;
        int r=0,c=0;
        int count=0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (btn[i][j].getInfo().equals("abcde")) {
                    count++;
                    r=i;
                    c=j;
                    over = false;
                }
            }
        }
        if(count==1) {
            if (btn[r][c].getValue() == -10) {
                setBackground(btn[r][c]);
                btn[r][c].setInfo("F");
                return true;
            }
        }
        return over;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case R.id.reset:inIt();
            break;
            case R.id.five: size=5;item.setChecked(true);inIt();
            break;
            case R.id.ten:size=10;item.setChecked(true);inIt();
            break;
            case R.id.twelve:size=12;item.setChecked(true);inIt();
            break;
            case R.id.easy:s="Easy";item.setChecked(true);inIt();
            break;
            case R.id.medium:s="Medium";item.setChecked(true);inIt();
            break;
            case R.id.hard:s="Hard";item.setChecked(true);inIt();
        }
        return true;
    }
}