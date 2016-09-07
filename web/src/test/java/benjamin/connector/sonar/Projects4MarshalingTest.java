package benjamin.connector.sonar;


import benjamin.connector.sonar.model.Project;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class Projects4MarshalingTest {

    @Test
    public void testUnmarshall() throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        InputStream is = Projects4MarshalingTest.class.getResourceAsStream("/sonar-projects-4.json");
        final Project[] result = mapper.readValue(is, Project[].class);
        assertEquals(2, result.length);
        assertThat(result[0].getId(), equalTo("68870"));
        assertThat(result[0].getKey(), equalTo("com.myorg.zyz.genericcomponents:AFilter"));
        assertThat(result[0].getName(), equalTo("SomeName"));
        assertThat(result[0].getSc(), equalTo("PRJ"));
        assertThat(result[0].getQualifier(), equalTo("TRK"));

    }
}