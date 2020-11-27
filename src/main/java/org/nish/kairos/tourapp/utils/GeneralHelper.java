package org.nish.kairos.tourapp.utils;


import java.text.SimpleDateFormat;
import java.util.Date;

public class GeneralHelper {

    public static String getDateFormatted(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String formattedDate = simpleDateFormat.format(date);
        return formattedDate;
    }

    public static String generateUniqueIdForTicket(Date date){
        return date.getTime() + "";
    }
}
