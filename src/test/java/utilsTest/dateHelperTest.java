package utilsTest;

import org.junit.Test;
import utils.DateHelper;

import java.text.ParseException;

public class dateHelperTest {

    @Test
    public void testDateFomat() throws ParseException {
        DateHelper.getDate("20080401T130000Z");
    }
}
