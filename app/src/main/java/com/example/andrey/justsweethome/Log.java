package com.example.andrey.justsweethome;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by User on 29.11.2017.
 */

public class Log {

    public static void print(String str){
        Date curTime = new Date();
        SimpleDateFormat parsedDate = new SimpleDateFormat("HH:mm:ss SSSS");
        System.out.println(" -> " + parsedDate.format(curTime) + " : " + str);
    }
}
