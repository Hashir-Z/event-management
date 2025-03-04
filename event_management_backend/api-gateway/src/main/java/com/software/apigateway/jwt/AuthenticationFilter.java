package com.software.apigateway.jwt;

import lombok.RequiredArgsConstructor;
import com.software.apigateway.model.ErrorResponseDto;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthenticationFilter implements GlobalFilter, Ordered {

    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        Route attribute = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
        assert attribute != null;
        String application = attribute.getId();

        if (application == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not authorized to access this resource 1");
        }

        if (!application.contains("auth")) {
            List<String> authorizationList = exchange.getRequest().getHeaders().get("Authorization");
            assert authorizationList != null;
            String authorization = authorizationList.isEmpty() ? "" : authorizationList.get(0);
            try {
                jwtTokenUtil.validateToken(authorization);
            } catch (Exception e) {
                List<String> details = new ArrayList<>();
                details.add(e.getLocalizedMessage());
                ErrorResponseDto error = new ErrorResponseDto(new Date(), HttpStatus.UNAUTHORIZED.value(), "UNAUTHORIZED", details, exchange.getRequest().getURI().toString());
                ServerHttpResponse response = exchange.getResponse();
                byte[] bytes = SerializationUtils.serialize(error);
                assert bytes != null;
                DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
                response.writeWith(Flux.just(buffer));
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            }
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
