package ro.uvt.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;

@Component
public class RequestLoggingFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(
            HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {

        long start = System.currentTimeMillis();
        String path = req.getMethod() + " " + req.getRequestURI();
        System.out.println("[LOG] --> " + path + " at " + Instant.now());

        try {
            chain.doFilter(req, res); // zincirde bir sonraki işlem
        } finally {
            long ms = System.currentTimeMillis() - start;
            System.out.println("[LOG] <-- " + path + " status=" + res.getStatus() + " (" + ms + " ms)");
        }
    }
}
