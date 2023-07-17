package br.com.alurafood.payments.service;

import br.com.alurafood.payments.repository.PaymentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public record PaymentService(
    PaymentRepository paymentRepository,
    ModelMapper modelMapper
) {



}


