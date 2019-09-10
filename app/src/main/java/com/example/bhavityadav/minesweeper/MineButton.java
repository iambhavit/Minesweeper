package com.example.bhavityadav.minesweeper;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;

import static com.example.bhavityadav.minesweeper.MainActivity.DEFAULT;

/**
 * Created by Bhavit Yadav on 03-02-2018.
 */

public class MineButton extends AppCompatButton {
    private int value;
    private String info;
    public MineButton(Context context) {
        super(context);
        this.value=DEFAULT;
        this.info="abcde";
    }

    public String getInfo(){return info;}
    public int getValue() {
        return value;
    }

    public void setInfo(String info){this.info=info;}
    public void setValue(int value) {
        this.value = value;
    }
}
