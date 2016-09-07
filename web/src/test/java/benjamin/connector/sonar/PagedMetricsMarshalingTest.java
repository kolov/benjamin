package benjamin.connector.sonar;


import benjamin.connector.sonar.model.PagedMetrics;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class PagedMetricsMarshalingTest {

    @Test
    public void testUnmarshall() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        InputStream is = PagedMetricsMarshalingTest.class.getResourceAsStream("/paged_metrics.json");
        PagedMetrics result = mapper.readValue(is, PagedMetrics.class);
        org.junit.Assert.assertEquals(1, result.getPage());
        org.junit.Assert.assertEquals(124, result.getTotal());
    }
}