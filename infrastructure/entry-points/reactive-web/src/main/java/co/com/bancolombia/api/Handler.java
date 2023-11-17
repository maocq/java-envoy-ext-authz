package co.com.bancolombia.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handler {

    public Mono<ServerResponse> listenUseCase(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(String.class)
                .switchIfEmpty(Mono.just("Empty"))
                .doOnNext(body -> printInfo(serverRequest, body))
                .flatMap(response -> ServerResponse.ok().bodyValue(response));
    }

    private void printInfo(ServerRequest serverRequest, String body) {
        System.out.println("Method: " + serverRequest.method());
        System.out.println("Path: " + serverRequest.path());
        System.out.println("Uri: " + serverRequest.uri());

        System.out.println("Headers: " + serverRequest.headers());

        System.out.println("Path variables: " + serverRequest.pathVariables());
        System.out.println("Query params: " + serverRequest.queryParams());

        System.out.println("Body: " + body);
    }
}
