package br.com.alurafood.payments.service.impl;

import br.com.alurafood.payments.client.OrderClient;
import br.com.alurafood.payments.enummeration.Status;
import br.com.alurafood.payments.model.Payment;
import br.com.alurafood.payments.repository.PaymentRepository;
import br.com.alurafood.payments.service.IPaymentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public record PaymentService(
    PaymentRepository paymentRepository,
    OrderClient orderClient

) implements IPaymentService {
  @Override
  public Payment save(Payment payment) {
    payment.setStatus(Status.CREATED);
    return paymentRepository.save(payment);
  }

  @Override
  public Payment update(Long id, Payment payment) {
    Payment paymentToUpdate = paymentRepository.save(payment);
    paymentToUpdate.setId(id);
    return paymentToUpdate;
  }

  @Override
  public Page<Payment> findAll(Pageable pageable) {
    return paymentRepository.findAll(pageable);
  }

  @Override
  public Payment findById(Long id) {
    return paymentRepository
        .findById(id)
        .orElseThrow(EntityNotFoundException::new);
  }

  @Override
  public void delete(Long id) {
    Payment paymentToDelete = findById(id);
    assert paymentToDelete != null;
    paymentRepository.delete(paymentToDelete);
  }

  public void confirmedPayment(Long id) {
    Payment payment = findById(id);
    payment.setStatus(Status.CONFIRMED);
    paymentRepository.save(payment);
    orderClient.confirmOrderPayment(payment.getOrderId());
  }

  public void updateStatus(Long id) {
    Payment payment = findById(id);
    payment.setStatus(Status.CONFIRMED_NO_INTEGRATION);
    paymentRepository.save(payment);
  }
}


