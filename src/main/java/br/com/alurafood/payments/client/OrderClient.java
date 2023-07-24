package br.com.alurafood.payments.client;

import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "order-ms")
public interface OrderClient {
  @RequestMapping(method = RequestMethod.PUT, value = "/orders/{id}/paid")
  ResponseEntity<Void> confirmOrderPayment(@PathVariable @NotNull Long id);
}
