package ru.spb;

import ru.spb.epa.MainThread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * User: Павел
 * Date: 08.12.2007
 * Time: 21:17:57
 *
 * staring the program
 */
public class Starter {

    public static void main(String[] args) {
        System.out.println(" ===== Server start ===== ");

        MainThread m = null;
        try {
            m = new MainThread();
        } catch (IOException e) { e.printStackTrace(); }

        try {
            BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
            boolean contunie = true;

            while(contunie){
                String command = r.readLine();
                if(command == null) contunie = false;
                else{
                    if("exit".equals(command))contunie = false;
                    if("show".equals(command)){
                        System.out.println(m);
                    }
                }
            }


        } catch (IOException e) { e.printStackTrace(); }

        try {
            if(m!=null){
                m.interrupt();
                m.join();
            }
        } catch (Exception e) { e.printStackTrace(); }

        System.out.println(" ===== Server end ===== ");
    }
}
