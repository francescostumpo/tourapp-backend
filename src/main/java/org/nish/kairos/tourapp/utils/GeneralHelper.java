package org.nish.kairos.tourapp.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class GeneralHelper {

    public static String getDateFormatted(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String formattedDate = simpleDateFormat.format(date);
        return formattedDate;
    }

    public static String convertStringDateBrowserToStringDateServer(String date){
        String[] firstSplitDate = date.split("-");
        String[] secondSplitDate = firstSplitDate[2].split("T");
        return secondSplitDate[0] + "-" + firstSplitDate[1] + "-" +firstSplitDate[0] + " " +secondSplitDate[1];
    }

    public static Date formatStringDateToDate(String date) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Date formattedDate = simpleDateFormat.parse(date);
        return formattedDate;
    }

    public static String generateUniqueIdForTicket(Date date){
        return date.getTime() + "";
    }
}
