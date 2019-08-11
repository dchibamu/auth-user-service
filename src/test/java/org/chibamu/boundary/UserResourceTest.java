package org.chibamu.boundary;

import org.chibamu.model.User;
import org.chibamu.service.api.UserService;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.internal.inject.Binder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import static org.junit.Assert.assertEquals;

public class UserResourceTest extends JerseyTest
{
    @Mock
    private UserService mockUserService;

    @Override
    public Application configure()
    {
        MockitoAnnotations.initMocks(this);
        ResourceConfig resourceConfig = new ResourceConfig(UserResource.class);
        Binder binder = new AbstractBinder() {
            @Override
            protected void configure() {
                bind(mockUserService).to(UserService.class);
            }
        };
        resourceConfig.register(binder);
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        return resourceConfig;
    }

    @Test
    public void shouldCreateUserSuccessfully()
    {
        User user = new User(100, "Ngonidzashe", "chibamudoubleO7@gmail.com");
        Response response = target("/users").request().post(Entity.entity(user, MediaType.APPLICATION_JSON));
        assertEquals("Should return status 201", Status.CREATED.getStatusCode(), response.getStatus());
    }
}
