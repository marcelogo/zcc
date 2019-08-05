package com.mgo.search.reposiory.reader;

import com.mgo.search.reposiory.entity.Entity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsMapWithSize.aMapWithSize;

class JsonReaderTest {

    private final ClassPathResource resourceData = new ClassPathResource("jsonReaderMockData.json");
    private static final String ENTITY1_ID = "1";
    private static final String ENTITY1_NAME = "one";
    private static final String ENTITY2_ID = "2";
    private static final String ENTITY2_NAME = "two";
    private static final String ENTITY3_ID = "3";
    private static final String ENTITY3_NAME = "three";

    @Test
    void shouldReadAllEntitiesFromJson() throws IOException {
        Map<String, MockEntity> read = new JsonReader().read(resourceData.getInputStream(), MockEntity.class);

        assertThat(read, aMapWithSize(3));
    }

    @Test
    void shouldCreateIndexReadEntitiesById() throws IOException {
        Map<String, MockEntity> read = new JsonReader().read(resourceData.getInputStream(), MockEntity.class);

        assertThat(read.get(ENTITY1_ID), org.hamcrest.core.Is.is(new MockEntity(ENTITY1_ID, ENTITY1_NAME)));
        assertThat(read.get(ENTITY2_ID), org.hamcrest.core.Is.is(new MockEntity(ENTITY2_ID, ENTITY2_NAME)));
        assertThat(read.get(ENTITY3_ID), org.hamcrest.core.Is.is(new MockEntity(ENTITY3_ID, ENTITY3_NAME)));

    }

    private class MockEntity implements Entity {

        private String id;
        private String name;

        MockEntity(String id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public String getId() {
            return id;
        }

        String getName() {
            return name;
        }

        @Override
        public boolean equals(Object o) {
            return EqualsBuilder.reflectionEquals(this, o);
        }

        @Override
        public int hashCode() {
            return HashCodeBuilder.reflectionHashCode(this);
        }

        @Override
        public String toString() {
            return "MockEntity{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}