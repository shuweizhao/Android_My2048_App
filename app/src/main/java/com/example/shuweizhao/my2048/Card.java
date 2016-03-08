package com.example.shuweizhao.my2048;

import android.content.Context;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by shuweizhao on 1/6/16.
 */
public class Card extends FrameLayout {

    private int number;
    private TextView tv;

    public Card(Context context) {
        super(context);
        tv = new TextView(getContext());
        tv.setTextSize(32);
        tv.setGravity(Gravity.CENTER);
        setNumber(0);
        tv.setBackgroundColor(0xffF0F4C3);
        LayoutParams lp = new LayoutParams(-1, -1);
        lp.setMargins(10, 10, 0, 0);
        addView(tv, lp);
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
        if(this.number <= 0) {
            tv.setText("");
        }
        else {
            tv.setText("" + number);
        }
    }

    public boolean equals(Card c) {
        return getNumber() == c.getNumber();
    }


}
