package com.mygdx.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.mygdx.game.GenericMap;


public abstract class GenericBicho extends Actor {

    protected int health;
    protected int vel;
    protected int attack;
    protected float size;
    protected Texture texture;
    public boolean started = false;
    protected int posX, posY;
    protected int price;
    protected GenericMap game;


    public GenericBicho(int posX, int posY, int vel, int health, int attack, float size, String t, int price, GenericMap game) {
        this.texture = new Texture(t);
        this.vel = vel;
        this.health = health;
        this.attack = attack;
        this.size = size;
        this.price = price;
        this.game = game;

        this.posX = Math.max(0,Math.round(posX-Gdx.graphics.getWidth()*size/2));
        this.posY = Math.max(0,Math.round(posY-Gdx.graphics.getWidth()*size/2));

        setBounds(this.posX, this.posY, Gdx.graphics.getWidth()*size, Gdx.graphics.getWidth()*size);

        this.addAction(Actions.moveTo(this.posX, Gdx.graphics.getHeight(), (Gdx.graphics.getHeight()-posY)/vel));

    }


    /**
     * Deducts damage to the current life and returns false if the bicho has died
     */
    public boolean deductLife(int damage) {
        health -= damage;
        return health>0;
    }

    public int getPrice() {return price;}
    @Override
    public void draw(Batch batch, float alpha){
        batch.draw(texture,this.getX(),this.getY(), Gdx.graphics.getWidth()*size, Gdx.graphics.getWidth()*size);

        // Check if actor is inside screen
        if (this.getY() == Gdx.graphics.getHeight()) {  // Remove actor from stage
            this.remove();

            // TODO: Llamada al servidor
        }
    }


    // This hit() instead of checking against a bounding box, checks a bounding circle.
    public Actor hit(float x, float y, boolean touchable){

        // If this Actor is hidden or untouchable, it cant be hit
        if(!this.isVisible() || this.getTouchable() == Touchable.disabled)
            return null;

        // Get centerpoint of bounding circle, also known as the center of the rect
        float centerX = getWidth()/2;
        float centerY = getHeight()/2;

        // Square roots are bad m'kay. In "real" code, simply square both sides for much speedy fastness
        // This however is the proper, unoptimized and easiest to grok equation for a hit within a circle
        // You could of course use LibGDX's Circle class instead.

        // Calculate radius of circle
        float radius = (float) Math.sqrt(centerX * centerX +
                centerY * centerY);

        // And distance of point from the center of the circle
        float distance = (float) Math.sqrt(((centerX - x) * (centerX - x))
                + ((centerY - y) * (centerY - y)));

        // If the distance is less than the circle radius, it's a hit
        if(distance <= radius) {
            return this;
        }

        // Otherwise, it isnt
        return null;
    }
}
