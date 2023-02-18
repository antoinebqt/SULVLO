package ca.ulaval.glo4003.subscription.infrastructure.payment;

import ca.ulaval.glo4003.subscription.domain.payment.Money;
import ca.ulaval.glo4003.subscription.domain.payment.PaymentClient;
import ca.ulaval.glo4003.subscription.domain.payment.creditCard.CreditCard;

public class DevPaymentClientImpl implements PaymentClient {
    @Override
    public void debit(Money money, CreditCard creditCard) {
        System.out.printf("Executing transaction debiting %.2f$ from the provided credit card.\n", money.toDouble());
    }

}
