package br.com.alurafood.payments.controller;

import br.com.alurafood.payments.dto.PaymentDto;
import br.com.alurafood.payments.model.Payment;
import br.com.alurafood.payments.service.impl.PaymentService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@AllArgsConstructor
@RestController
@RequestMapping("/payments")
@Slf4j
public class PaymentController {
  private final PaymentService paymentService;

  @GetMapping
  public ResponseEntity<Page<PaymentDto>> findAll(Pageable pageable, @Value("${local.server.port}") String port) {
    Page<Payment> payments = paymentService.findAll(pageable);
    Page<PaymentDto> paymentDto = payments.map(PaymentDto::new);
    log.info("port: {}", port);
    return ResponseEntity.ok(paymentDto);
  }

  @GetMapping("/{id}")
  public ResponseEntity<PaymentDto> findById(@PathVariable Long id) {
    Payment payment = paymentService.findById(id);
    PaymentDto paymentDto = new PaymentDto(payment);
    return ResponseEntity.ok(paymentDto);
  }

  @PostMapping
  public ResponseEntity<PaymentDto> savePayment(@RequestBody @Valid PaymentDto paymentDto) {
    Payment payment = paymentDto.toModel();
    Payment savedPayment = paymentService.save(payment);
    URI uri = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("{/payments/{id}")
        .buildAndExpand(payment.getId())
        .toUri();
    return ResponseEntity.created(uri).body(new PaymentDto(savedPayment));
  }

  @PutMapping("/{id}")
  public ResponseEntity<PaymentDto> updatePayment(@PathVariable Long id, @RequestBody PaymentDto paymentDto) {
    Payment payment = paymentDto.toModel();
    Payment updatedPayment = paymentService.update(id, payment);
    PaymentDto updatedPaymentDto = new PaymentDto(updatedPayment);
    return ResponseEntity.ok(updatedPaymentDto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletePaymenr(@PathVariable Long id) {
    paymentService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/{id}/paid")
  @CircuitBreaker(name = "updateOrder", fallbackMethod = "confirmedPaymentWithPendentIntegration")
  public ResponseEntity<Void> confirmOrderPayment(@PathVariable Long id) {
    paymentService.confirmedPayment(id);
    return ResponseEntity.noContent().build();
  }

  public ResponseEntity<Void> confirmedPaymentWithPendentIntegration(Long id, Exception exception) {
    paymentService.updateStatus(id);
    return ResponseEntity.noContent().build();
  }
}
