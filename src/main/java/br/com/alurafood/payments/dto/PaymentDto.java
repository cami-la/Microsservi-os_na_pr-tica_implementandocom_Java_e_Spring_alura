package br.com.alurafood.payments.dto;

import br.com.alurafood.payments.enummeration.Status;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Builder
@Getter
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
}
