package com.chaconneluo.music.gateway.filter;

import com.chaconneluo.music.gateway.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.servlet.annotation.WebFilter;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;

/**
 * @author ChaconneLuo
 */

@Component
@RequiredArgsConstructor
@WebFilter("/file/**")
public class FileFilter implements GatewayFilter, Ordered {

    private final SecurityService securityService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        var request = exchange.getRequest();
        var response = exchange.getResponse();
        var URIPath = request.getURI().getPath();
        if (URIPath.contains("/account/register") || URIPath.contains("/account/login")) {
            return chain.filter(exchange);
        }
        var email = (String) exchange.getAttribute("email");

        var newPath = URIPath + "/" + email;
        var newRequest = request.mutate().path(newPath).build();
        exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, newRequest.getURI());

        return chain.filter(exchange.mutate()
                .request(newRequest).build());
    }

    @Override
    public int getOrder() {
        return 5;
    }
}