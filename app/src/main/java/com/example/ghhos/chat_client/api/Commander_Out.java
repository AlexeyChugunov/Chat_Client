package com.example.ghhos.chat_client.api;

import com.google.gson.Gson;

import org.java_websocket.client.WebSocketClient;

public class Commander_Out {

    //********Авторизаця************
    public static void SendAuth(String name, String psw){
        final Gson gson = new Gson();
        Request modelObject = new Request(name , "auth" ,psw,"" );
        String json = gson.toJson(modelObject);
        WebSocketClient wr=new Websockets().wsa;
        wr.send(json);
    }

   // ********регистрация**********
    public static  void Sendreg(String name, String psw, String email){
        final Gson gson = new Gson();
        Request modelObject = new Request(name, "reg",psw , email);
        String json = gson.toJson(modelObject);
        Websockets.wsa.send(json);
    }
    //***********восстановление пароля**************      уточнить команду!!!!!!!!!
    public static void SendForgotPsw(String email){//
        final Gson gson = new Gson();
        Request modelObject = new Request("","","","");
        String json = gson.toJson(modelObject);
        Websockets.wsa.send(json);
    }
    //********** Смена пароля**********
    public  static  void SendChangePsw(String oldPSW, String  newPSW){
        final Gson gson = new Gson();
        Request modelObject = new Request("auth","changePass",oldPSW,newPSW);
        String json = gson.toJson(modelObject);
        Websockets.wsa.send(json);
    }
    //********Отправка сообщения из комнаты***************
    public static void SendMsg(String msg, String name){
        final Gson gson = new Gson();
        Request modelObject = new Request("rooms","message",msg,name);//name - room name
        String json = gson.toJson(modelObject);
        Websockets.wsa.send(json);
    }

    public  static void SendCloseRoom(String name){
        final Gson gson = new Gson();
        Request modelObject = new Request("rooms","leave",name,"");//name - room name
        String json = gson.toJson(modelObject);
        Websockets.wsa.send(json);
    }

    public static void SendExitRoom(String name){
        final Gson gson = new Gson();
        Request modelObject = new Request("rooms","exit",name,"");//name - room name
        String json = gson.toJson(modelObject);
        Websockets.wsa.send(json);
    }
    //*********** Создание комнаты*********
    public  static  void SendCreateRoom(String name){
        final Gson gson = new Gson();
        Request modelObject = new Request("rooms","createroom",name,"");
        String json = gson.toJson(modelObject);
        Websockets.wsa.send(json);
    }
    //******* Выход из системы*******************
    public static void SendMsgClose(String msg, String name){
        final Gson gson = new Gson();
        Request modelObject = new Request("","","","");
        String json = gson.toJson(modelObject);
        Websockets.wsa.send(json);
    }
    //*********LOGOUT*****************
    public  static void SendLogout(){
        final Gson gson = new Gson();
        Request modelObject = new Request("auth","exit","","");
        String json = gson.toJson(modelObject);
        Websockets.wsa.send(json);
    }

    //**********Обновление списка комнат**********
    public  static void SendRefreshRoom(){
        final Gson gson = new Gson();
        Request modelObject = new Request("lobby","refresh","","");
        String json = gson.toJson(modelObject);
        Websockets.wsa.send(json);
    }
    //*********Обновление списка клиентов*********
    public  static  void SendRefreshClient(){
        final Gson gson = new Gson();
        Request modelObject = new Request("lobby","refreshclients","","");
        String json = gson.toJson(modelObject);
        Websockets.wsa.send(json);
    }
    //**********Вход в комнату**********
    public static void SendEnterRoom(String romname){
        final Gson gson = new Gson();
        Request modelObject = new Request("rooms","enter",romname,"");
        String json = gson.toJson(modelObject);
        Websockets.wsa.send(json);
    }
    //**********Создание приватной комнаты************
    public static void SendPrivateRoom(String username){
        final Gson gson = new Gson();
        Request modelObject = new Request("rooms","privateroom",username,"");
        String json = gson.toJson(modelObject);
        Websockets.wsa.send(json);
    }
    //********Отправка сообщения из приватной комнаты***************
    public  static void SendClosePrivateRoom(String name){
        final Gson gson = new Gson();
        Request modelObject = new Request("rooms","leave",name,"");//name - room name
        String json = gson.toJson(modelObject);
        Websockets.wsa.send(json);
    }

    public static void SendExitPrivateRoom(String name){
        final Gson gson = new Gson();
        Request modelObject = new Request("rooms","exit",name,"");//name - room name
        String json = gson.toJson(modelObject);
        Websockets.wsa.send(json);
    }
    //********Отправка сообщения из приватной комнаты***************
    public static void SendPrivateMsg(String msg, String name){
        final Gson gson = new Gson();
        Request modelObject = new Request("rooms","privateroom",msg,"");//name - room name
        String json = gson.toJson(modelObject);
        Websockets.wsa.send(json);
    }

    //************************ Bun****************************
    public static void SendBun(String username, String time){
        final Gson gson = new Gson();
        Request modelObject = new Request("lobby","ban",username,time);//name - room name
        String json = gson.toJson(modelObject);
        Websockets.wsa.send(json);
    }

    //***********************UnBun*******************************
    public static void SendUnBun(String username){
        final Gson gson = new Gson();
        Request modelObject = new Request("lobby","unban",username,"");//name - room name
        String json = gson.toJson(modelObject);
        Websockets.wsa.send(json);
    }
}
