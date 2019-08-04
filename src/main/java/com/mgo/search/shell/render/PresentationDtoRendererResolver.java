package com.mgo.search.shell.render;

import com.mgo.search.model.Organization;
import com.mgo.search.model.PresentationDto;
import com.mgo.search.model.Ticket;
import com.mgo.search.model.User;
import com.mgo.search.service.EntityType;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Map;

public class PresentationDtoRendererResolver implements PresentationDtoRenderer<PresentationDto> {

    private Map<EntityType, PresentationDtoRenderer> rendererMap;

    public PresentationDtoRendererResolver(Map<EntityType, PresentationDtoRenderer> rendererMap) {
        this.rendererMap = rendererMap;
    }

    @SuppressWarnings("unchecked")
    public String render(Organization organization) {
        return rendererMap.get(EntityType.ORGANIZATION).render(organization);
    }

    @SuppressWarnings("unchecked")
    public String render(User user) {
        return rendererMap.get(EntityType.USER).render(user);
    }

    @SuppressWarnings("unchecked")
    public String render(Ticket ticket) {
        return rendererMap.get(EntityType.TICKET).render(ticket);
    }

    public String render(PresentationDto dto) {
        throw new NotImplementedException();
    }
}
