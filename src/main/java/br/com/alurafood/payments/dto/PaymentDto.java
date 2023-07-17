package br.com.alurafood.payments.dto;

import br.com.alurafood.payments.enummeration.Status;
import br.com.alurafood.payments.model.Payment;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
@Setter
public class PaymentDto {
  private Long id;
  private BigDecimal value;
  private String name;
  private String number;
  private String expiration;
  private String code;
  private Status status;
  private Long orderId;
  private Long formOfPaymentId;

  public PaymentDto(Payment payment) {
    this.id = payment.getId();
    this.value = payment.getValue();
    this.name = payment.getName();
    this.number = payment.getNumber();
    this.expiration = payment.getExpiration();
    this.code = payment.getCode();
    this.status = payment.getStatus();
    this.orderId = payment.getOrderId();
    this.formOfPaymentId = payment.getFormOfPaymentId();
  }

  public Payment toModel() {
    return Payment.builder()
        .id(this.id)
        .value(this.value)
        .name(this.name)
        .number(this.number)
        .expiration(this.expiration)
        .code(this.code)
        .status(this.status)
        .orderId(this.orderId)
        .formOfPaymentId(this.formOfPaymentId)
        .build();
  }
}
