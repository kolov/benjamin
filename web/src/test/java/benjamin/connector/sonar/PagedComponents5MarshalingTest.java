package benjamin.connector.sonar;


import benjamin.connector.sonar.model.PagedComponents5;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class PagedComponents5MarshalingTest {

    @Test
    public void testUnmarshall() throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        InputStream is = PagedComponents5MarshalingTest.class.getResourceAsStream("/paged_components-5.json");
        final PagedComponents5 result = mapper.readValue(is, PagedComponents5.class);
        org.junit.Assert.assertEquals(1, result.getPage());
    }
}