package com.mgo.search.shell.render;

import com.mgo.search.model.Organization;
import com.mgo.search.model.PresentationDto;
import com.mgo.search.model.Ticket;
import com.mgo.search.model.User;
import com.mgo.search.service.EntityType;
import org.apache.commons.lang3.NotImplementedException;

import java.util.Map;

public class PresentationDtoRendererResolver implements PresentationDtoRenderer<PresentationDto> {

    private Map<EntityType, PresentationDtoRenderer> rendererMap;

    public PresentationDtoRendererResolver(Map<EntityType, PresentationDtoRenderer> rendererMap) {
        this.rendererMap = rendererMap;
    }

    @SuppressWarnings("unchecked")
    private String render(Organization organization) {
        return rendererMap.get(EntityType.ORGANIZATION).render(organization);
    }

    @SuppressWarnings("unchecked")
    private String render(User user) {
        return rendererMap.get(EntityType.USER).render(user);
    }

    @SuppressWarnings("unchecked")
    private String render(Ticket ticket) {
        return rendererMap.get(EntityType.TICKET).render(ticket);
    }

    public String render(PresentationDto dto) {
        if (dto instanceof Organization) {
            return render((Organization) dto);
        } else if (dto instanceof User) {
            return render((User) dto);
        } else if (dto instanceof Ticket) {
            return render((Ticket) dto);
        }

        throw new NotImplementedException(String.format("No render support for %s", dto.getClass().getName()));
    }
}
