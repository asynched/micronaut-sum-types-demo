package tech.asynched.controllers;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import io.micronaut.http.exceptions.HttpStatusException;
import io.micronaut.serde.annotation.Serdeable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.asynched.entities.Payment;
import tech.asynched.functional.optional.None;
import tech.asynched.functional.optional.Some;
import tech.asynched.functional.result.Err;
import tech.asynched.functional.result.Ok;
import tech.asynched.repositories.PaymentRepository;
import tech.asynched.services.PaymentService;

@Controller("/payments")
public class PaymentController {
    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Get("/{id}")
    public Payment findById(@PathVariable("id") Long id) {
        var result = paymentService.findById(id);

        return switch (result) {
            case Some(Payment payment) -> payment;
            case None() -> throw new HttpStatusException(HttpStatus.NOT_FOUND, "Payment not found");
        };
    }

    @Post
    public CreatePaymentResponse create(@Body CreatePaymentDto data) {
        var result = paymentService.create(data.payerId(), data.payeeId(), data.amount());

        return switch (result) {
            case Ok(Void _response) -> CreatePaymentResponse.fromDefaults();
            case Err(PaymentRepository.PaymentRepositoryError err) -> {
                logger.error("Payment creation failed {}", err);

                throw new HttpStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Payment creation failed");
            }
        };
    }

    @Serdeable
    public record CreatePaymentResponse(String status) {
        public static CreatePaymentResponse fromDefaults() {
            return new CreatePaymentResponse("ok");
        }
    }

    @Serdeable
    public record CreatePaymentDto(
        String payerId,
        String payeeId,
        Double amount
    ) {}
}
