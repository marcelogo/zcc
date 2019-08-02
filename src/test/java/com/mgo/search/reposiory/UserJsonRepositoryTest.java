package com.mgo.search.reposiory;

import com.mgo.search.reposiory.reader.JsonReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserJsonRepositoryTest {

    private UserJsonRepository userRepo;

    private static String ENTITY_ID = "1";

    @BeforeEach
    void setUp() throws IOException {
        userRepo = new UserJsonRepository("userMockData.json", new JsonReader());
    }

    @Test
    void shouldFindAll() {
        assertThat(userRepo.findAll(), hasSize(5));
    }

    @Test
    void shouldReadUserId(){
        assertThat(userRepo.byId(ENTITY_ID).getId(), is(ENTITY_ID));
    }

    @Test
    void shouldReadUserURL(){
        assertThat(userRepo.byId(ENTITY_ID).getUrl(), is("http://initech.zendesk.com/api/v2/users/1.json"));
    }

    @Test
    void shouldReadUserExternalId(){
        assertThat(userRepo.byId(ENTITY_ID).getExternalId(), is("74341f74-9c79-49d5-9611-87ef9b6eb75f"));
    }

    @Test
    void shouldReadUserName(){
        assertThat(userRepo.byId(ENTITY_ID).getName(), is("Francisca Rasmussen"));
    }

    @Test
    void shouldReadUserAlias(){
        assertThat(userRepo.byId(ENTITY_ID).getAlias(), is( "Miss Coffey"));
    }

    @Test
    void shouldReadUserCreateAt(){
        assertThat(userRepo.byId(ENTITY_ID).getCreatedAt(), is( "2016-04-15T05:19:46 -10:00"));
    }

    @Test
    void shouldReadUserActive(){
        assertTrue(userRepo.byId(ENTITY_ID).isActive());
    }

    @Test
    void shouldReadUserVerified(){
        assertTrue(userRepo.byId(ENTITY_ID).isVerified());
    }

    @Test
    void shouldReadUserShared(){
        assertTrue(userRepo.byId(ENTITY_ID).isShared());
    }

    @Test
    void shouldReadUserLocale(){
        assertThat(userRepo.byId(ENTITY_ID).getLocale(), is( "en-AU"));
    }

    @Test
    void shouldReadUserTimezone(){
        assertThat(userRepo.byId(ENTITY_ID).getTimezone(), is( "Sri Lanka"));
    }
    
    @Test
    void shouldReadUserLastLoginAt(){
        assertThat(userRepo.byId(ENTITY_ID).getLastLoginAt(), is( "2013-08-04T01:03:27 -10:00"));
    }

    @Test
    void shouldReadUserEmail(){
        assertThat(userRepo.byId(ENTITY_ID).getEmail(), is( "coffeyrasmussen@flotonic.com"));
    }

    @Test
    void shouldReadUserPhone(){
        assertThat(userRepo.byId(ENTITY_ID).getPhone(), is( "8335-422-718"));
    }

    @Test
    void shouldReadUserSignature(){
        assertThat(userRepo.byId(ENTITY_ID).getSignature(), is( "Don't Worry Be Happy!"));
    }

    @Test
    void shouldReadUserOrganizationId(){
        assertThat(userRepo.byId(ENTITY_ID).getOrganizationId(), is( "119"));
    }

    @Test
    void shouldReadUserTags(){
        Set<String> tags = userRepo.byId(ENTITY_ID).getTags();

        assertThat(tags, hasSize(4));
        assertThat(tags, hasItems( "Springville", "Sutton", "Hartsville/Hartley", "Diaperville"));
    }

    @Test
    void shouldReadUserSuspended(){
        assertTrue(userRepo.byId(ENTITY_ID).isSuspended());
    }

    @Test
    void shouldReadUserRole(){
        assertThat(userRepo.byId(ENTITY_ID).getRole(), is( "admin"));
    }

}