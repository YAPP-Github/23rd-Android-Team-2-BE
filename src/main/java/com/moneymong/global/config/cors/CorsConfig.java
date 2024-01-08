package com.moneymong.global.config.cors;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.io.IOException;

@Configuration
public class CorsConfig {

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public Filter corsFilter() {
        return new Filter() {
            @Override
            public void init(FilterConfig filterConfig) {
            }

            @Override
            public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
                HttpServletRequest request = (HttpServletRequest) req;
                HttpServletResponse response = (HttpServletResponse) res;

                response.setHeader("Access-Control-Allow-Origin", "*");
                response.setHeader("Access-Control-Allow-Credentials", "true");
                response.setHeader("Access-Control-Allow-Methods","*");
                response.setHeader("Access-Control-Max-Age", "3600");
                response.setHeader("Access-Control-Allow-Headers",
                        "Origin, X-Requested-With, Content-Type, Accept, Authorization");

                if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
                    response.setStatus(HttpServletResponse.SC_OK);
                } else {
                    chain.doFilter(req, res);
                }
            }

            @Override
            public void destroy() {
            }
        };
    }
}
