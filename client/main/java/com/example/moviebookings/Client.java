package com.example.moviebookings;

import java.io.*;
import java.net.*;

public class Client {
    // initialize socket and input output streams
    private static Socket socket = null;
    private static PrintWriter out = null;

    private static BufferedReader in =null;

    // constructor to put ip address and port
    public Client(String address, int port)
    {
        // establish a connection
        try {
            socket = new Socket(address, port);
            System.out.println("Connected");
        } catch (IOException u) {
            System.out.println(u);
        }
    }
    public  boolean sendData(String userInput){
        try{
            out = new PrintWriter(
                    socket.getOutputStream(),true);
            out.println(userInput);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String response=in.readLine();
            socket.close();
            if (response.equals("1"))
                return true;
            } catch (IOException e) {}
        return false;
    }
    public String getData(String userInput){
        String response=null;
        try{
            out = new PrintWriter(
                    socket.getOutputStream(),true);
            out.println(userInput);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            response=in.readLine();
            socket.close();
        } catch (IOException e) {}
        return response;
    }

}
