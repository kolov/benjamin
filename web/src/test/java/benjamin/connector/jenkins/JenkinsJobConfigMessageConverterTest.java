package benjamin.connector.jenkins;


import benjamin.ApplicationConfiguration;
import benjamin.connector.common.RestHelper;
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

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;

@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(locations = "/converters-tests.properties")
@ContextConfiguration(classes = {ApplicationConfiguration.class, RestHelper.class})
public class JenkinsJobConfigMessageConverterTest {


    @Autowired
    private RestHelper restHelper;

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void testRestTemplate() throws Exception {

        MockRestServiceServer server = MockRestServiceServer.createServer(restTemplate);

        server.expect(requestTo("/")).andExpect(method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withSuccess(readContent("/jenkins-commit-job-config.xml"),
                        MediaType.APPLICATION_XML));

        JenkinsJobConfig cfg = restTemplate.getForObject("/", JenkinsJobConfig.class);

        assertNotNull(cfg.getScm());
        assertEquals(cfg.getScm().getClazz(), "hudson.plugins.git.GitSCM");
        assertNotNull(cfg.getScm().getUserRemoteConfigs());
        assertNotNull(cfg.getScm().getUserRemoteConfigs().getUserRemoteConfigList());
        assertEquals("https://jenkins@bitbucket.xxx.com:7999/some/module.git",
                cfg.getScm().getUserRemoteConfigs().getUserRemoteConfigList().get(0).getUrl().trim());


        server.verify();
    }

    private byte[] readContent(String resourcename) throws IOException {
        URL url = JenkinsJobConfigMessageConverterTest.class.getResource(resourcename);
        long length = new File(url.getFile()).length();
        byte[] buf = new byte[(int) length];
        url.openStream().read(buf, 0, buf.length);
        return buf;
    }
}