package io.github.grano22.feedbrico.shared.infrastructure.logging;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.Principal;
import java.util.Optional;
import java.util.UUID;

public class EachRequestLogsEnricher extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(EachRequestLogsEnricher.class);

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            String requestId = Optional.ofNullable(request.getHeader("X-Request-Id"))
                    .orElse(UUID.randomUUID().toString());
            LoggerContext.setGlobalDetail("requestId", requestId);

            String clientIp = Optional.ofNullable(request.getHeader("X-Forwarded-For"))
                    .map(ip -> ip.split(",")[0].trim())
                    .orElse(request.getRemoteAddr());
            LoggerContext.setGlobalDetail("clientIp", clientIp);

            String userId = Optional.ofNullable(request.getUserPrincipal())
                    .map(Principal::getName)
                    .orElse("anonymous");
            LoggerContext.setGlobalDetail("userId", userId);

            LoggerContext.setGlobalDetail("httpMethod", request.getMethod());
            LoggerContext.setGlobalDetail("requestPath", request.getRequestURI());

            LoggerContext.setGlobalDetail("userAgent", Optional.ofNullable(request.getHeader("User-Agent")).orElse("unknown"));
            String sessionId = Optional.ofNullable(request.getRequestedSessionId()).orElse("no-session");
            LoggerContext.setGlobalDetail("sessionId", sessionId);
        } catch (Throwable t) {
            logger.error("Failed to enrich request logging context", t);
        } finally {
            LoggerContext.clearGlobalDetails();
        }

        try {
            logger.info("Request: {} {}", request.getMethod(), request.getRequestURI());
        } catch (Throwable t) {
            logger.error("Failed to log request", t);
        }

        filterChain.doFilter(request, response);

        try {
            logger.info("Response for {}: {}", request.getRequestURI(), response.getStatus());
        } catch (Throwable t) {
            logger.error("Failed to log response", t);
        }
    }
}
