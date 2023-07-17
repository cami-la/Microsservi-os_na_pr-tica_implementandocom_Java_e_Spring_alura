package br.com.alurafood.payments.model;

import br.com.alurafood.payments.enummeration.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@Entity
@Getter
@NoArgsConstructor
@Setter
@Table(name = "pagamentos")
public class Payment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @NotNull
  @Positive
  private BigDecimal value;
  @NotBlank
  @Size(max=100)
  private String name;
  @NotBlank
  @Size(max = 19)
  private String number;
  @NotBlank
  @Size(max = 7)
  private String expiration;
  @NotBlank
  @Size(min = 3, max = 3)
  private String code;
  @NotNull
  @Enumerated
  private Status status;
  @NotNull
  private Long orderId;
  @NotNull
  private Long formOfPaymentId;
}
