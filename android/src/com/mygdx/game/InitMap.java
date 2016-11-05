package com.mygdx.game;

import android.content.Intent;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

/**
 * Created by alber on 05/11/2016.
 */

public class InitMap extends AndroidApplication {
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

        Intent i = getIntent();
        Bundle extras = i.getExtras();

        if (extras.getString("map").compareTo("attack")==0){
            initialize(new AttackMap(), config);
        }
        else {
            initialize(new DefenseMap(), config);
        }
    }
}
