package com.mgo.search;

import com.mgo.search.converter.OrganizationMarshaller;
import com.mgo.search.converter.PresentationMarshaller;
import com.mgo.search.converter.TicketMarshaller;
import com.mgo.search.converter.UserMarshaller;
import com.mgo.search.index.FieldValueExtractor;
import com.mgo.search.index.InMemoryMapIndexService;
import com.mgo.search.index.IndexService;
import com.mgo.search.model.Organization;
import com.mgo.search.model.Ticket;
import com.mgo.search.model.User;
import com.mgo.search.reposiory.OrganizationJsonRepository;
import com.mgo.search.reposiory.Repository;
import com.mgo.search.reposiory.TicketJsonRepository;
import com.mgo.search.reposiory.UserJsonRepository;
import com.mgo.search.reposiory.entity.OrganizationEntity;
import com.mgo.search.reposiory.entity.TicketEntity;
import com.mgo.search.reposiory.entity.UserEntity;
import com.mgo.search.reposiory.reader.JsonReader;
import com.mgo.search.service.SearchService;
import com.mgo.search.service.SearchServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class SearchAppConfig {

    @Bean
    public JsonReader jsonReader() {
        return new JsonReader();
    }

    @Bean
    public Repository<UserEntity> userEntityRepository(JsonReader jsonReader) throws IOException {
        return new UserJsonRepository("data/users.json", jsonReader);
    }

    @Bean
    public Repository<OrganizationEntity> organizationEntityRepository(JsonReader jsonReader) throws IOException {
        return new OrganizationJsonRepository("data/organizations.json", jsonReader);
    }

    @Bean
    public Repository<TicketEntity> ticketEntityRepository(JsonReader jsonReader) throws IOException {
        return new TicketJsonRepository("data/tickets.json", jsonReader);
    }

    @Bean
    public FieldValueExtractor fieldValueExtractor() {
        return new FieldValueExtractor();
    }

    @Bean
    public IndexService<OrganizationEntity> organizationEntityIndexService(Repository<OrganizationEntity> organizationEntityRepository,
                                                                           FieldValueExtractor fieldValueExtractor) {
        return new InMemoryMapIndexService<>(organizationEntityRepository, fieldValueExtractor);
    }

    @Bean
    public IndexService<UserEntity> userEntityIndexService(Repository<UserEntity> userEntityRepository,
                                                           FieldValueExtractor fieldValueExtractor) {
        return new InMemoryMapIndexService<>(userEntityRepository, fieldValueExtractor);
    }

    @Bean
    public IndexService<TicketEntity> ticketEntityIndexService(Repository<TicketEntity> ticketEntityRepository,
                                                               FieldValueExtractor fieldValueExtractor) {
        return new InMemoryMapIndexService<>(ticketEntityRepository, fieldValueExtractor);
    }

    @Bean
    public PresentationMarshaller<OrganizationEntity, Organization> organizationMarshaller() {
        return new OrganizationMarshaller();
    }

    @Bean
    public PresentationMarshaller<UserEntity, User> userMarshaller(Repository<OrganizationEntity> organizationEntityRepository,
                                                                   PresentationMarshaller<OrganizationEntity, Organization> organizationMarshaller) {
        return new UserMarshaller(organizationEntityRepository, organizationMarshaller);
    }

    @Bean
    public PresentationMarshaller<TicketEntity, Ticket> ticketMarshaller(Repository<OrganizationEntity> organizationEntityRepository,
                                                                         PresentationMarshaller<OrganizationEntity, Organization> organizationMarshaller,
                                                                         Repository<UserEntity> userEntityRepository,
                                                                         PresentationMarshaller<UserEntity, User> userMarshaller) {
        return new TicketMarshaller(organizationEntityRepository, organizationMarshaller, userEntityRepository, userMarshaller);
    }

    @Bean
    public SearchService<Organization> organizationSearchService(IndexService<OrganizationEntity> organizationIndexService,
                                                                 Repository<OrganizationEntity> organizationRepository,
                                                                 PresentationMarshaller<OrganizationEntity, Organization> organizationMarshaller) {
        return new SearchServiceImpl<>(organizationIndexService, organizationRepository, organizationMarshaller);
    }

    @Bean
    public SearchService<User> userSearchService(IndexService<UserEntity> userIndexService, Repository<UserEntity> userRepository,
                                                 PresentationMarshaller<UserEntity, User> userMarshaller) {
        return new SearchServiceImpl<>(userIndexService, userRepository, userMarshaller);
    }

    @Bean
    public SearchService<Ticket> ticketSearchService(IndexService<TicketEntity> ticketEntityIndexService,
                                                   Repository<TicketEntity> ticketRepository,
                                                   PresentationMarshaller<TicketEntity, Ticket> ticketMarshaller) {
        return new SearchServiceImpl<>(ticketEntityIndexService, ticketRepository, ticketMarshaller);
    }
}
