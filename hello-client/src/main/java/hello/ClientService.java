package hello;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

import java.net.URI;

@Service
@EnableCircuitBreaker
@EnableHystrixDashboard
public class ClientService {

  private final RestTemplate restTemplate;

  public ClientService(RestTemplate rest) {
    this.restTemplate = rest;
  }

  @HystrixCommand(fallbackMethod = "reliable")
  public String getClient() {
    URI uri = URI.create("http://localhost:8080/service-instances/a-bootiful-client");

    return this.restTemplate.getForObject(uri, String.class);
  }

  public String reliable() {
    return "Client not up. Switching to default client";
  }

  @HystrixCommand(fallbackMethod = "fallback")
  public String getMsg() {
    try{
      //Thread.sleep(5000);
    }catch(Exception e){
      // do nothing
    }
    URI uri = URI.create("http://localhost:8080/service-instances/a-bootiful-client");

    return this.restTemplate.getForObject(uri, String.class);
  }

  public String fallback() {
    return "Client not up. Switching to default message";
  }

}
