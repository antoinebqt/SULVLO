package ca.ulaval.glo4003.communication.domain.emails;

import ca.ulaval.glo4003.code.domain.UnlockCode;
import ca.ulaval.glo4003.communication.domain.Email;
import ca.ulaval.glo4003.user.domain.EmailAddress;

public class CodeEmail extends Email {

    private static final String SUBJECT = "Your SULVLO electric bike code";
    private static final String MESSAGE = "Your code is %s";

    public CodeEmail(EmailAddress addressee, UnlockCode unlockCode) {
        super(SUBJECT, String.format(MESSAGE, unlockCode.getValue()), addressee);
    }
}
