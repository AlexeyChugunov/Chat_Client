package com.example.ghhos.chat_client.ui.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.ghhos.chat_client.api.Commander_Out;
import com.example.ghhos.chat_client.api.FragmentManager;
import com.example.ghhos.chat_client.ui.activity.MainActivity;
import com.example.ghhos.chat_client.R;



public class FragmentLobby extends Fragment {

    Button bLogout          = null;
    Button bLogin=null;
    Button bChangePSW       = null;
    Button bCreateRoom      = null;
    Button bRefreshRoom     = null;
    Button bRefreshClient   = null;
    Button bEnter=null;
    Button bPrivate = null;
    Button bBan;
    Button bUnban;

    TextView user=null;
    ListView lRoomList      = null;
    ListView lClientList    = null;
    EditText input;
    EditText inputban;
    String roomname;
    String username;
    int lastposition;
    int lastpositionuser;
    String name = "";
    AlertDialog.Builder ad;
    AlertDialog.Builder ban;
    AlertDialog.Builder unban;
    public String username_my="";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lobby, container, false);
        input = new EditText(MainActivity.context);
        inputban = new EditText(MainActivity.context);

        bLogout = (Button) view.findViewById(R.id.fr_lobby_bLogout);
        bLogin = (Button) view.findViewById(R.id.fragment_lobby_bLogin);
        bChangePSW = (Button) view.findViewById(R.id.fr_lobby_bChange);
        bCreateRoom = (Button) view.findViewById(R.id.fr_lobby_bCreate);
        bRefreshRoom=(Button) view.findViewById(R.id.fr_lobby_bRefreshrooms);
        bRefreshClient=(Button) view.findViewById(R.id.fr_lobby_bRefreshclients);
        bEnter=(Button) view.findViewById(R.id.fr_lobby_bEnter);
        bPrivate=(Button) view.findViewById(R.id.fr_lobby_bPrivate);
        bBan=(Button) view.findViewById(R.id.fr_lobby_bBan);
        bUnban = (Button) view.findViewById(R.id.fr_lobby_bUnban);
        user = (TextView) view.findViewById(R.id.fr_lobby_name);

        lRoomList = (ListView) view.findViewById(R.id.fr_lobby_lvRoomsList);
        lClientList = (ListView) view.findViewById(R.id.fr_lobby_lvUsersList);

        SetCreateDlg();
        SetBanDlg();
        SetUnbanDlg();
        final AlertDialog alertd = ad.create();
        final AlertDialog alertdban = ban.create();
        final AlertDialog alertdunban = unban.create();


        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.main_activity_fragment_placeholder, MainActivity.fragments.get(0));//переход на форму регистрации
                transaction.commit();
            }
        });

        bLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Commander_Out.SendLogout();//дописать команды
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.main_activity_fragment_placeholder, MainActivity.fragments.get(0));//переход на форму регистрации
                transaction.commit();
            }
        });

        bChangePSW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangePSW();
            }
        });

        bCreateRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertd.show();
            }
        });

        bRefreshRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Commander_Out.SendRefreshRoom();
            }
        });
        bRefreshClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Commander_Out.SendRefreshClient();
            }
        });
        bEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lastposition == -1)
                {
                    Toast.makeText(getContext(), "Please select room",
                            Toast.LENGTH_LONG).show();
                }
                else {
                    Commander_Out.SendEnterRoom(roomname);
                    ToRoom();
                }
            }
        });
        bPrivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lastposition == -1)
                {
                    Toast.makeText(getContext(), "Please select room",
                            Toast.LENGTH_LONG).show();
                }
                else {
                    Commander_Out.SendPrivateRoom(username);
                    FragmentManager.frRoom.PrivateRoom=true;
                    ToRoom();
                }
            }
        });

        lClientList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {

                username = lClientList.getItemAtPosition(position).toString();
                lClientList.setItemChecked(position,true);
                if(lastpositionuser != -1 && lastpositionuser != position) {
                    lClientList.setItemChecked(lastpositionuser, false);
                }
                lastpositionuser = position;
            }
        });

        lRoomList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {

                String[] splitrooms = lRoomList.getItemAtPosition(position).toString().split(" ");;
                roomname = splitrooms[0];
                lRoomList.setItemChecked(position,true);
                if(lastposition != -1 && lastposition != position) {
                    lRoomList.setItemChecked(lastposition, false);
                }
                lastposition = position;
            }
        });

        bBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lastpositionuser == -1)
                {
                    Toast.makeText(getContext(), "Please select user",
                            Toast.LENGTH_LONG).show();
                }
                else {
                    alertdban.show();
                }
            }
        });

        bUnban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lastpositionuser == -1)
                {
                    Toast.makeText(getContext(), "Please select user",
                            Toast.LENGTH_LONG).show();
                }
                else {
                    alertdunban.show();
                }
            }
        });

        return view;
    }

    public  void ShowLobby(){

        bLogout.setVisibility(View.VISIBLE);
        bChangePSW.setVisibility(View.VISIBLE);
        bCreateRoom.setVisibility(View.VISIBLE);
        bRefreshRoom.setVisibility(View.VISIBLE);
        bRefreshClient.setVisibility(View.VISIBLE);
        bEnter.setVisibility(View.VISIBLE);
        bPrivate.setVisibility(View.VISIBLE);
        lRoomList.setVisibility(View.VISIBLE);
        lClientList.setVisibility(View.VISIBLE);
        user.setVisibility(View.VISIBLE);
        user.setText(username_my);

        bLogin.setVisibility(View.INVISIBLE);
    }

    public  void ShowLobbyAdmin(){
        bLogout.setVisibility(View.VISIBLE);
        bChangePSW.setVisibility(View.VISIBLE);
        bCreateRoom.setVisibility(View.VISIBLE);
        bRefreshRoom.setVisibility(View.VISIBLE);
        bRefreshClient.setVisibility(View.VISIBLE);
        bBan.setVisibility(View.VISIBLE);
        bUnban.setVisibility(View.VISIBLE);
        bEnter.setVisibility(View.VISIBLE);
        bPrivate.setVisibility(View.VISIBLE);
        lRoomList.setVisibility(View.VISIBLE);
        lClientList.setVisibility(View.VISIBLE);
        user.setVisibility(View.VISIBLE);
        user.setText(username_my);

        bLogin.setVisibility(View.INVISIBLE);
    }

    private void ChangePSW(){
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.change_psw, null);

        final EditText oldPSW = (EditText) v.findViewById(R.id.old_PSW);
        final EditText newPSW = (EditText) v.findViewById(R.id.new_PSW);
        final AlertDialog.Builder aDialogBuilder = new AlertDialog.Builder(this.getContext());
        aDialogBuilder.setView(v);
        aDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Commander_Out.SendChangePsw(oldPSW.getText().toString(),newPSW.getText().toString());
                dialog.cancel();
            }
        });
        aDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        aDialogBuilder.setCancelable(false);
        aDialogBuilder.create();
        aDialogBuilder.show();
    }

    private void ToRoom(){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.main_activity_fragment_placeholder,FragmentManager.frRoom);
        transaction.commit();

    }
    private void SetCreateDlg()//create room
    {
        String title = "Create room";
        String button1String = "Create";
        String button2String = "Cancel";

        ad = new AlertDialog.Builder(getContext());
        ad.setTitle(title);  // заголовок
        ad.setPositiveButton(button1String, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                Commander_Out.SendCreateRoom(input.getText().toString());
                Toast.makeText(getContext(), "Room created",
                        Toast.LENGTH_LONG).show();
                input.setText("");
            }
        });
        ad.setNegativeButton(button2String, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                Toast.makeText(getContext(), "Cancel", Toast.LENGTH_LONG)
                        .show();
            }
        });
        ad.setCancelable(true);
        ad.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
                input.setText("");
            }
        });
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        ad.setView(input);
    }

    private void SetBanDlg()
    {
        String title = "Do you want to ban " + username + " ?";
        String button1String = "Permanent";
        String button2String = "For time";
        ban = new AlertDialog.Builder(getContext());
        ban.setTitle(title);  // заголовок
        ban.setPositiveButton(button1String, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {

                Commander_Out.SendBun(username,"permanent");
                Toast.makeText(getContext(), "User has been banned",
                        Toast.LENGTH_LONG).show();
                inputban.setText("");

            }
        });
        ban.setNegativeButton(button2String, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                if(CheckBan(inputban.getText().toString())) {
                    Commander_Out.SendBun(username,inputban.getText().toString());
                    Toast.makeText(getContext(), "User has been banned", Toast.LENGTH_LONG)
                            .show();
                }
                inputban.setText("");
            }
        });
        ban.setCancelable(true);
        ban.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
                inputban.setText("");
            }
        });

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        inputban.setLayoutParams(lp);
        ban.setView(inputban);
    }

    private boolean CheckBan(String bantime)
    {
        if(bantime.length() < 60)
        {
            Toast.makeText(getContext(), "Minimum ban time 60",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        else if(bantime.length() > 9999)
        {
            Toast.makeText(getContext(), "Maximum ban time 9999",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        else if(!(bantime.matches("^[0-9]+$")))
        {
            Toast.makeText(getContext(), "Please enter only numbers",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void SetUnbanDlg()
    {
        String title = "Do you want to unban " + username + " ?";
        String button1String = "Unban";
        String button2String = "Cancel";

        unban = new AlertDialog.Builder(getContext());

        unban.setTitle(title);  // заголовок
        unban.setPositiveButton(button1String, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                Commander_Out.SendUnBun(username);
                Toast.makeText(getContext(), "User has been unbanned",
                        Toast.LENGTH_LONG).show();

            }
        });
        unban.setNegativeButton(button2String, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                Toast.makeText(getContext(), "Cancel", Toast.LENGTH_LONG)
                        .show();
            }
        });
        unban.setCancelable(true);
        unban.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {

            }
        });

    }

    public void Passive(String cmd, String dat, String tm, Context ctx)    //отображение пропущенных сообщений
    {

        ArrayAdapter<String> adapter;
        int a = -1;
        String[] data = new String[lRoomList.getCount()];
        for (int i = 0; i < lRoomList.getCount(); i++)
        {
            String[] str1 =  lRoomList.getItemAtPosition(i).toString().split(" ");
            data[i] = lRoomList.getItemAtPosition(i).toString();
            if (str1[0].equals(cmd))
                a = i;
        }
        if (a != -1)
        {
            if (dat.equals("0") && !(tm.equals("True"))) {
                data[a] = cmd + " ";

                adapter = new ArrayAdapter<String>(ctx,android.R.layout.simple_list_item_activated_1,data);

                lRoomList.setAdapter(adapter);
            }
            else if (!(dat.equals("0"))) {
                data[a] = cmd + "   +" + dat;
                adapter = new ArrayAdapter<String>(ctx,android.R.layout.simple_list_item_activated_1,data);
                lRoomList.setAdapter(adapter);
            }
        }
        else
        if (!(dat.equals("0"))) {
            String[] newdata = new String[data.length + 1];
            for (int i = 0; i < data.length; i++)
            {
                newdata[i] = data[i];
            }
            newdata[data.length] = cmd + "   +" + dat;
            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(ctx,android.R.layout.simple_list_item_activated_2,newdata);

            lRoomList.setAdapter(adapter1);
        }
    }

    public void SetRooms(String[] data, Context ctx)
    {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ctx,android.R.layout.simple_list_item_activated_1,data);
        lRoomList.setAdapter(adapter);
    }
    public void SetClients(String[] data, Context ctx)
    {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ctx,android.R.layout.simple_list_item_activated_1,data);
        lClientList.setAdapter(adapter);
    }

}



