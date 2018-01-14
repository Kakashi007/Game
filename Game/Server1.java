/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Server implements Runnable{

    static ServerSocket server=null;
   // static BufferedReader in=null;
   // static PrintWriter out=null;
    static Socket client1=null,client2=null;
    static public Thread t;

    Server() {
        server=null;
        client1=client2=null;
    }

    Server(ServerSocket x,Socket y,Socket z) {
        server=x;
        client1=y;
        client2=z;
        t=new Thread(this);
        t.start();
    }

    public static void main(String args[]) {
        try {
            server=new ServerSocket(8080);
            System.out.println("Server Created.");
            do {
                client1=server.accept();
                System.out.println("Waiting for another Client.");
                client2=server.accept();
                new Server(server,client1,client2);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
            }while(true);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void run() {
        try {
            BufferedReader in1,in2;
            PrintWriter out1,out2;
            String msg1,msg2;
            in1=new BufferedReader(new InputStreamReader(client1.getInputStream()));
            in2=new BufferedReader(new InputStreamReader(client2.getInputStream()));
            out1=new PrintWriter(client1.getOutputStream(),true);
            out2=new PrintWriter(client2.getOutputStream(),true);
            System.out.println(in1);
            Conversation c1=new Conversation(in1,out2);
            Conversation c2=new Conversation(in2,out1);
            try {
                c1.t.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                c2.t.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

class Conversation implements Runnable {
    Thread t;
    BufferedReader in;
    PrintWriter out;
    Conversation(BufferedReader x,PrintWriter y) {
        in=x;
        out=y;
        t=new Thread(this);
        t.start();
    }

    public void run() {
        String msg=null;
        while(true) {
            try {
                msg=in.readLine();
                System.out.println(msg);
                out.println(msg);
            } catch (IOException ex) {
                Logger.getLogger(Conversation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
