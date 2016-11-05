package com.mygdx.characters;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.mygdx.game.GenericMap;

/**
 * Created by alber on 04/11/2016.
 */

public class Invader extends GenericBicho{

    private static final int VEL = 100;
    private static final int HEALTH = 100;
    private static final int ATTACK = 5;
    private static final int PRICE = 5;
    private static final String sTexture = "invader.png";
    private static final double SIZE = 0.1;

    public Invader(int posX, int posY, GenericMap game){
        super(posX, posY, VEL, HEALTH, ATTACK, (float) SIZE, sTexture, PRICE, game);

        this.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int buttons){
                ((Invader)event.getTarget()).started = true;
                setVisible(false);
                ((Invader)event.getTarget()).remove();
                addGold();
                return true;
            }
        });
    }

    private void addGold() {
        this.game.gold += this.getPrice();
        this.game.goldLabel = "GOLD: " + this.game.gold;
    }

}
