package com.hamza.expandingfab;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity{

    FloatingActionButton fab;

    boolean isEntryAnimationNext = true;
    private Button[] buttons;

    int LINE_BUTTON = 200;
    Point[] linePoints;
    private boolean buttonsCreated = false;
    boolean linePointsGenerated = false;
    int black;
    int accent;
    int darkAccent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        black = getResources().getColor(R.color.black);
        accent = getResources().getColor(R.color.colorAccent);
        darkAccent = getResources().getColor(R.color.darkAccent);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int width = fab.getWidth();
                int height = fab.getHeight();
                int x = (int) fab.getX();
                int y = (int) fab.getY();

                Log.d("fabx", x + "");
                if (isEntryAnimationNext){

                    for (Button b : buttons){
                        b.setWidth(width);
                        b.setHeight(height);
                        b.setX(x);
                        b.setY(y);
                        b.setVisibility(View.VISIBLE);
                    }

                    animateColorChange(fab, darkAccent, accent);
                    for (int i = 0; i < buttons.length; i++) {
                        playExpandAnimation(buttons[i], i);
                    }
                    isEntryAnimationNext = false;
                }
                else{
                    animateColorChange(fab, accent, darkAccent);
                    for (int i = 0; i < buttons.length; i++) {
                        playContractAnimation(buttons[i], i);
                    }
                    isEntryAnimationNext = true;
                }
            }
        });

        fab.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (!buttonsCreated){createButtons();}
                if (!linePointsGenerated){generateLinePoints();}
            }
        });


    }

    private void generateLinePoints() {

        linePoints = new Point[4];
        int fabHeight = fab.getHeight();
        int fabRadius = fabHeight/2;
        int x = (int) fab.getX();
        int y = (int) fab.getY() + fabRadius; // center of FAB

        int padding = 20;

        linePoints[0] = new Point(x, y - fabHeight - fabRadius - padding);
        linePoints[1] = new Point(x, linePoints[0].y - fabHeight - padding);
        linePoints[2] = new Point(x, y + fabHeight - fabRadius + padding);
        linePoints[3] = new Point(x, linePoints[2].y + fabHeight + padding);


        linePointsGenerated = true;
    }

    private void playContractAnimation(final Button button, int i){
        ViewPropertyAnimator animator =
                button.animate()
                        .y(fab.getY())
                        .setInterpolator(new FastInSlowOutInterpolator())
                        .setDuration(250)
                        .rotationBy(-360);

        animator.setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                button.setRotation(0);
                button.setY(fab.getY());
            }
        });
    }

    private void playExpandAnimation(final Button button, int i) {

        int fabHeight = fab.getHeight();
        button.setX(fab.getX());

        if (i < 2) {
            ViewPropertyAnimator animator =
                button.animate()
                    .y(linePoints[i].y)
                    .setDuration(250)
                    .setInterpolator(new FastOutSlowInInterpolator())
                    .rotationBy(360);

            animator.setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    button.setRotation(360);
                }
            });
        }
        else {
            ViewPropertyAnimator animator =
                    button.animate()
                            .y(linePoints[i].y)
                            .setDuration(250)
                            .setInterpolator(new FastOutSlowInInterpolator())
                            .rotationBy(360);

            animator.setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    button.setRotation(360);
                }
            });
        }

    }

    public void animateColorChange(final FloatingActionButton fab, int fromColor, final int targetColor){
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(fab,
                "backgroundColor",
                fromColor,
                targetColor
        );
        objectAnimator.setInterpolator(new FastOutSlowInInterpolator());
        objectAnimator.setEvaluator(new ArgbEvaluator());
        objectAnimator.setDuration(300);
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                fab.setBackgroundTintList(ColorStateList.valueOf(targetColor));
            }
        });

        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value = (int) valueAnimator.getAnimatedValue();
                fab.setBackgroundTintList(ColorStateList.valueOf(value));
            }
        });


        objectAnimator.start();
    }

    private void createButtons() {
        buttons = new Button[4];

        int fabHeight = fab.getHeight();
        int fabWidth = fab.getWidth();
        for (int i = 0; i < buttons.length; i++) {

            buttons[i] = new Button(MainActivity.this);
            buttons[i].setLayoutParams(new RelativeLayout.LayoutParams(fabWidth, fabHeight));
            buttons[i].setX(0);
            buttons[i].setY(0);
//            buttons[i].setTag(i);
//            buttons[i].setOnClickListener(this);
            buttons[i].setVisibility(View.INVISIBLE);
            buttons[i].setBackgroundResource(R.drawable.circle);
            buttons[i].setTextColor(Color.WHITE);
            buttons[i].setText(String.valueOf(i + 1));
            buttons[i].setTextSize(20);

            ((RelativeLayout) findViewById(R.id.activity_main)).addView(buttons[i]);
        }

        buttonsCreated = true;
    }


}
