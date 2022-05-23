package com.chaconneluo.music.gateway.filter;

import com.chaconneluo.music.gateway.common.Const;
import com.chaconneluo.music.gateway.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author ChaconneLuo
 */

@Component
@RequiredArgsConstructor
public class AuthorizeFilter implements GlobalFilter, Ordered {

    private final SecurityService securityService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        var request = exchange.getRequest();
        var response = exchange.getResponse();
        var URIPath = request.getURI().getPath();
        if (URIPath.contains("/account/register") || URIPath.contains("/account/login")) {
            return chain.filter(exchange);
        }

        var requestToken = request.getHeaders().getFirst(Const.TOKEN_COOKIE_NAME);
        if (requestToken == null) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        var token = securityService.verifyJWT(requestToken);
        if (!token.equals("")) {
            var cookie = ResponseCookie.from(Const.TOKEN_COOKIE_NAME, token).path("/").build();
            response.addCookie(cookie);
            return chain.filter(exchange);
        }
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
