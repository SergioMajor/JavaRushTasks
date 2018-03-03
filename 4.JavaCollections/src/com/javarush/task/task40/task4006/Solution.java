package com.javarush.task.task40.task4006;

import java.io.*;
import java.net.Socket;
import java.net.URL;

public class Solution {
    public static void main(String[] args) throws Exception {
        getSite(new URL("http://javarush.ru/social.html"));
    }

    public static void getSite(URL url) {
        try {
            Socket socket = new Socket(url.getHost(), 80);
            OutputStream theOutput = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(theOutput, false);
            pw.print("GET " + url.getFile() + " HTTP/1.0\r\n");
            pw.print("Accept: text/plain, text/html, text/*\r\n");
            pw.print("\r\n");
            pw.flush();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String responseLine;

            while ((responseLine = bufferedReader.readLine()) != null) {
                System.out.println(responseLine);
            }
            bufferedReader.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}