import java.util.Calendar;
import java.util.Date;

public class Main {

    public static void main(String[] args){

        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.get(Calendar.MONTH);
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        String yearFormatted = year.substring(2,4);
        System.out.println(date.getTime());

    }
}
