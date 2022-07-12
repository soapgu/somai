package com.example.elcapi;

import android.content.Context;
import android.content.Intent;

import com.shangma.mcu.GpioController;
import com.shangma.mcu.McuDeviceManager;


/**
 * Intent intent = new Intent();
 * intent.setAction("android.vendorinterface.action.key");
 * intent.putExtra("keycode", 2110);
 * 	intent.putExtra("enable", 1);  //显示导航栏/状态栏
 * 或
 * 	intent.putExtra("enable", 0);  //隐藏导航栏/状态栏
 * this.sendBroadcast(intent);
 *
 *
 *
 * Intent intent = new Intent();
 * intent.setAction("android.vendorinterface.action.key");
 * intent.putExtra("keycode", 2111);
 * 	intent.putExtra("enable", 1);  //允许下滑显示导航栏/状态栏
 * 或
 * 	intent.putExtra("enable", 0);  //禁止下滑显示导航栏/状态栏
 * this.sendBroadcast(intent);
 */
public class Alien22Util {
    /**
     * 显示状态栏
     * @param context
     */
    public static void showSystemBar(Context context) {
        Intent intent = new Intent();
        intent.setAction("android.vendorinterface.action.key");
        intent.putExtra("keycode", 2110);
        intent.putExtra("enable", 1);
        context.sendBroadcast(intent);
    }

    /**
     * 隐藏状态栏
     * @param context
     */
    public static void hideSystemBar(Context context) {
        Intent intent = new Intent();
        intent.setAction("android.vendorinterface.action.key");
        intent.putExtra("keycode", 2110);
        intent.putExtra("enable", 0);
        context.sendBroadcast(intent);
    }

    /**
     * 重启
     * @return
     */
    public static int reboot() {
        McuDeviceManager.the().retsetDevice();
        return 1;
    }

    /**
     * 喂狗
     * countdown  喂狗频率，毫秒单位
     * @return
     */
    public static int feedWatchDog() {
//        McuDeviceManager.the().feedWatchDog(60000);
        return 1;
    }

    /**
     * 打开继电器
     */
    public static void openRelay(){
        GpioController.the().openRelay();
    }


    /**
     * 关闭继电器
     */
    public static void closeRelay() {
        GpioController.the().closeRelay();
    }

    /**
     * 红灯亮
     */
    public static void onRedLed() {
        GpioController.the().turnOnRedLed();
    }

    /**
     * 关红灯
     */
    public static void offRedLed() {
        GpioController.the().turnOffRedLed();
    }

    /**
     * 绿灯亮
     */
    public static void onGreenLed() {
        GpioController.the().turnOnGreenLed();
    }

    /**
     * 关绿灯
     */
    public static void offGreenLed() {
        GpioController.the().turnOffGreenLed();
    }


    /**
     * 关闭屏幕，物理关闭
     * @param context
     */
    public static void closeScreen(Context context) {
        Intent intent = new Intent();
        intent.setAction("android.vendorinterface.action.key");
        intent.putExtra("keycode", 2103);
        intent.putExtra("enable", 0);
        context.sendBroadcast(intent);

    }

    /**
     * 打开屏幕
     * @param context
     */
    public static void openScreen(Context context) {
        Intent intent = new Intent();
        intent.setAction("android.vendorinterface.action.key");
        intent.putExtra("keycode", 2103);
        intent.putExtra("enable", 1);
        context.sendBroadcast(intent);
    }

    /**
     * 屏幕背光关
     * @param context
     */
    public static void screenLightOff(Context context) {
        Intent intent = new Intent();
        intent.setAction("android.vendorinterface.action.key");
        intent.putExtra("keycode", 2102);
        intent.putExtra("enable", 0);
        context.sendBroadcast(intent);
    }

    /**
     * 屏幕背光开
     * @param context
     */

    public static void screenLightOn(Context context) {
        Intent intent = new Intent();
        intent.setAction("android.vendorinterface.action.key");
        intent.putExtra("keycode", 2102);
        intent.putExtra("enable", 1);
        context.sendBroadcast(intent);
    }


    /**
     * 摄像头相关方法，暂时没用
     * @param context
     */
    public static void openUsbServer(Context context) {
        Intent intent = new Intent();
        intent.setAction("android.vendorinterface.action.key");
        intent.putExtra("keycode", 2121);
        intent.putExtra("enable", 1);
        context.sendBroadcast(intent);
    }

    public static void closeUsbServer(Context context) {
        Intent intent = new Intent();
        intent.setAction("android.vendorinterface.action.key");
        intent.putExtra("keycode", 2121);
        intent.putExtra("enable", 0);
        context.sendBroadcast(intent);
    }
}
