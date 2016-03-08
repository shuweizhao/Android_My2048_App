package com.example.shuweizhao.my2048;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shuweizhao on 1/6/16.
 */
public class GameView extends GridLayout {

    private Card[][] cardMap = new Card[4][4];
    private List<Point> emptyList = new ArrayList<>();

    public static GameView gameView;

    public static GameView getGameView() {
        return gameView;
    }
    public GameView(Context context) {
        super(context);
        gameView = this;
        initView();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        gameView = this;
        initView();

    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        gameView = this;
        initView();
    }

    public void initView() {
        setColumnCount(4);
        setBackgroundColor(0xffCDDC39);

        setOnTouchListener(new OnTouchListener() {
            private float startX, startY, offsetX, offsetY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        offsetX = event.getX() - startX;
                        offsetY = event.getY() - startY;

                        if (Math.abs(offsetX) > Math.abs(offsetY)) {
                            if (offsetX < -5) {
                                swipeLeft();
                            } else if (offsetX > 5) {
                                swipeRight();
                            }
                        } else {
                            if (offsetY < -5) {
                                swipeUp();
                            } else if (offsetY > 5) {
                                swipeDown();
                            }
                        }
                        break;
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        startY = event.getY();
                        break;
                    default:
                        break;

                }
                return true;
            }
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int cardWidth = (Math.min(w, h)-10) / 4;
        addCards(cardWidth);
        startGame();
    }

    private void addCards(int cardWidth) {

        Card c;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                c = new Card(getContext());
                c.setNumber(0);
                cardMap[j][i] = c;
                addView(c, cardWidth, cardWidth);
            }
        }
    }

    public void startGame() {
        MainActivity.getMainActivity().clearScore();
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                cardMap[x][y].setNumber(0);
            }
        }
        addRandomNum();
        addRandomNum();
    }

    private void addRandomNum() {
        emptyList.clear();
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (cardMap[x][y].getNumber() <= 0) {
                    emptyList.add(new Point(x, y));
                }
            }
        }
        Point p = emptyList.remove((int)(Math.random() * emptyList.size()));
        cardMap[p.x][p.y].setNumber(Math.random() > 0.1 ? 2 : 4);
    }

    private void swipeLeft() {
        boolean refresh = false;
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                for (int xx = x + 1; xx < 4; xx++) {
                    if (cardMap[xx][y].getNumber() == 0) {
                        continue;
                    }
                    if (cardMap[x][y].getNumber() == 0) {
                        cardMap[x][y].setNumber(cardMap[xx][y].getNumber());
                        cardMap[xx][y].setNumber(0);
                        x--;
                        refresh = true;
                        break;
                    }
                    else if (cardMap[x][y].equals(cardMap[xx][y])) {
                        cardMap[x][y].setNumber(cardMap[x][y].getNumber()*2);
                        cardMap[xx][y].setNumber(0);
                        MainActivity.getMainActivity().addScore(cardMap[x][y].getNumber());
                        refresh = true;
                        break;
                    }
                }
            }
        }
        if (refresh) {
            addRandomNum();
            checkComplete();
        }
    }
    private void swipeRight() {
        boolean refresh = false;
        for (int y = 0; y < 4; y++) {
            for (int x = 3; x >= 0; x--) {
                for (int xx = x - 1; xx >= 0; xx--) {
                    if (cardMap[xx][y].getNumber() == 0) {
                        continue;
                    }
                    if (cardMap[x][y].getNumber() == 0) {
                        cardMap[x][y].setNumber(cardMap[xx][y].getNumber());
                        cardMap[xx][y].setNumber(0);
                        x++;
                        refresh = true;
                        break;
                    }
                    else if (cardMap[x][y].equals(cardMap[xx][y])) {
                        cardMap[x][y].setNumber(cardMap[x][y].getNumber()*2);
                        cardMap[xx][y].setNumber(0);
                        MainActivity.getMainActivity().addScore(cardMap[x][y].getNumber());
                        refresh = true;
                        break;
                    }
                }
            }
        }
        if (refresh) {
            addRandomNum();
            checkComplete();
        }
    }
    private void swipeUp() {
        boolean refresh = false;
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                for (int yy =y + 1; yy < 4; yy++) {
                    if (cardMap[x][yy].getNumber() == 0) {
                        continue;
                    }
                    if (cardMap[x][y].getNumber() == 0) {
                        cardMap[x][y].setNumber(cardMap[x][yy].getNumber());
                        cardMap[x][yy].setNumber(0);
                        y--;
                        refresh = true;
                        break;
                    }
                    else if (cardMap[x][y].equals(cardMap[x][yy])) {
                        cardMap[x][y].setNumber(cardMap[x][yy].getNumber()*2);
                        cardMap[x][yy].setNumber(0);
                        MainActivity.getMainActivity().addScore(cardMap[x][y].getNumber());
                        refresh = true;
                        break;
                    }
                }
            }
        }
        if (refresh) {
            addRandomNum();
            checkComplete();
        }
    }
    private void swipeDown() {
        boolean  refresh = false;
        for (int x = 0; x < 4; x++) {
            for (int y = 3; y >= 0; y--) {
                for (int yy =y - 1; yy >= 0; yy--) {
                    if (cardMap[x][yy].getNumber() == 0) {
                        continue;
                    }
                    if (cardMap[x][y].getNumber() == 0) {
                        cardMap[x][y].setNumber(cardMap[x][yy].getNumber());
                        cardMap[x][yy].setNumber(0);
                        y++;
                        refresh = true;
                        break;
                    }
                    else if (cardMap [x][y].equals(cardMap[x][yy])) {
                        cardMap[x][y].setNumber(cardMap[x][yy].getNumber()*2);
                        cardMap[x][yy].setNumber(0);
                        MainActivity.getMainActivity().addScore(cardMap[x][y].getNumber());
                        refresh = true;
                        break;
                    }
                }
            }
        }
        if (refresh) {
            addRandomNum();
            checkComplete();
        }
    }

    private void checkComplete() {
        boolean complete = true;
        out:for (int y = 0; y < 4; y++) {
                for (int x = 0; x < 4; x++) {
                    if (cardMap[x][y].getNumber() == 0 ||
                            (x > 0 && cardMap[x - 1][y].equals(cardMap[x][y])) ||
                            (x < 3 && cardMap[x + 1][y].equals(cardMap[x][y])) ||
                            (y > 0 && cardMap[x][y - 1].equals(cardMap[x][y])) ||
                            (y < 3 && cardMap[x][y + 1].equals(cardMap[x][y]))) {
                        complete = false;
                        break out;
                    }
                }
        }
        if (complete) {
            new AlertDialog.Builder(getContext()).setMessage("Game Ends!").setPositiveButton("Try Again!", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startGame();
                }
            });
        }
    }


}
