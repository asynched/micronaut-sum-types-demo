package tech.asynched.repositories;

import tech.asynched.entities.Payment;
import tech.asynched.functional.optional.Optional;
import tech.asynched.functional.result.Result;

public interface PaymentRepository {
    Result<Void, PaymentRepositoryError> create(String payerId, String payeeId, Double amount);
    Optional<Payment> findById(Long id);

    sealed interface PaymentRepositoryError {
    }

    final class StorageFault implements PaymentRepositoryError {}
    final class TimeoutError implements PaymentRepositoryError {}
}
