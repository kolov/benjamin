package benjamin.connector.sonar;


import benjamin.connector.sonar.model.PagedComponents;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class PagedComponentsMarshalingTest {

    @Test
    public void testUnmarshall() throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        InputStream is = PagedComponentsMarshalingTest.class.getResourceAsStream("/paged_components.json");
        final PagedComponents result = mapper.readValue(is, PagedComponents.class);
        org.junit.Assert.assertEquals(1, result.getPage());
    }
}