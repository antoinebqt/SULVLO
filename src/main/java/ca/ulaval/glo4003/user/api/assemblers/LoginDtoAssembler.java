package ca.ulaval.glo4003.user.api.assemblers;

import ca.ulaval.glo4003.user.api.dtos.TokenResponse;

public class LoginDtoAssembler {

    public TokenResponse toResponse(String token) {
        return new TokenResponse(token);
    }

}
