package com.example.ghhos.chat_client.ui.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ghhos.chat_client.R;
import com.example.ghhos.chat_client.api.Commander;
import com.example.ghhos.chat_client.api.Commander_Out;
import com.example.ghhos.chat_client.api.FragmentManager;
import com.example.ghhos.chat_client.api.Request;
import com.example.ghhos.chat_client.ui.activity.MainActivity;
import com.google.gson.Gson;

import org.java_websocket.client.WebSocketClient;

public class FragmentRoom extends Fragment {

    public boolean PrivateRoom=false;
    @Nullable
    Button bSend;
    Button bClose;
    Button bExit;
    EditText etSend;
    ListView lvMessages=null;
    TextView tvRoomName;

    String name;//имя комнаты
    ArrayAdapter<String> adapter;
    String data = "";

    final Gson gson = new Gson();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_room, container, false);

        bSend = (Button) view.findViewById(R.id.fr_room_bSend);
        bClose = (Button) view.findViewById(R.id.fr_room_bClose);
        etSend = (EditText) view.findViewById(R.id.fr_room_etSend);
        lvMessages = (ListView) view.findViewById(R.id.lv_messages);
        bExit = (Button) view.findViewById(R.id.fr_room_bExit);
        tvRoomName = (TextView) view.findViewById(R.id.fr_room_roomname);

        tvRoomName.setText(name);

        lvMessages.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        etSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i = 0;i < lvMessages.getCount(); i++)
                {
                    lvMessages.setItemChecked(i, false);
                }
            }
        });

        bSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!PrivateRoom){Commander_Out.SendMsg(etSend.getText().toString(),name);
                etSend.setText("");}
                else {Commander_Out.SendPrivateMsg(etSend.getText().toString(),name);
                    etSend.setText("");}
            }
        });

        bClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!PrivateRoom)
                {Commander_Out.SendCloseRoom(name);}
                else {Commander_Out.SendClosePrivateRoom(name);}
                lvMessages.setAdapter(null);
                Leave();
            }
        });

        bExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!PrivateRoom){Commander_Out.SendExitRoom(name);}
                else {Commander_Out.SendExitPrivateRoom(name);}
                Leave();
            }
        });

        String[] tmpadadapter = this.data.split("\\~");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.context,android.R.layout.simple_list_item_activated_1,tmpadadapter);

        lvMessages.setAdapter(adapter);

        return view;
    }


    public void SetMessages(String data, String cmd, Context ctx)
    {

//        String[] str = data.split("\\~");
//        if (str[0].equals("missed"))
//        {
//            String[] tmp = str[1].split("\\.");
//            for (int i = 0; i < tmp.length; i++)
//            {
//                this.data += tmp[i] + "~";
//            }
//
//        }
    }

    public void CreateRoom(String data, String cmd, Context ctx) throws InterruptedException {
        name=cmd;
        tvRoomName.setText(cmd);
        lvMessages.setAdapter(null);
        SetMessages(data, cmd, ctx);
    }

    public void SetMessage(String dat, Context ctx)
    {
        data += dat + "~";
        String[] tmpadadapter = data.split("\\~");

        adapter = new ArrayAdapter<String>(ctx,android.R.layout.simple_list_item_activated_1,tmpadadapter);


        lvMessages.setAdapter(adapter);
    }

    public void Leave()
    {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.main_activity_fragment_placeholder, FragmentManager.lobbyFrag);
        transaction.commit();
        FragmentManager.NewRoom();
        FragmentManager.lobbyFrag.ShowLobby();
    }
}
