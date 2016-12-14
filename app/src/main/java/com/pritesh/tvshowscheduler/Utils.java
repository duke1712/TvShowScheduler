package com.pritesh.tvshowscheduler;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by prittesh on 13/12/16.
 */

public class Utils {
    public static String getDate(String when)
    {
        Calendar today=Calendar.getInstance();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("ddMMyyyy");
        if(when.equalsIgnoreCase("Today"))
         return simpleDateFormat.format(today.getTime());
        else if(when.equalsIgnoreCase("Tomorrow")){
            today.add(Calendar.DATE,1);
            return simpleDateFormat.format(today.getTime());
        }
        else{
            today.add(Calendar.DATE,2);
            return simpleDateFormat.format(today.getTime());
        }
    }

}
