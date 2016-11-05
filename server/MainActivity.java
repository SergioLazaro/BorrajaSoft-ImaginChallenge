package com.example.android.socketapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;

/**import io.socket.client.IO;
import io.socket.client.Manager;
import io.socket.client.Socket;
import io.socket.client.SocketIOException;
import io.socket.emitter.Emitter;*/

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
        SERVERPORT = "1235";

        //new ClientSocket(getApplicationContext()).execute(SERVERIP,SERVERPORT2);
        Button button = (Button) findViewById(R.id.button);


        try {
            String _url = SERVERNAME + ":" + SERVERPORT;
            mSocket = IO.socket(_url);
            Log.e("CONNECTING TO...",_url);
            mSocket.on("receive", onNewMessage);
            mSocket.on("connected", onConnected);
            mSocket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
    public void send(View view){
        notifyNewBicho(10,10);
    }

    //LLAMAR A ESTA FUNCION CUANDO SE SALGAN DE PANTALLA
    public void notifyNewBicho(int posx, int bicho){
        JSONObject data = new JSONObject();
        try{
            data.put("user",SERVERPORT);
            data.put("posx",posx);
            data.put("bicho",bicho);
            mSocket.emit("message", data.toString());
            Log.e("SENDING MESSAGE","foo bar");
        }
        catch(JSONException ex){

        }
    }

    //ON NEW MESSAGE RECEIVED
    private Emitter.Listener onNewMessage = new Emitter.Listener() {

        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    try {
                        String user = (String) data.get("user");
                        int posx = data.getInt("posx");
                        int bicho = data.getInt("bicho");

                        Toast.makeText(mContext,posx + " - " + bicho, Toast.LENGTH_SHORT).show();
                        //COSICAS NAZIS AQUI

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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
}
