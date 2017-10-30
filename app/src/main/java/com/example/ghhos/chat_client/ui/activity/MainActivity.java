package com.example.ghhos.chat_client.ui.activity;
/*
*************  SUPER CHAT***************
 */
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.ghhos.chat_client.api.Commander;
import com.example.ghhos.chat_client.R;
import com.example.ghhos.chat_client.api.FragmentManager;
import com.example.ghhos.chat_client.ui.fragments.FragmentLobby;
import com.example.ghhos.chat_client.ui.fragments.FragmentRA;
import com.example.ghhos.chat_client.ui.fragments.FragmentRoom;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private WebSocketClient webSocket;
    public static ArrayList<Fragment> fragments;
    public static Context context;
    public static Handler UIHandler;

    static
    {
        UIHandler = new Handler(Looper.getMainLooper());
    }

    public static void runOnUI(Runnable runnable) {
        UIHandler.post(runnable);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitWebSocket();
        FragmentManager.InitWebSocket(webSocket);

        fragments = new ArrayList<Fragment>();
        context = this;

        FragmentRA fragmentRA = new FragmentRA();
        FragmentLobby fragmentLobby = new FragmentLobby();
        FragmentRoom fragmentRoom = new FragmentRoom();
        fragments.add(fragmentRA);
        fragments.add(fragmentLobby);
        fragments.add(fragmentRoom);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.main_activity_fragment_placeholder,FragmentManager.lobbyFrag);
        transaction.commit();
    }

    public void InitWebSocket(){
        URI uri;
        try {
            uri = new URI("ws://10.0.2.2:8888/");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        webSocket = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                Log.i("Websocket", "Opened");
            }

            @Override
            public void onMessage(String s) {
                final String msg = s;

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Commander.listenLoop(msg,context);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Log.i("Websocket", msg);
                    }});
            }
            @Override
            public void onClose(int i, String s, boolean b) {
                Log.i("Websocket", "Closed " + s);
            }
            @Override
            public void onError(Exception e) {
                Log.i("Websocket", "Error " + e.getMessage());
            }
        };
        webSocket.connect();
    }
}
