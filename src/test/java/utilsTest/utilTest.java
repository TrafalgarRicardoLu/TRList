package utilsTest;

import org.junit.Test;
import utils.propertyGenerator;

import java.io.IOException;
import java.util.Properties;

public class utilTest {

    @Test
    public void testProperties() throws IOException {
        System.out.println(propertyGenerator.getProperties("filePath"));
    }
}
