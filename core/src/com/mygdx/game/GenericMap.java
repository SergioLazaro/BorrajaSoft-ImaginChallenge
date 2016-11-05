package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.Stage;
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

}
