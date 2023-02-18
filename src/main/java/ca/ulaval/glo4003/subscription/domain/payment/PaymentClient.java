package ca.ulaval.glo4003.subscription.domain.payment;

import ca.ulaval.glo4003.subscription.domain.payment.creditCard.CreditCard;

public interface PaymentClient {
    void debit(Money money, CreditCard creditCard);
}
