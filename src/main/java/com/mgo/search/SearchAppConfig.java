package com.mgo.search;

import com.mgo.search.converter.OrganizationMarshaller;
import com.mgo.search.converter.PresentationMarshaller;
import com.mgo.search.converter.TicketMarshaller;
import com.mgo.search.converter.UserMarshaller;
import com.mgo.search.index.*;
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
import com.mgo.search.service.EntityType;
import com.mgo.search.service.SearchService;
import com.mgo.search.service.SearchServiceImpl;
import com.mgo.search.service.SearchableFieldService;
import com.mgo.search.shell.render.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class SearchAppConfig {

    @Bean
    public JsonReader jsonReader() {
        return new JsonReader();
    }

    @Bean
    public Repository<UserEntity> userEntityRepository(JsonReader jsonReader) throws IOException {
        return new UserJsonRepository(new ClassPathResource("data/users.json").getInputStream(), jsonReader);
    }

    @Bean
    public Repository<OrganizationEntity> organizationEntityRepository(JsonReader jsonReader) throws IOException {
        return new OrganizationJsonRepository(new ClassPathResource("data/organizations.json").getInputStream(), jsonReader);
    }

    @Bean
    public Repository<TicketEntity> ticketEntityRepository(JsonReader jsonReader) throws IOException {
        return new TicketJsonRepository(new ClassPathResource("data/tickets.json").getInputStream(), jsonReader);
    }

    @Bean
    public FieldValueExtractor fieldValueExtractor() {
        return new FieldValueExtractor();
    }

    @Bean
    public IndexFieldNameResolver fieldNameResolver() {
        return new SerializedFieldNameResolverImpl();
    }

    @Bean
    public IndexService<OrganizationEntity> organizationEntityIndexService(Repository<OrganizationEntity> organizationEntityRepository,
                                                                           FieldValueExtractor fieldValueExtractor,
                                                                           IndexFieldNameResolver fieldNameResolver) {
        return new InMemoryMapIndexService<>(organizationEntityRepository, fieldValueExtractor, fieldNameResolver);
    }

    @Bean
    public IndexService<UserEntity> userEntityIndexService(Repository<UserEntity> userEntityRepository,
                                                           FieldValueExtractor fieldValueExtractor,
                                                           IndexFieldNameResolver fieldNameResolver) {
        return new InMemoryMapIndexService<>(userEntityRepository, fieldValueExtractor, fieldNameResolver);
    }

    @Bean
    public IndexService<TicketEntity> ticketEntityIndexService(Repository<TicketEntity> ticketEntityRepository,
                                                               FieldValueExtractor fieldValueExtractor,
                                                               IndexFieldNameResolver fieldNameResolver) {
        return new InMemoryMapIndexService<>(ticketEntityRepository, fieldValueExtractor, fieldNameResolver);
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

    @Bean
    public SearchableFieldService searchableFieldService(IndexFieldNameResolver fieldNameResolver) {
        return new SearchableFieldService(fieldNameResolver);
    }

    @Bean
    public SearchableFieldRenderer searchableFieldRenderer(SearchableFieldService searchableFieldService) {
        return new SearchableFieldRenderer(searchableFieldService);
    }

    @Bean
    public FieldValueRenderer fieldValueRenderer() {
        return new FieldValueRenderer();
    }

    @Bean
    public PresentationDtoSummaryRenderer presentationDtoRenderer(FieldValueRenderer fieldValueRenderer) {
        return new PresentationDtoSummaryRenderer(fieldValueRenderer);
    }

    @Bean
    public Map<EntityType, SearchService> searchServiceMap(SearchService<Organization> orgSearch,
                                                           SearchService<User> userSearch,
                                                           SearchService<Ticket> ticketSearchService) {
        Map<EntityType, SearchService> map = new HashMap<>();
        map.put(EntityType.ORGANIZATION, orgSearch);
        map.put(EntityType.USER, userSearch);
        map.put(EntityType.TICKET, ticketSearchService);
        return map;
    }

    @Bean
    public UserRenderer userRenderer(PresentationDtoSummaryRenderer presentationDtoRenderer,
                                     SearchService<Ticket> ticketSearchService){
        return new UserRenderer(presentationDtoRenderer, ticketSearchService);
    }

    @Bean
    public OrganizationRenderer organizationRenderer(PresentationDtoSummaryRenderer presentationDtoRenderer,
                                                     SearchService<User> userSearchService,
                                                     SearchService<Ticket> ticketSearchService){
        return new OrganizationRenderer(presentationDtoRenderer, userSearchService, ticketSearchService);
    }

    @Bean
    public TicketRenderer ticketRenderer(PresentationDtoSummaryRenderer presentationDtoRenderer){
        return new TicketRenderer(presentationDtoRenderer);
    }

    @Bean
    public PresentationDtoRendererResolver presentationDtoRendererResolver(OrganizationRenderer organizationRenderer,
                                                                           UserRenderer userRenderer,
                                                                           TicketRenderer ticketRenderer){
        Map<EntityType, PresentationDtoRenderer> map = new HashMap<>();
        map.put(EntityType.ORGANIZATION, organizationRenderer);
        map.put(EntityType.USER, userRenderer);
        map.put(EntityType.TICKET, ticketRenderer);
        return new PresentationDtoRendererResolver(map);
    }

}
