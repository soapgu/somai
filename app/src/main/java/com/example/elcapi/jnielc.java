package com.example.elcapi;

public class jnielc {
    /**
     * Created by xiao_ on 2018/5/15.
     */
        public final static native int ledoff();
        public final static native int seekstart();
        public final static native int seekstop();
        public final static native int ledseek(int flag, int progress);

        static {
            System.loadLibrary("jnielc");
        }
}
