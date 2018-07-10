package utilsTest;

import org.junit.Test;
import utils.DateHelper;

import java.text.ParseException;

public class dateHelperTest {

    @Test
    public void testDateFormat() throws ParseException {
        DateHelper.getDate("20080401T130000Z");
    }

    @Test
    public void testCalDateFormat(){
        String date = "2018-Ap-23-16:30";
        DateHelper.getCalDate(date);
    }
}
