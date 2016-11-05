package com.mygdx.server;

import com.badlogic.gdx.Gdx;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.mygdx.characters.Centauro;
import com.mygdx.characters.Invader;
import com.mygdx.characters.Megaman;
import com.mygdx.game.GenericMap;

import java.net.URISyntaxException;

/**
 * Created by alber on 05/11/2016.
 */

public class ComunicationProtocol {

    private String SERVERNAME;
    private String SERVERPORT;
    private Socket mSocket;
    private GenericMap map;

    public ComunicationProtocol(String port, GenericMap map) {
        this.map = map;

        SERVERNAME = "http://192.168.3.1";
        SERVERPORT = port;

        try {
            String _url = SERVERNAME + ":" + SERVERPORT;
            mSocket = IO.socket(_url);
            mSocket.on("receive", onNewMessage);
            mSocket.on("connected", onConnected);
            Gdx.app.log("NEW_CONNECTION", "Port " + SERVERPORT);
        } catch (URISyntaxException e) {
            Gdx.app.log("ERROR", "Error " + SERVERPORT);
            e.printStackTrace();
        }
    }


    public void connect(){
        mSocket = mSocket.connect();
        Gdx.app.log("CONNECT", "Port " + SERVERPORT);

    }

    public void disconnect(){
        mSocket.close();
    }

    //LLAMAR A ESTA FUNCION CUANDO SE SALGAN DE PANTALLA
    public void notifyNewBicho(int posx, int bicho){
        String data = SERVERPORT + ":" + posx + ":" + bicho;
        mSocket.emit("message", data);
    }

    //ON NEW MESSAGE RECEIVED
    private Emitter.Listener onNewMessage = new Emitter.Listener() {

        @Override
        public void call(final Object... args) {
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    String data = (String) args[0];
                    String[] info = data.split(":");

                    String user = info[0];
                    int posx = Integer.valueOf(info[1]);
                    int bicho = Integer.valueOf(info[2]);

                    if (bicho == 1) {
                        map.stage.addActor(new Invader(posx, Gdx.graphics.getHeight(), map, false));
                    }
                    else if (bicho == 2) {
                        map.stage.addActor(new Megaman(posx, Gdx.graphics.getHeight(), map, false));
                    }
                    else {
                        map.stage.addActor(new Centauro(posx, Gdx.graphics.getHeight(), map, false));
                    }


                    //COSICAS NAZIS AQUI

                }
            });
        }
    };

    private Emitter.Listener onConnected = new Emitter.Listener() {

        @Override
        public void call(final Object... args) {
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    //JSONObject data = (JSONObject) args[0];
                    String data = (String) args[0];
                }
            });
        }
    };

}
