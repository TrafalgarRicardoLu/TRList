package utilsTest;

import org.junit.Test;
import utils.conf.ConfigHelper;

import java.io.IOException;

public class utilTest {

    @Test
    public void testProperties() throws IOException {
        System.out.println(ConfigHelper.getCalendarPath());
        System.out.println(ConfigHelper.getXMLPath());
    }
}
