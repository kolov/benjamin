package benjamin.connector.sonar;


import benjamin.connector.sonar.model.PagedMetrics5;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class PagedMetrics5MarshalingTest {

    @Test
    public void testUnmarshall() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        InputStream is = PagedMetrics5MarshalingTest.class.getResourceAsStream("/paged_metrics-5.json");
        PagedMetrics5 result = mapper.readValue(is, PagedMetrics5.class);
        org.junit.Assert.assertEquals(1, result.getPage());
        org.junit.Assert.assertEquals(124, result.getTotal());
    }
}