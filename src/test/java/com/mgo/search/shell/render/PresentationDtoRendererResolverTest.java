package com.mgo.search.shell.render;

import com.mgo.search.model.Organization;
import com.mgo.search.model.Ticket;
import com.mgo.search.model.User;
import com.mgo.search.service.EntityType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PresentationDtoRendererResolverTest {

    @Mock
    private OrganizationRenderer organizationRenderer;
    @Mock
    private UserRenderer userRenderer;
    @Mock
    private TicketRenderer ticketRenderer;

    private PresentationDtoRendererResolver resolver;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);

        Map<EntityType, PresentationDtoRenderer> map = new HashMap<>();
        map.put(EntityType.ORGANIZATION, organizationRenderer);
        map.put(EntityType.USER, userRenderer);
        map.put(EntityType.TICKET, ticketRenderer);

        resolver = new PresentationDtoRendererResolver(map);
    }

    @Test
    void shouldDelegateToOrganizationRendererWhenDtoIsOrganization() {
        Organization organization = mock(Organization.class);
        String renderedOrganization = "rendered organization";
        when(organizationRenderer.render(organization)).thenReturn(renderedOrganization);

        assertThat(resolver.render(organization), is(renderedOrganization));
    }

    @Test
    void shouldDelegateToUserRendererWhenDtoIsUser() {
        User user = mock(User.class);
        String renderedUser = "rendered user";
        when(userRenderer.render(user)).thenReturn(renderedUser);

        assertThat(resolver.render(user), is(renderedUser));
    }

    @Test
    void shouldDelegateToTicketRendererWhenDtoIsTicket() {
        Ticket ticket = mock(Ticket.class);
        String renderedTicket = "rendered ticket";
        when(ticketRenderer.render(ticket)).thenReturn(renderedTicket);

        assertThat(resolver.render(ticket), is(renderedTicket));
    }

    @Test
    void shouldThrowExceptionWhenDtoDoesNotHaveRenderer() {
        assertThrows(NotImplementedException.class, () -> resolver.render(new MockPresentationDto()));
    }
}