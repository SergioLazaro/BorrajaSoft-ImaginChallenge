package com.mygdx.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.mygdx.game.GenericMap;


public abstract class GenericBicho extends Actor {

    public int health;
    protected int vel;
    protected int attack;
    protected float size;
    protected Texture texture;
    public boolean started = false;
    protected int posX, posY;
    protected int price;
    protected GenericMap game;
    protected boolean moveAttack;


    public GenericBicho(int posX, int posY, int vel, int health, int attack, float size, String t, int price, GenericMap game, boolean moveAttack) {
        this.texture = new Texture(t);
        this.vel = vel;
        this.health = health;
        this.attack = attack;
        this.size = size;
        this.price = price;
        this.game = game;
        this.moveAttack = moveAttack;

        this.posX = Math.max(0,Math.round(posX-Gdx.graphics.getWidth()*size/2));
        this.posY = Math.max(0,Math.round(posY-Gdx.graphics.getWidth()*size/2));

        setBounds(this.posX, this.posY, Gdx.graphics.getWidth()*size, Gdx.graphics.getWidth()*size);

        if (moveAttack) {
            this.addAction(Actions.moveTo(this.posX, Gdx.graphics.getHeight(), (Gdx.graphics.getHeight() - posY) / vel));
        }
        else {
            this.addAction(Actions.moveTo(this.posX, 0, posY/vel));
        }

        this.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int buttons){
                if (deductAmmo()) {
                    ((GenericBicho) event.getTarget()).health -= 10;
                    if (((GenericBicho) event.getTarget()).health <= 0) {
                        ((GenericBicho) event.getTarget()).started = true;
                        setVisible(false);
                        ((GenericBicho) event.getTarget()).remove();
                        addGold();
                    }
                }
                return true;
            }
        });


    }

    private void addGold() {
        this.game.gold += this.getPrice();
        this.game.goldLabel = "GOLD: " + this.game.gold;
    }

    private boolean deductAmmo() {
        if (game.ammo > 0) {
            this.game.ammo--;
            this.game.ammoLabel = "AMMO: " + this.game.ammo;
            return true;
        }else { return false; }
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

        // Actor out of screen
        if (moveAttack && this.getY() >= Gdx.graphics.getHeight()) {  // Remove actor from stage

            if (this instanceof Invader) {
                game.cp.notifyNewBicho(Math.round(this.getX()), 1);
            }
            else if(this instanceof Megaman) {
                game.cp.notifyNewBicho(Math.round(this.getX()), 2);
            }
            else {
                game.cp.notifyNewBicho(Math.round(this.getX()), 3);
            }

            this.remove();
        }

        if (!moveAttack && this.getY() == 0) {  // Remove actor from stage
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

    /*protected void goldWin(){
        Gdx.app.log("MEGAMAN INVISIBLE", "DEBERIA BORRAR");
        GlyphLayout goldLayout = new GlyphLayout();
        BitmapFont font = new BitmapFont();
        String goldLabel = "+3"; // SET TO A CALCULATED VALUE, NOT A FIXED VALUE
        font.getData().setScale(3);
        font.setColor(Color.GOLD);

        goldLayout.setText(font, goldLabel);
        float w = this.getX();
        float y = this.getY();
        game.batch.begin();
        font.draw(game.batch, goldLabel, Gdx.graphics.getWidth()*size, Gdx.graphics.getWidth()*size);
        game.batch.end();
    }*/
}
