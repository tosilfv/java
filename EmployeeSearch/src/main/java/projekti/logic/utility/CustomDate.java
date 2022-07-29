package projekti.logic.utility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.springframework.stereotype.Service;

@Service
public class CustomDate {

    // Returns current date without time
    public String date() {
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL);
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }

    // Returns current date with time
    public String dateTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy : HH.mm.ss z");
        Date date = new Date(System.currentTimeMillis());
        System.out.println();
        return formatter.format(date);
    }
}
