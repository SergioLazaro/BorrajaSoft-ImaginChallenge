package com.example.android.socketapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.net.URISyntaxException;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

public class MainActivity extends AppCompatActivity {

    private String SERVERNAME;
    private String SERVERPORT;
    private Socket mSocket;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = getApplicationContext();

        //SERVERNAME = "http://borrajasoftnode-sergiolazaro.rhcloud.com";
        //SERVERNAME = "https://borrajasoft-node.herokuapp.com";
        SERVERNAME = "http://192.168.3.1";
        SERVERPORT = "1234";

        //new ClientSocket(getApplicationContext()).execute(SERVERIP,SERVERPORT2);
        Button button = (Button) findViewById(R.id.button);
        Button buttonEnd = (Button) findViewById(R.id.buttonEnd);
        Button buttonConnect = (Button) findViewById(R.id.buttonConnect);

        try {
            String _url = SERVERNAME + ":" + SERVERPORT;
            mSocket = IO.socket(_url);
            Log.e("CONNECTING TO...",_url);
            mSocket.on("receive", onNewMessage);
            mSocket.on("connected", onConnected);
            mSocket.on("warning", onWarning);
            mSocket.on("endgame",onEndGame);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void connect(View view){
        mSocket.connect();
    }

    public void send(View view){
        notifyNewBicho(10,10);
    }

    public void end(View view){
        Toast.makeText(mContext,"EXITING",Toast.LENGTH_SHORT).show();
        notifyEnd();
    }

    public void notifyEnd(){
        mSocket.close();
    }

    public void notifyEndGame(){
        mSocket.emit("result","");
    }

    //LLAMAR A ESTA FUNCION CUANDO SE SALGAN DE PANTALLA
    public void notifyNewBicho(int posx, int bicho){
        String data = SERVERPORT + ":" + posx + ":" + bicho;
        mSocket.emit("message", data);
        Log.e("SENDING MESSAGE","foo bar");
 }

    //ON NEW MESSAGE RECEIVED
    private Emitter.Listener onNewMessage = new Emitter.Listener() {

        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String data = (String) args[0];
                    String[] info = data.split(":");

                    String user = info[0];
                    int posx = Integer.valueOf(info[1]);
                    int bicho = Integer.valueOf(info[2]);
                    Toast.makeText(mContext,posx + " - " + bicho, Toast.LENGTH_SHORT).show();


                    //COSICAS NAZIS AQUI

                }
            });
        }
    };

    private Emitter.Listener onConnected = new Emitter.Listener() {

        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //JSONObject data = (JSONObject) args[0];
                    String data = (String) args[0];
                    Toast.makeText(mContext,"RECEIVE: " + data, Toast.LENGTH_SHORT).show();
                }
            });
        }
    };

    private Emitter.Listener onWarning = new Emitter.Listener() {

        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //JSONObject data = (JSONObject) args[0];
                    String data = (String) args[0];
                    Toast.makeText(mContext,data, Toast.LENGTH_SHORT).show();
                }
            });
        }
    };

    private Emitter.Listener onEndGame = new Emitter.Listener() {

        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //JSONObject data = (JSONObject) args[0];
                    String data = (String) args[0];
                    Toast.makeText(mContext,data, Toast.LENGTH_LONG).show();
                }
            });
        }
    };
}
