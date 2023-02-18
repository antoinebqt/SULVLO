package ca.ulaval.glo4003.communication.domain.emails;

import ca.ulaval.glo4003.communication.domain.Email;
import ca.ulaval.glo4003.subscription.domain.payment.Invoice;
import ca.ulaval.glo4003.user.domain.EmailAddress;

public class InvoiceEmail extends Email {

    private static final String SUBJECT = "Thanks for using SULVLO!";
    private static final String MESSAGE = "You paid %.2f$";

    public InvoiceEmail(EmailAddress addressee, Invoice invoice) {
        super(SUBJECT, String.format(MESSAGE, invoice.getCost().toDouble()), addressee);
    }
}
