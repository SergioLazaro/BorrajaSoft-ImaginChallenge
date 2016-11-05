package com.mygdx.characters;

import com.mygdx.game.GenericMap;


/**
 * Created by alber on 04/11/2016.
 */

public class Megaman extends GenericBicho{

    private static final int VEL = 150;
    private static final int HEALTH = 50;
    private static final int ATTACK = 5;
    private static final int PRICE = 5;
    private static final String sTexture = "megaman.png";
    private static final double SIZE = 0.2;

    public Megaman(int posX, int posY, GenericMap game, boolean movAttack){
        super(posX, posY, VEL, HEALTH, ATTACK, (float) SIZE, sTexture, PRICE, game, movAttack);

    }

}
