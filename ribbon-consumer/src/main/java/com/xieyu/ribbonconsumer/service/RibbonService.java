package com.xieyu.ribbonconsumer.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheRemove;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * Description:
 *
 * @author 谢宇
 * Date: 2019/3/24 22:24
 */
@Service
public class RibbonService {
    @Autowired
    private WebClient.Builder webClientBuilder;
    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(groupKey = "testGroup",
            commandKey = "testRTG",
            threadPoolKey = "testGetThread",
            fallbackMethod = "getError")
    @CacheResult
    public String testRestTemplate() {
        return restTemplate.getForEntity("http://HELLO-SERVICE/hello?name={1}", String.class, "Jack").getBody();
    }

    @HystrixCommand(groupKey = "testGroup",
            commandKey = "testRTC",
            threadPoolKey = "testGetThread",
            fallbackMethod = "getError")
    @CacheRemove(commandKey = "testRTG")
    public String testChangeRestTemplate() {
        return restTemplate.getForEntity("http://HELLO-SERVICE/hello?name={1}", String.class, "Jack").getBody();
    }

    @HystrixCommand(fallbackMethod = "getError")
    public String testWebClient() {
        Mono<String> result = webClientBuilder.build()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("http")
                        .host("HELLO-SERVICE")
                        .path("/hello")
                        .queryParam("name", "Tom")
                        .build())

                .retrieve()
                .bodyToMono(String.class);
        return result.block();
    }

    public String getError() {
        return "hi,sorry error!";
    }
}
