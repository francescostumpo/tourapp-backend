package org.nish.kairos.tourapp.utils;


import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.stream.Collectors;

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

    public static String addOneDay(String date) throws ParseException {
        Calendar c = Calendar.getInstance();
        Date dateConverted = formatStringDateToDate(date);
        c.setTime(dateConverted);
        c.add(Calendar.DATE, 1);
        return getDateFormatted(c.getTime());
    }

    public static String generateRandomPassword(int len, int randNumOrigin, int randNumBound)
    {
        SecureRandom random = new SecureRandom();
        return random.ints(len, randNumOrigin, randNumBound + 1)
                .mapToObj(i -> String.valueOf((char)i))
                .collect(Collectors.joining());
    }
}
