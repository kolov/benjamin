package benjamin.connector.sonar;


import benjamin.connector.sonar.model.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class Projects4MarshalingTest {

    @Test
    public void testUnmarshall() throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        InputStream is = Projects4MarshalingTest.class.getResourceAsStream("/sonar-projects-4.json");
        final Component[] result = mapper.readValue(is, Component[].class);
        org.junit.Assert.assertEquals(2, result.length);
    }
}