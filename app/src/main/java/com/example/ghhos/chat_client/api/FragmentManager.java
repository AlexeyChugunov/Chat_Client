package com.example.ghhos.chat_client.api;

import com.example.ghhos.chat_client.ui.fragments.FragmentLobby;
import com.example.ghhos.chat_client.ui.fragments.FragmentRA;
import com.example.ghhos.chat_client.ui.fragments.FragmentRoom;


import org.java_websocket.client.WebSocketClient;

public class FragmentManager {


    public static FragmentLobby lobbyFrag;
    public static FragmentRA regFrag;
    public static FragmentRoom frRoom;

    static {
        lobbyFrag = new FragmentLobby();
        regFrag = new FragmentRA();
        frRoom = new FragmentRoom();
    }

    public  static void NewRoom(){
        frRoom=new FragmentRoom();
    }

    public static void InitWebSocket(WebSocketClient webSocket) {
        Websockets.wsa = webSocket;
    }

}
