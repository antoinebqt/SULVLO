package ca.ulaval.glo4003.user.api;

import ca.ulaval.glo4003.main.application.message.ErrorMessages;
import ca.ulaval.glo4003.user.api.assemblers.LoginDtoAssembler;
import ca.ulaval.glo4003.user.api.dtos.TokenResponse;
import ca.ulaval.glo4003.user.application.UserService;
import ca.ulaval.glo4003.user.application.dtos.LoginDto;
import ca.ulaval.glo4003.user.application.dtos.UserCreationDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path(("/"))
public class UserResource {
    private final UserService userService;
    private final LoginDtoAssembler loginDtoAssembler;

    public UserResource(UserService userService, LoginDtoAssembler loginDtoAssembler) {
        this.userService = userService;
        this.loginDtoAssembler = loginDtoAssembler;
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Path("users")
    @POST
    public Response createUser(
            @Valid @NotNull(message = ErrorMessages.MISSING_REQUEST) UserCreationDto userCreationDto) {
        userService.createUser(userCreationDto);

        return Response.status(Response.Status.CREATED).build();
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("login")
    @POST
    public Response login(
            @Valid @NotNull(message = ErrorMessages.MISSING_REQUEST) LoginDto loginDto) {
        String token = userService.login(loginDto);

        TokenResponse tokenResponse = loginDtoAssembler.toResponse(token);

        return Response.ok().entity(tokenResponse).build();
    }
}
