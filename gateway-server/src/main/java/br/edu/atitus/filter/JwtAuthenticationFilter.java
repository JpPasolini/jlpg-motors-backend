package br.edu.atitus.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import javax.crypto.SecretKey;

@Component
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {

    private final String SECRET_KEY = "minha_chave_secreta_muito_segura_e_longa_para_o_jwt_jlpg_motors";

    public JwtAuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            // 1. Verifica se o Header Authorization está presente
            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, "Header de autorização ausente", HttpStatus.UNAUTHORIZED);
            }

            String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return onError(exchange, "Padrão Token inválido", HttpStatus.UNAUTHORIZED);
            }

            String token = authHeader.substring(7);

            try {
                // 2. Valida e Extrai os Claims (dados de dentro do token)
                SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
                Claims claims = Jwts.parser()
                        .verifyWith(key)
                        .build()
                        .parseSignedClaims(token)
                        .getPayload();

                // 3. Regra de Autorização por Role (ADMIN)
                HttpMethod method = request.getMethod();

                // Se for criar (POST), editar (PUT) ou deletar (DELETE)...
                if (method == HttpMethod.POST || method == HttpMethod.PUT || method == HttpMethod.DELETE) {
                    String role = claims.get("role", String.class);

                    // Se não tiver a role ou ela não for ADMIN, barra com 403 Forbidden
                    if (role == null || !role.toUpperCase().contains("ADMIN")) {
                        return onError(exchange, "Acesso negado: Apenas administradores podem gerenciar veículos", HttpStatus.FORBIDDEN);
                    }
                }

            } catch (Exception e) {
                return onError(exchange, "Token JWT inválido ou expirado", HttpStatus.UNAUTHORIZED);
            }

            return chain.filter(exchange);
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        // Opcional: Adiciona o motivo do erro no cabeçalho para ajudar o frontend a debugar
        response.getHeaders().add("X-Auth-Error", err);
        return response.setComplete();
    }

    public static class Config {}
}