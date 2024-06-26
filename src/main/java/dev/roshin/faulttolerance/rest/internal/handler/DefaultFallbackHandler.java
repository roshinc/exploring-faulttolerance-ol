package dev.roshin.faulttolerance.rest.internal.handler;

import jakarta.ws.rs.WebApplicationException;
import org.eclipse.microprofile.faulttolerance.FallbackHandler;
import org.eclipse.microprofile.faulttolerance.ExecutionContext;

import java.util.Map;

public class DefaultFallbackHandler implements FallbackHandler<Map<String, String>> {

    @Override
    public Map<String, String> handle(ExecutionContext context) {
        Throwable exception = context.getFailure();
        String method = context.getMethod().getDeclaringClass().getName();
        // Log the exception or handle it as needed
        System.err.println("Fallback triggered due to: " + exception.getMessage() + " in method: " + method);
        System.out.println("Fallback handler called");
        throw new WebApplicationException("Service Unavailable", 503);
    }
}
