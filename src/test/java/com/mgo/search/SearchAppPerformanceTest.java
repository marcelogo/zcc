package com.mgo.search;

import com.mgo.search.model.User;
import com.mgo.search.reposiory.Repository;
import com.mgo.search.reposiory.entity.UserEntity;
import com.mgo.search.service.SearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false",
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false"
})
@Profile("performanceTest")
class SearchAppPerformanceTest {

    private static final int DATA_VOLUME = 100000;

    private Map<String, UserEntity> userSource;

    @Autowired
    private SearchService<User> userSearchService;

    @MockBean
    private Repository<UserEntity> userRepository;

    @BeforeEach
    void populateUserSource() {
        userSource = IntStream.range(0, DATA_VOLUME)
                .mapToObj(this::createUserEntity)
                .collect(Collectors.toMap(UserEntity::getId, Function.identity()));

        Mockito.when(userRepository.findAll()).thenReturn(userSource.values());

        when(userRepository.byId(anyString())).thenAnswer(invocation -> {
            String id = invocation.getArguments()[0].toString();
            return userSource.get(id);
        });
    }

    @Test
    private UserEntity createUserEntity(Integer id) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId("id" + id);
        userEntity.setName("name" + id);
        userEntity.setActive(true);
        userEntity.setCreatedAt("createdAt" + id);
        userEntity.setEmail("user" + id + "@mail.com");
        userEntity.setExternalId("externalId" + id);
        userEntity.setLastLoginAt("lastLoginAt" + id);
        userEntity.setLocale("en_AU");
        userEntity.setOrganizationId("62");
        userEntity.setAlias("alias" + id);
        userEntity.setPhone("phone" + id);
        userEntity.setTimezone("timezone" + id);
        userEntity.setTags(Collections.emptySet());

        return userEntity;
    }

    @Test
    void shouldBeAbleToHandle100kUserEntities() {
        IntStream.range(0, DATA_VOLUME).forEach(id -> {
            String searchId = "id" + id;
            Collection<User> results = userSearchService.search("_id", searchId);
            assertThat(results, hasSize(1));
            assertThat(results.iterator().next().getId(), is(searchId));
        });
    }

}