package org.chibamu.boundary;

import org.chibamu.model.User;
import org.chibamu.service.api.UserService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

@Path("/users")
public class UserResource
{

    private UserService userService;

    @Inject
    public UserResource(UserService service)
    {
        userService = service;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(User user)
    {
        userService.create(user);
        URI location = UriBuilder.fromResource(UserResource.class)
                .path("/{isbn}")
                .resolveTemplate("isbn", user.getId())
                .build();
        return Response.created(location).build();
    }
}
