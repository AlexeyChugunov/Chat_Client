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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ghhos.chat_client.R;
import com.example.ghhos.chat_client.api.Commander_Out;
import com.example.ghhos.chat_client.api.FragmentManager;
import com.example.ghhos.chat_client.api.Websockets;
import com.google.gson.Gson;

import java.util.Objects;
import java.util.regex.Pattern;


public class FragmentRA extends Fragment {

    final Gson gson = new Gson();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ra, container, false);

        Button bAuth = (Button) view.findViewById(R.id.fragment_ra_bAuth);
        Button bReg = (Button) view.findViewById(R.id.fragment_ra_bReg);
        Button bForgotPass = (Button) view.findViewById(R.id.fragment_ra_bChangePass);

        final EditText etUsername = (EditText) view.findViewById(R.id.fragment_ra_etUsername);
        final EditText etPassword = (EditText) view.findViewById(R.id.fragment_ra_etPassword);

        bAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newUser=etUsername.getText().toString();
                Bundle bundle = new Bundle();
                bundle.putString(Websockets.msg,newUser );
                FragmentRA fragReg1 = new FragmentRA();
                fragReg1.setArguments(bundle);
                FragmentManager.lobbyFrag.username_my=newUser;
                Commander_Out.SendAuth(newUser, etPassword.getText().toString());
                ToLobby();
            }
        });

        bReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenRegistrationDialog();
            }
        });

        bForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogForgotPSW();
            }
        });

        return view;
    }

    public void ToLobby() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.main_activity_fragment_placeholder, FragmentManager.lobbyFrag);
        transaction.commit();
    }

    private void DialogForgotPSW(){
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.dialog_forgot_password, null);

        final EditText newpsw = (EditText) v.findViewById(R.id.dialog_forgotpsw_etUsername);
        final AlertDialog.Builder aDialogBuilder = new AlertDialog.Builder(this.getContext());
        aDialogBuilder.setView(v);

        aDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Commander_Out.SendForgotPsw(newpsw.getText().toString());
                dialog.cancel();
            }
        });
        aDialogBuilder.setCancelable(false);
        aDialogBuilder.create();
        aDialogBuilder.show();
    }

    private void OpenRegistrationDialog() {

        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.fragment_registration, null);

        final EditText username = (EditText) v.findViewById(R.id.dialog_changePass_etUsername);
        final EditText pass = (EditText) v.findViewById(R.id.dialog_changePass_etPassword);
        final EditText eMail = (EditText) v.findViewById(R.id.dialog_changePass_etNewPassword);

        final AlertDialog.Builder aDialogBuilder = new AlertDialog.Builder(this.getContext());
        aDialogBuilder.setView(v);
        aDialogBuilder.setMessage("Registration");

        aDialogBuilder.setPositiveButton("Registration", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               String email = eMail.getText().toString();
                if (Pattern.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$", email)) {
                    Commander_Out.Sendreg(username.getText().toString(), pass.getText().toString(), eMail.getText().toString());
                }
                else {
                    Dialog("Incorrect e-mail address!");
                }
                ToLobby();
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

    //*******диалог для проверок**********
    private void Dialog(String text){
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.dialog, null);

        final TextView dialog = (TextView) v.findViewById(R.id.textviewDialog);
        dialog.setText(text);
        final AlertDialog.Builder aDialogBuilder = new AlertDialog.Builder(this.getContext());
        aDialogBuilder.setView(v);

        aDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        aDialogBuilder.setCancelable(false);
        aDialogBuilder.create();
        aDialogBuilder.show();
    }
}
