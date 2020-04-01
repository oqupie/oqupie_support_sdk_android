package com.oqupie.oqupiesupport;

import android.util.Log;

/**
 * 로거 클래스
 */
public class Logger {
    public final static int VERBOSE = 0;
    public final static int INFO = 1;
    public final static int DEBUG = 2;
    public final static int WARN = 3;
    public final static int ERROR = 4;
    public final static int OFF = 10;

    private static int level = ERROR;

    private static String tag = "Oqupie";

    /**
     * 로그 레벨을 설정한다
     *
     * @param level 로그레벨
     */
    public static void setLogLevel(int level) {
        Logger.level = level;
    }

    /**
     * Tag 를 설정한다.
     *
     * @param tag 태그
     */
    public static void setTag(String tag) {
        Logger.tag = tag;
    }

    public static void verbose(String msg) {
        if (Logger.level > VERBOSE)
            return;

        Log.v(Logger.tag, msg);
    }

    public static void debug(String msg) {
        if (Logger.level > DEBUG)
            return;

        Log.d(Logger.tag, msg);
    }

    public static void info(String msg) {
        if (Logger.level > INFO)
            return;

        Log.i(Logger.tag, msg);
    }

    public static void warn(String msg) {
        if (Logger.level > WARN)
            return;

        Log.w(Logger.tag, msg);
    }

    public static void error(String msg) {
        if (Logger.level > ERROR)
            return;

        Log.e(Logger.tag, msg);
    }
}
