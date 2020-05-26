package com.example.placeholders;

import android.os.Bundle;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.transition.ChangeBounds;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

public class ConstraintSets extends AppCompatActivity {
    private ConstraintLayout layoutSet;
    private ConstraintSet constraintSetOld = new ConstraintSet();
    private ConstraintSet constraintSetNew = new ConstraintSet();
    private boolean altLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.constraint_sets);

        layoutSet = findViewById(R.id.layoutSet);

        constraintSetOld.clone(layoutSet);
        constraintSetNew.clone(this, R.layout.constraint_sets_alt);
    }

    public void swapView(View v){
        Transition changeBounds = new ChangeBounds();
        changeBounds.setInterpolator(new OvershootInterpolator());

        TransitionManager.beginDelayedTransition(layoutSet, changeBounds);
        if(!altLayout){
            constraintSetNew.applyTo(layoutSet);
            altLayout = true;
        } else  {
            constraintSetOld.applyTo(layoutSet);
            altLayout = false;
        }
    }
}
