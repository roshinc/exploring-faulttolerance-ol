package dev.roshin.faulttolerance.rest.resources;

import dev.roshin.faulttolerance.rest.internal.handler.DefaultFallbackHandler;
import jakarta.ws.rs.*;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Path("/main")
public class MainResource {

    private final Random random = new Random();


    /**
     * CircuitBreaker Configuration Properties:
     * <p>
     * requestVolumeThreshold:
     *     The minimum number of requests that must be made within the rolling
     *     window before the circuit breaker can trip. Default is 4.
     * <p>
     * failureRatio:
     *     The failure threshold above which the circuit breaker will trip open.
     *     Value between 0 and 1. Default is 0.5 (50% failures).
     * <p>
     * delay:
     *     The delay in milliseconds after tripping the circuit before allowing
     *     another request through. Default is 5000ms (5 seconds).
     * <p>
     * successThreshold:
     *     The number of consecutive successful invocations required to close
     *     the circuit, once it's half-open. Default is 2.
     */

    @POST
    @Path("/echo")
    @Produces("application/json")
    @Consumes("application/json")
    @CircuitBreaker(requestVolumeThreshold = 4,
            failureRatio = 0.5,
            delay = 5000,
            successThreshold = 2)
    @Fallback(DefaultFallbackHandler.class)
    public Map<String, String> echoMap(Map<String, String> inputMap) throws InterruptedException {
        // Simulate a delay between 100ms and 2000ms
        long delay = 100 + random.nextInt(1901);
        TimeUnit.MILLISECONDS.sleep(delay);

        // Simulate a failure 50% of the time
        if (random.nextBoolean()) {
            System.out.println("Simulated failure in echoMap");
            throw new RuntimeException("Simulated failure in echoMap");
        }

        Map<String, String> responseMap = new HashMap<>(inputMap);
        responseMap.put("response", "success");
        responseMap.put("delay", String.valueOf(delay));

        return responseMap;
    }
}