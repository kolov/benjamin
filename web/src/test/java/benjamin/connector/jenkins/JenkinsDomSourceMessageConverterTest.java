package benjamin.connector.jenkins;


import benjamin.ApplicationConfiguration;
import benjamin.connector.common.RestHelper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.RestTemplate;

import javax.xml.transform.dom.DOMSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;

@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(locations = "/converters-tests.properties")
@ContextConfiguration(classes = {ApplicationConfiguration.class, JenkinsConnectorFactory.class, RestHelper.class})
public class JenkinsDomSourceMessageConverterTest {


    @Autowired
    private RestHelper restHelper;

    @Test
    public void testRestTemplate() throws Exception {

        RestTemplate restTemplate = restHelper.getRestTemplate();
        MockRestServiceServer server = MockRestServiceServer.createServer(restTemplate);

        server.expect(requestTo("/")).andExpect(method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withSuccess(readContent("/jenkins-commit-job-config.xml"),
                        MediaType.APPLICATION_XML));

        DOMSource result = restTemplate.getForObject("/", DOMSource.class);
        XPath xpath = XPathFactory.newInstance().newXPath();
        // change ELEMENTS


        String xPathExpression = "string(//scm[1]/@class)";
        String scmClass = (String) xpath.evaluate(xPathExpression, result.getNode(), XPathConstants.STRING);
        Assert.assertEquals(scmClass, "hudson.plugins.git.GitSCM");

        server.verify();
    }

    private byte[] readContent(String resourcename) throws IOException {
        URL url = JenkinsDomSourceMessageConverterTest.class.getResource(resourcename);
        long length = new File(url.getFile()).length();
        byte[] buf = new byte[(int) length];
        url.openStream().read(buf, 0, buf.length);
        return buf;
    }
}