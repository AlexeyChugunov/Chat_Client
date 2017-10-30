package com.example.ghhos.chat_client.api;


import android.app.Fragment;
import android.content.Context;
import android.widget.Toast;
import android.os.Bundle;

import com.example.ghhos.chat_client.ui.activity.MainActivity;
import com.example.ghhos.chat_client.ui.fragments.FragmentLobby;
import com.example.ghhos.chat_client.ui.fragments.FragmentRA;
import com.example.ghhos.chat_client.ui.fragments.FragmentRoom;
import com.google.gson.Gson;

import org.java_websocket.client.WebSocketClient;

import java.util.Objects;
//******************************* СОГЛАСОВАТЬ КОМАНДЫ ОТПРАВКИ!!!!!************
public class Commander extends Fragment{

    public static void listenLoop(String message, Context context) throws InterruptedException {
        Request req = new Gson().fromJson(message, Request.class);

        switch (req.modul) {
            case "ok":
                if(Objects.equals(req.data, "admin")){
                    FragmentManager.lobbyFrag.username_my=req.data;
                   FragmentManager.lobbyFrag.ShowLobbyAdmin();
                    }
                else {
                    FragmentManager.lobbyFrag.username_my=req.data;
                    FragmentManager.lobbyFrag.ShowLobby();
                }
                break;
            case "badlogin":
                Toast.makeText(context, "User already exist",
                        Toast.LENGTH_LONG).show();
                break;
            case "refresh":
                FragmentLobby er = FragmentManager.lobbyFrag;
                String[] splitrooms = req.data.split("\\.");
                er.SetRooms(splitrooms, context);
                break;
            case "refreshclients":
                FragmentLobby erc = FragmentManager.lobbyFrag;
                String[] splitclients = req.data.split("\\.");
                erc.SetClients(splitclients, context);
                break;
            case "enter":
                FragmentManager.frRoom.CreateRoom(req.data, req.command, context);
                break;
            case "message":
                FragmentManager.frRoom.SetMessage(req.data, context);
                break;
            case "leave":
                FragmentManager.lobbyFrag.ShowLobby();
                break;
            case "wrongroom":
                Toast.makeText(context, "Error, room " + req.data + " has already been created.", Toast.LENGTH_LONG)
                        .show();
                break;
            case "youbanned":
                Toast.makeText(context, "You are Baned!!!", Toast.LENGTH_LONG)
                        .show();
                break;
            case "alreadyenter":
                Toast.makeText(context, "You are already logged into this room.", Toast.LENGTH_LONG)
                        .show();
                break;
            case "bancreate":
                Toast.makeText(context, "You have been banned for " + req.data, Toast.LENGTH_LONG)
                        .show();
                break;
            case "passive"://пропущенные сообщения, !!!!! Modul: passive, Command: rommname, data: 0(missed massage)
                //FragmentManager.lobbyFrag.ShowLobby();
//                FragmentLobby ps = (FragmentLobby) MainActivity.fragments.get(1);
                //FragmentManager.lobbyFrag.Passive(req.command, req.data, req.time, context);
                break;
            case "newpass":
                Toast.makeText(context, "Password changed", Toast.LENGTH_LONG)
                        .show();
                break;
            case "wrongnewpass":
                Toast.makeText(context, "Incorrect old password", Toast.LENGTH_LONG)
                        .show();
                break;
        }
    }
}
