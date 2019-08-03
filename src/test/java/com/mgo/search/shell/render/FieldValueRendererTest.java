package com.mgo.search.shell.render;

import com.mgo.search.model.Organization;
import com.mgo.search.model.Ticket;
import com.mgo.search.model.User;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class FieldValueRendererTest {
    private static final long LONG_FIELD_VALUE = 9239121739217392L;
    private static final int INT_FIELD_VALUE = 135;
    private static final String STRING_FIELD_VALUE = "stringField";
    private static final String ORGANIZATION_NAME = "Zendesk";
    private static final String USER_NAME = "Michael Jackson";
    private static final String TICKET_SUBJECT = "Printer does not work";
    private Object mockField = null;
    private MockPresentationDto dto;

    private FieldValueRenderer renderer = new FieldValueRenderer();

    @BeforeEach
    void setUp(){
        populateMockDto();
    }

    @Test
    void shouldExtractStringPropertyValue() throws NoSuchFieldException {
        Field stringField = dto.getClass().getDeclaredField("stringField");

        assertThat(renderer.render(dto, stringField), is("stringField: " + STRING_FIELD_VALUE));
    }

    @Test
    void shouldExtractLongPropertyValue() throws NoSuchFieldException {
        Field longField = dto.getClass().getDeclaredField("longField");

        assertThat(renderer.render(dto, longField), is("longField: " + LONG_FIELD_VALUE));
    }

    @Test
    void shouldExtractIntegerPropertyValue() throws NoSuchFieldException {
        Field intField = dto.getClass().getDeclaredField("intField");

        assertThat(renderer.render(dto, intField), is("intField: " + INT_FIELD_VALUE));
    }

    @Test
    void shouldReturnTrueAsStringIfBooleanFieldIsTrue() throws NoSuchFieldException {
        Field booleanField = dto.getClass().getDeclaredField("booleanField");

        assertThat(renderer.render(dto, booleanField), is("booleanField: true"));
    }

    @Test
    void shouldReturnFalseAsStringIfBooleanFieldIsFalse() throws NoSuchFieldException {
        dto.setBooleanField(Boolean.FALSE);
        Field booleanField = dto.getClass().getDeclaredField("booleanField");

        assertThat(renderer.render(dto, booleanField), is("booleanField: false"));
    }

    @Test
    void shouldExtractCollectionPropertyValueAsConcatenatedString() throws NoSuchFieldException {
        Field collectionField = dto.getClass().getDeclaredField("collectionField");

        String expectedResult = StringUtils.join(Arrays.asList("collectionField:", STRING_FIELD_VALUE,
                String.valueOf(LONG_FIELD_VALUE), String.valueOf(INT_FIELD_VALUE), String.valueOf(Boolean.TRUE)), " ");


        assertThat(renderer.render(dto, collectionField), is(expectedResult));
    }

    @Test
    void shouldReturnEmptyStringIfFieldValueIsNull() throws NoSuchFieldException {
        Field nullableField = dto.getClass().getDeclaredField("nullableField");

        assertThat(renderer.render(dto, nullableField), is("nullableField: "));
    }

    @Test
    void shouldReturnOrganizationNameWhenFieldIsOfTypeOrganization() throws NoSuchFieldException {
        Field mockField =  dto.getClass().getDeclaredField("organization");

        assertThat(renderer.render(dto, mockField), is("organization: " + ORGANIZATION_NAME));
    }

    @Test
    void shouldReturnUserNameWhenFieldIsOfTypeUser() throws NoSuchFieldException {
        Field mockField =  dto.getClass().getDeclaredField("user");

        assertThat(renderer.render(dto, mockField), is("user: " + USER_NAME));
    }

    @Test
    void shouldReturnTicketSubjectWhenFieldIsOfTypeTicket() throws NoSuchFieldException {
        Field mockField =  dto.getClass().getDeclaredField("ticket");

        assertThat(renderer.render(dto, mockField), is("ticket: " + TICKET_SUBJECT));
    }

    @Test
    void shouldReturnEmptyStringIfFieldValueCantBeAccessed() throws NoSuchFieldException {
        Field mockField = this.getClass().getDeclaredField("mockField");

        assertThat(renderer.render(dto, mockField), is("mockField: "));
    }



    private void populateMockDto() {
        dto = new MockPresentationDto();
        dto.setLongField(LONG_FIELD_VALUE);
        dto.setIntField(INT_FIELD_VALUE);
        dto.setStringField(STRING_FIELD_VALUE);
        dto.setBooleanField(Boolean.TRUE);
        dto.setCollectionField(Arrays.asList(STRING_FIELD_VALUE, LONG_FIELD_VALUE, INT_FIELD_VALUE, Boolean.TRUE));
        dto.setNullableField(null);

        Organization org = new Organization();
        org.setName(ORGANIZATION_NAME);
        dto.setOrganization(org);

        User user = new User();
        user.setName(USER_NAME);
        dto.setUser(user);

        Ticket ticket = new Ticket();
        ticket.setSubject(TICKET_SUBJECT);
        dto.setTicket(ticket);
    }
}