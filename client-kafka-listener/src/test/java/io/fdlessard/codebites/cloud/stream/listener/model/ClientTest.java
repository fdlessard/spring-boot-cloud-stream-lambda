package io.fdlessard.codebites.cloud.stream.listener.model;

import com.fasterxml.jackson.databind.JsonNode;
import io.fdlessard.codebites.cloud.stream.listener.TestUtils;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClientTest {

    public static final String TEST_ID_STR = "id";
    public static final String TEST_FIRST_NAME_STR = "firstName";
    public static final String TEST_LAST_NAME_STR = "lastName";
    public static final String TEST_COMPANY_STR = "company";

    private static ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
    private static Validator validator = vf.getValidator();

    @Test
    void clientMarshallingToJson() {
        Client client = buildClient();
        JsonNode node = TestUtils.getObjectMapper().convertValue(client, JsonNode.class);
        assertClientNode(client, node);
    }

    @Test
    void clientUnMarshallingFromJson() throws IOException {

        Client client = TestUtils
                .readFileIntoPojo("/Client.json", Client.class);
        assertClient(client);
    }

    @Test
    void validateLastNameNotBlank() {

        Client client = buildClient();
        client.setLastName(null);

        Set<ConstraintViolation<Client>> violationSet = validator
                .validate(client, Default.class);

        long violationsCount = TestUtils.getViolationsCount(violationSet,
                "lastName name cannot be blank", TEST_LAST_NAME_STR);
        assertEquals(1, violationsCount);

        client.setFirstName(RandomStringUtils.random(3, StringUtils.SPACE));

        violationSet = validator.validate(client, Default.class);
        violationsCount = TestUtils.getViolationsCount(violationSet,
                "lastName name cannot be blank", TEST_LAST_NAME_STR);

        assertEquals(1, violationsCount);
    }

    @Test
    void validateLastNameMinSize() {

        Client client = buildClient();
        client.setLastName("a");

        Set<ConstraintViolation<Client>> violationSet = validator
                .validate(client, Default.class);

        long violationsCount = TestUtils.getViolationsCount(violationSet,
                "lastName must have more thant 2 characters", TEST_LAST_NAME_STR);
        assertEquals(1, violationsCount);
    }

    @Test
    void validateFirstNameNotBlank() {

        Client client = buildClient();
        client.setFirstName(null);

        Set<ConstraintViolation<Client>> violationSet = validator
                .validate(client, Default.class);

        long violationsCount = TestUtils.getViolationsCount(violationSet,
                "firstName name cannot be blank", TEST_FIRST_NAME_STR);
        assertEquals(1, violationsCount);

        client.setFirstName(RandomStringUtils.random(3, StringUtils.SPACE));

        violationSet = validator.validate(client, Default.class);
        violationsCount = TestUtils.getViolationsCount(violationSet,
                "firstName name cannot be blank", TEST_FIRST_NAME_STR);

        assertEquals(1, violationsCount);
    }

    @Test
    void validateFirstNameMinSize() {

        Client client = buildClient();
        client.setFirstName("a");

        Set<ConstraintViolation<Client>> violationSet = validator
                .validate(client, Default.class);

        long violationsCount = TestUtils.getViolationsCount(violationSet,
                "firstName must have more thant 2 characters", TEST_FIRST_NAME_STR);
        assertEquals(1, violationsCount);
    }

    @Test
    void validateCompanyNotBlank() {

        Client client = buildClient();
        client.setCompany(null);

        Set<ConstraintViolation<Client>> violationSet = validator
                .validate(client, Default.class);

        long violationsCount = TestUtils.getViolationsCount(violationSet,
                "company name cannot be blank", TEST_COMPANY_STR);
        assertEquals(1, violationsCount);

        client.setFirstName(RandomStringUtils.random(3, StringUtils.SPACE));

        violationSet = validator.validate(client, Default.class);
        violationsCount = TestUtils.getViolationsCount(violationSet,
                "company name cannot be blank", TEST_COMPANY_STR);

        assertEquals(1, violationsCount);
    }

    @Test
    void validateCompanyMinSize() {

        Client client = buildClient();
        client.setCompany("a");

        Set<ConstraintViolation<Client>> violationSet = validator
                .validate(client, Default.class);

        long violationsCount = TestUtils.getViolationsCount(violationSet,
                "company must have more thant 2 characters", TEST_COMPANY_STR);
        assertEquals(1, violationsCount);
    }

    @Test
    void equalsContract() {

        EqualsVerifier.forClass(Client.class)
                .withRedefinedSuperclass()
                .suppress(Warning.STRICT_INHERITANCE)
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();

        Client client1 = buildClient();
        Client client2 = new Client();
        client2.setId(0l);
        client2.setFirstName(TEST_FIRST_NAME_STR);
        client2.setLastName(TEST_LAST_NAME_STR);
        client2.setCompany(TEST_COMPANY_STR);
        assertEquals(client1, client2);
        assertEquals(client1.toString(), client2.toString());
    }

    public static void assertClientNode(Client client, JsonNode node) {
        assertEquals(client.getId(), node.get(TEST_ID_STR).asInt());
        assertEquals(client.getLastName(), node.get(TEST_LAST_NAME_STR).asText());
        assertEquals(client.getFirstName(), node.get(TEST_FIRST_NAME_STR).asText());
        assertEquals(client.getCompany(), node.get(TEST_COMPANY_STR).asText());
    }

    public static void assertClient(Client client) {
        //assertEquals(0l, customer.getId());
        assertEquals(TEST_LAST_NAME_STR, client.getLastName());
        assertEquals(TEST_FIRST_NAME_STR, client.getFirstName());
        assertEquals(TEST_COMPANY_STR, client.getCompany());
    }

    public static Client buildClient() {
        return Client.builder()
                .id(0l)
                .firstName(TEST_FIRST_NAME_STR)
                .lastName(TEST_LAST_NAME_STR)
                .company(TEST_COMPANY_STR)
                .build();
    }

    public static List<Client> buildClients() {

        Client c = buildClient();
        c.setId(1l);

        return Arrays.asList(Client.builder()
                        .id(0l)
                        .firstName(TEST_FIRST_NAME_STR)
                        .lastName(TEST_LAST_NAME_STR)
                        .company(TEST_COMPANY_STR)
                        .build(),
                c
        );
    }
}
