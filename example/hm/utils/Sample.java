package com.example.hm.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Sample {
    public static void main(String ar[]) throws ParseException {
        String dateInString="01/28/19";
        SimpleDateFormat formatter=new SimpleDateFormat("MM/dd/yy");
        //SimpleDateFormat formatter=new SimpleDateFormat("MM/dd/yy");
        Date date=formatter.parse(dateInString);
        String convertedDate=formatter.format(date);
        System.out.println(convertedDate);
    }
}
