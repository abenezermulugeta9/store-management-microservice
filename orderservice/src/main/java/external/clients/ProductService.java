package external.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * The application name of the feign client should be the same as in the configuration file of the product service.
 * The request mapping configuration should be the same as in the controller of the product service.
 */
@FeignClient(name = "PRODUCT-SERVICE/products")
public interface ProductService {
    /**
     * The method declaration should be the same as in the controller in the product service.
     * Because this is an external call, it needs to have the same api in order to establish a connection between the two services.
     */
    @PutMapping("/reduce-quantity/{id}")
    ResponseEntity<Void> reduceQuantity(@PathVariable("id") Long productId, @RequestParam Long quantity);
}
