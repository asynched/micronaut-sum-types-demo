package tech.asynched.services;

import jakarta.inject.Singleton;
import tech.asynched.entities.Payment;
import tech.asynched.functional.optional.Optional;
import tech.asynched.functional.result.Result;
import tech.asynched.repositories.PaymentRepository;


@Singleton
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Result<Void, PaymentRepository.PaymentRepositoryError> create(String payerId, String payeeId, Double amount) {
        return paymentRepository.create(payerId, payeeId, amount);
    }

    public Optional<Payment> findById(Long id) {
        return paymentRepository.findById(id);
    }
}
