import com.github.tomakehurst.wiremock.client.WireMock;
import org.assertj.core.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
properties = {"httpbin=http://localhost:${wiremock.server.port}"})
@AutoConfigureWireMock(port = 0)
class AuthCenterApplicationTests {

    @Autowired
    private WebTestClient webClient;
//    @Test
    void contextLoads() {
        //Stubs
        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/get"))
                .willReturn(WireMock.aResponse()
                        .withBody("{\"headers\":{\"Hello\":\"World\"}}")
                        .withHeader("Content-Type", "application/json")));
        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/delay/3"))
                .willReturn(WireMock.aResponse()
                        .withBody("no fallback")
                        .withFixedDelay(3000)));

        webClient
                .get().uri("/get")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.headers.Hello").isEqualTo("World");

        webClient
                .get().uri("/delay/3")
                .header("Host", "www.hystrix.com")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(
                        response -> Assertions.assertThat(response.getResponseBody()).isEqualTo("fallback".getBytes()));
    }

}
