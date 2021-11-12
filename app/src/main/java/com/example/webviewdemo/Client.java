package com.example.webviewdemo;



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;


public class Client implements Runnable {
    private static  boolean connectflag=false;
    private static int ch0=0;
    private static int ch1=1500;
    private static int ch2=1500;
    private static int ch3=1500;
    private static int ch4=1500;
    private static int ch5=1500;
    private static int ch6=1500;
    private static int ch7=1500;

    private static int sent[];
    private static String receive="inital";

    public static void setConnectflag(boolean connectflag) {
        Client.connectflag = connectflag;
    }

    public static void setCh0(int ch0) {
        Client.ch0 = ch0;
    }
    public static void setCh1(int ch1) {
        Client.ch1 = ch1;
    }

    public static void setCh2(int ch2) {
        Client.ch2 = ch2;
    }

    public static void setCh3(int ch3) {
        Client.ch3 = ch3;
    }

    public static void setCh4(int ch4) { Client.ch4 = ch4; }

    public static void setCh5(int ch5) { Client.ch5 = ch5; }

    public static void setCh6(int ch6) { Client.ch6 = ch6; }

    public static void setCh7(int ch7) { Client.ch7 = ch7; }

    public static String getReceive() {
        return receive;
    }






            public void run() {
                try {
                    sent=new int[8];
                    Socket socket = new Socket("106.14.149.41",8888 );
                    while (true){
                        Thread.sleep(10);
                        sent[0]=ch0;//报头
                        sent[1]=ch1;
                        sent[2]=ch2;
                        sent[3]=ch3;
                        sent[4]=ch4;
                        sent[5]=ch5;
                        sent[6]=ch6;
                        sent[7]=ch7;


                        //发送
                        PrintWriter out = new PrintWriter(socket.getOutputStream());
                        out.println(sent[0]+" "+sent[1]+" "+sent[2]+" "+sent[3]+" "+sent[4]+" "+sent[5]+" "+sent[6]+" "+sent[7]);
                        out.flush();

                        // 接收来自服务器的消息
                        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        receive = br.readLine();

                    }


                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();

                }


            }


    }







