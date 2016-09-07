package benjamin.controller;

import benjamin.connector.sonar.model.Metric;
import benjamin.persistence.MetricsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
@RequestMapping(path = "/v1/metrics")
public class MetricsController {


    @Autowired
    private MetricsRepository metricsRepository;


    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Metric> list() {
        return metricsRepository.findAll();
    }


}