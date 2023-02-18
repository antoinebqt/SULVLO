package ca.ulaval.glo4003.main.api.responses;

import java.util.List;

public class ManyErrorsResponse extends ErrorResponse {

    public final List<String> errors;

    public ManyErrorsResponse(String description, List<String> errors) {
        super(description);
        this.errors = errors;
    }
}
