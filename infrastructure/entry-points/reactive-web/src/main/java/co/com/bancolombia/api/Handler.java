package co.com.bancolombia.api;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@Component
@RequiredArgsConstructor
public class Handler {

    public Mono<ServerResponse> listenUseCase(ServerRequest serverRequest) {
        printInfo(serverRequest);

        if (serverRequest.headers().firstHeader("x-value") == null)
            return ServerResponse.status(FORBIDDEN).bodyValue(Response.builder().message("Invalid authorization :(").build());

        return serverRequest.principal()
                .flatMap(principal -> {
                    if (principal instanceof Authentication authentication)
                        System.out.println("Authorities: " + authentication.getAuthorities());

                    return ServerResponse.ok()
                            .header("x-user-name", principal.getName())
                            .bodyValue(Response.builder().message(principal.getName()).build());
                });
    }

    private void printInfo(ServerRequest serverRequest) {
        System.out.println("Method: " + serverRequest.method());
        System.out.println("Path: " + serverRequest.path());
        System.out.println("Uri: " + serverRequest.uri());

        System.out.println("Headers: " + serverRequest.headers());

        System.out.println("Path variables: " + serverRequest.pathVariables());
        System.out.println("Query params: " + serverRequest.queryParams());
    }

    @Builder
    record Response(String message) {}
}
