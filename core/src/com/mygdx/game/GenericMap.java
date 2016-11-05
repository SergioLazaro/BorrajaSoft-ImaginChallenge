package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.server.ComunicationProtocol;

/**
 * Created by alber on 05/11/2016.
 */

public abstract class GenericMap {


    public int gold;
    public String goldLabel, ammoLabel;
    public int ammo;
    public ComunicationProtocol cp;
    public Stage stage;
    public String end;
    public Skin skin;

    protected void isEnd(){
        new Thread(new Runnable() {
            @Override
            public void run() {

                while (true) {
                    if (end != null && end.compareTo("") != 0) {
                        Dialog diag = new Dialog("Game over: " + end, skin, "default").show(stage);
                        diag.scaleBy(4);
                        diag.setPosition(Math.round(Gdx.graphics.getWidth()/7), Math.round(Gdx.graphics.getHeight()/2));
                        stage.addActor(diag);

                        //Thread.currentThread().interrupt();
                        return;
                    }

                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        Gdx.app.log("INTERRUPT", "Interrupt");

                    }
                }

            }
        }).start();
    }
}
