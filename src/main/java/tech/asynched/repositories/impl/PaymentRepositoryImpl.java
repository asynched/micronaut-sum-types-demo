package tech.asynched.repositories.impl;

import io.micronaut.transaction.annotation.ReadOnly;
import io.micronaut.transaction.annotation.Transactional;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;
import tech.asynched.entities.Payment;
import tech.asynched.functional.optional.Optional;
import tech.asynched.functional.result.Err;
import tech.asynched.functional.result.Ok;
import tech.asynched.functional.result.Result;
import tech.asynched.repositories.PaymentRepository;

import java.util.Random;


@Singleton
public class PaymentRepositoryImpl implements PaymentRepository {
    private final EntityManager entityManager;

    public PaymentRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public Result<Void, PaymentRepositoryError> create(String payerId, String payeeId, Double amount) {
        var payment = new Payment(
            payerId,
            payeeId,
            amount
        );

        var random = new Random();

        if (random.nextBoolean()) {
            var val = random.nextInt(2);

            if (val == 0) {
                return new Err<>(new TimeoutError());
            }

            return new Err<>(new StorageFault());
        }

        entityManager.persist(payment);
        return new Ok<>(null);
    }

    @Override
    @ReadOnly
    public Optional<Payment> findById(Long id) {
        return Optional.fromNullable(entityManager.find(Payment.class, id));
    }
}
