package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;


import java.util.List;

@EnableDiscoveryClient
@SpringBootApplication
@RestController
public class HelloApp {

    @Autowired
    private ClientService clientService;
    
    @Bean
    public RestTemplate rest(RestTemplateBuilder builder) {
      return builder.build();
    }
  
    @RequestMapping("/hello/{name}")
    public String greeting(
            @PathVariable String name) {
        return("Hello " + name);
    }

    @RequestMapping("/client")
    public String findHost() {
      return clientService.getClient();
    }

    @RequestMapping("/msg")
    public String getMsg() {
      return clientService.getMsg();
    }

    public static void main(String[] args) {
        SpringApplication.run(HelloApp.class, args);
    }
}
