package com.mygdx.characters;


import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class DefaultBicho extends GenericBicho {

    private static final int VEL = 100;
    private static final int HEALTH = 100;
    private static final int ATTACK = 5;
    private static final double SIZE = 0.1;
    private static final String sTexture = "invader.png";


    public DefaultBicho(int posX, int posY){
        super(posX, posY, VEL, HEALTH, ATTACK, (float) SIZE, sTexture);

        this.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int buttons){
                ((DefaultBicho)event.getTarget()).started = true;
                setVisible(false);
                return true;
            }
        });
    }

}
