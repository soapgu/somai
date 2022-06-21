package com.example.elcapi;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.elcapi.jnielc;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class AlienJJ156 {
    private static final int seek_red =0xa1;
    private static final int seek_red_left =0xb1;
    private static final int seek_green=0xa2;
    private static final int seek_green_left =0xb2;
    private static final int seek_yellow = 0xa6;
    private static final int seek_yellow_left = 0xb6;
    public static String adbcommand(String command) {
        Log.e("alienJJ",command);
        Process process = null;
        DataOutputStream os = null;
        String excresult = "";
        try {
            process = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(process.getOutputStream());
            os.writeBytes(command + "\n");
            os.writeBytes("exit\n");
            os.flush();

            if (process.waitFor() != 0) {
                System.err.println("exit value = " + process.exitValue());
                //Log.e("alienJJ",process.exitValue());
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    process.getInputStream()));
            StringBuffer stringBuffer = new StringBuffer();
            String line = null;
            while ((line = in.readLine()) != null) {
                stringBuffer.append(line + " ");
            }
            excresult = stringBuffer.toString();
            Log.e("alienJJ",excresult);
            System.out.println(excresult);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return excresult;
    }

    public static void AlienJJ_RedLED(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                    jnielc.seekstart();
                    jnielc.ledseek(seek_red , 70);
                    jnielc.ledseek(seek_red_left , 70);
                    jnielc.seekstop();
//                    adbcommand("echo w 0x03 > ./sys/devices/platform/led_con_h/zigbee_reset");

                    Thread.sleep(500);
                    adbcommand("echo w 0x00f720df > ./sys/devices/platform/led_con_h/zigbee_reset");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void AlienJJ_GreedLED(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Thread.sleep(500);
//                    jnielc.ledseek(seek_green,10);
//                    jnielc.seekstop();
                    jnielc.seekstart();
                    jnielc.ledseek(seek_green , 70);
                    jnielc.ledseek(seek_green_left , 70);
                    jnielc.seekstop();
//                    adbcommand("echo w 0x03 > ./sys/devices/platform/led_con_h/zigbee_reset");

                    Thread.sleep(500);
//                    adbcommand("echo w 0x05 > ./sys/devices/platform/led_con_h/zigbee_reset");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void AlienJJ_YellowLED(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Thread.sleep(500);

                    jnielc.seekstart();
                    jnielc.ledseek(seek_yellow , 70);
                    jnielc.ledseek(seek_yellow_left , 70);
                    jnielc.seekstop();

                    Thread.sleep(500);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void AlienJJ_CloseLED(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Thread.sleep(500);

                    jnielc.seekstart();
                    jnielc.ledoff();
                    jnielc.seekstop();

                    Thread.sleep(500);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void  rebootSystem_AlienJJ(){
        try {
            Runtime.getRuntime().exec("su -c am start -a " + Intent.ACTION_REBOOT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void  hideSystemBar_AlienJJ(Context context){
        Intent in =new Intent();
        in.setAction("com.elclcd.unhidebar");
        context.sendBroadcast(in);

        Intent in2 =new Intent();
        in2.setAction("elc.view.show");
        context.sendBroadcast(in2);

        Intent in3 =new Intent();
        in3.setAction("elc.systembar.hide");
        context.sendBroadcast(in3);
    }

    public static void  showSystemBar_AlienJJ(Context context){
        Intent in =new Intent();
        in.setAction("com.elclcd.hidebar");
        context.sendBroadcast(in);

        Intent in2 =new Intent();
        in2.setAction("elc.view.hide");
        context.sendBroadcast(in2);

        Intent in3 =new Intent();
        in3.setAction("elc.systembar.show");
        context.sendBroadcast(in3);
    }

    public static void screenOff(Context context) {
        Intent in3 =new Intent();
        in3.setAction("android.intent.action.gotosleep");
        context.sendBroadcast(in3);
    }

    public static void screenOn(Context context) {
        Intent in3 =new Intent();
        in3.setAction("android.intent.action.exitsleep");
        context.sendBroadcast(in3);
    }
    public static void screenLightOff(Context context) {
        adbcommand("echo 0 >sys/class/backlight/backlight/brightness");
    }

    public static void screenLightOn(Context context) {
        adbcommand("echo 255 >sys/class/backlight/backlight/brightness");
    }

    public static void reboot() {
        adbcommand("reboot");
    }
//    else if (action.equals("alienjj_red")) {
//
//        jnielc.seekstart();
//        jnielc.ledseek(seek_red , 70);
//        jnielc.ledseek(seek_red_left , 70);
//        jnielc.seekstop();
//
//        callbackContext.success("busy success");
//        return true;
//    }
//  else if (action.equals("alienjj_green")) {
//
//        jnielc.seekstart();
//        jnielc.ledseek(seek_green , 70);
//        jnielc.ledseek(seek_green_left , 70);
//        jnielc.seekstop();
//
//        callbackContext.success("busy success");
//        return true;
//    }else if (action.equals("alienjj_closeLed")) {
//        // sdk 2.0
////   int fb=jnielc.open();
////   jnielc.ledoff(fb);
//
//        // sdk 2.1-
//        jnielc.seekstart();
//        jnielc.ledoff();
//        jnielc.seekstop();
//
//
//        callbackContext.success("busy success");
//        return true;
//    }
}
