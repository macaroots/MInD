package br.ifce.mind.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.HttpStatus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;

import java.net.URI;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import br.com.hapvida.wsmonitor.service.StatusService;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


class StatusServiceTest {

	 private static final Logger LOGGER = LoggerFactory.getLogger(StatusServiceTest.class);
	
	private ClientAndServer mockServer;
	private StatusService service;
	private WebClient webClient;
	
	private static String BASE_URL = "http://localhost:1080";
	String base_uri = "http://localhost:8080/wsmonitor/v1";
/*
	@BeforeEach
    public void startMockServer() {
        mockServer = startClientAndServer(1080);

        // set up mock with a delay of 5 seconds
        mockServer.when(HttpRequest.request().withMethod("GET")
        .withPath("/accounts")).
                respond(HttpResponse.response()
                        .withBody("{ \"result\": \"ok\"}")
                        .withDelay(TimeUnit.MILLISECONDS, 5000));
        
        webClient = WebClient.builder().build();
    }
	
	private Mono<JsonNode> doGetWithDefaultConnectAndReadTimeOut(URI uri, long timeout) {
        return webClient.get()
                .uri(uri)
                .retrieve()
                .onStatus(HttpStatus::isError, clientResponse -> {
                    LOGGER.error("Error while calling endpoint {} with status code {}",
                    uri.toString(), clientResponse.statusCode());
                    throw new RuntimeException("Error while calling  accounts endpoint");
        }).bodyToMono(JsonNode.class)
                // setting the signal timeout
                .timeout(Duration.ofMillis(timeout));
    }
	
	@Test
    public void testWebClient() {
		
        URI uri = UriComponentsBuilder.fromUriString(BASE_URL + "/accounts").build().toUri();
        // do the service call out with 3 seconds of signal timeout
        
        //Optional<StatusResponse> resposta = service.getApiVersion(base_uri, endpoint);
        //System.out.println("O resultado do teste do status foi:" + resposta);
        
        
        Mono<JsonNode> result = doGetWithDefaultConnectAndReadTimeOut(uri, 3000);
        StepVerifier.create(result)
                .expectSubscription()
                .assertNext(jsonNode -> assertEquals("ok", jsonNode.get("result").textValue()))
                .verifyComplete();
    }
	
	 @AfterEach
	    public void stopMockServer() {
	        mockServer.stop();
	    }
*/
}
