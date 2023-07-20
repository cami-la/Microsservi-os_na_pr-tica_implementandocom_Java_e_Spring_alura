package br.com.alurafood.payments.service;

import br.com.alurafood.payments.model.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IPaymentService {
  Payment save(Payment payment);
  Payment update(Long id, Payment payment);
  Page<Payment> findAll(Pageable pageable);
  Payment findById(Long id);
  void delete(Long id);
}
