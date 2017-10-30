package com.example.ghhos.chat_client.api;

public class Request {

    public String modul;
    public String command;
    public String data;
    public String time;

    public Request(String modul, String command, String data, String data2)
    {
        this.modul = modul;
        this.command = command;
        this.data = data;
        this.time = data2;
    }
}
