package com.mygdx.characters;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * Created by alber on 04/11/2016.
 */

public class Megaman extends GenericBicho{

    private static final int VEL = 100;
    private static final int HEALTH = 100;
    private static final int ATTACK = 5;
    private static final String sTexture = "megaman.png";
    private static final double SIZE = 0.1;


    public Megaman(int posX, int posY){
        super(posX, posY, VEL, HEALTH, ATTACK, (float) SIZE, sTexture);

        this.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int buttons){
                ((Megaman)event.getTarget()).started = true;
                setVisible(false);
                return true;
            }
        });
    }

}
