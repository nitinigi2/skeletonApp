package org.example.steps;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RealmRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Testcontainers
public abstract class AbstractTestcontainers {

    private static final GenericContainer<?> keycloakContainer = new GenericContainer<>("quay.io/keycloak/keycloak:24.0.5");

    protected static Keycloak keycloakSimpleApi;

    @DynamicPropertySource
    private static void dynamicProperties(DynamicPropertyRegistry registry) {
        keycloakContainer.withExposedPorts(8080)
                .withEnv("KEYCLOAK_ADMIN", "admin")
                .withEnv("KEYCLOAK_ADMIN_PASSWORD", "admin")
                .withEnv("KC_DB", "dev-mem")
                .withCommand("start-dev")
                .waitingFor(Wait.forHttp("/admin").forPort(8080).withStartupTimeout(Duration.ofMinutes(2)))
                .start();

        String keycloakHost = keycloakContainer.getHost();
        Integer keycloakPort = keycloakContainer.getMappedPort(8080);

        String issuerUri = String.format("http://%s:%s/realms/%s", keycloakHost, keycloakPort, MY_REALM_NAME);
        registry.add("spring.security.oauth2.resourceserver.jwt.issuer-uri", () -> issuerUri);

        if (keycloakSimpleApi == null) {
            String keycloakServerUrl = String.format("http://%s:%s", keycloakHost, keycloakPort);
            setupKeycloak(keycloakServerUrl);
        }
    }

    private static void setupKeycloak(String keycloakServerUrl) {
        Keycloak keycloakAdmin = KeycloakBuilder.builder()
                .serverUrl(keycloakServerUrl)
                .realm("master")
                .username("admin")
                .password("admin")
                .clientId("admin-cli")
                .build();

        // Realm
        RealmRepresentation realmRepresentation = new RealmRepresentation();
        realmRepresentation.setRealm(MY_REALM_NAME);
        realmRepresentation.setEnabled(true);
        realmRepresentation.setRequiredActions(List.of());

        // Client
        ClientRepresentation clientRepresentation = new ClientRepresentation();
        clientRepresentation.setId(SIMPLE_API_CLIENT_ID);
        clientRepresentation.setDirectAccessGrantsEnabled(true);
        clientRepresentation.setSecret(SIMPLE_API_CLIENT_SECRET);
        realmRepresentation.setClients(Collections.singletonList(clientRepresentation));

        // Client roles
        Map<String, List<String>> clientRoles = new HashMap<>();
        clientRoles.put(SIMPLE_API_CLIENT_ID, SIMPLE_API_ROLES);

        // Credentials
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(USER_PASSWORD);

        // User
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(USER_USERNAME);
        userRepresentation.setEnabled(true);
        userRepresentation.setCredentials(Collections.singletonList(credentialRepresentation));
        userRepresentation.setClientRoles(clientRoles);
        realmRepresentation.setUsers(Collections.singletonList(userRepresentation));

        keycloakAdmin.realms().create(realmRepresentation);

        keycloakSimpleApi = KeycloakBuilder.builder()
                .serverUrl(keycloakServerUrl)
                .realm(MY_REALM_NAME)
                .username(USER_USERNAME)
                .password(USER_PASSWORD)
                .clientId(SIMPLE_API_CLIENT_ID)
                .clientSecret(SIMPLE_API_CLIENT_SECRET)
                .build();
    }

    private static final String MY_REALM_NAME = "my-realm";
    private static final String SIMPLE_API_CLIENT_ID = "simple-api";
    private static final String SIMPLE_API_CLIENT_SECRET = "abc123";
    private static final List<String> SIMPLE_API_ROLES = Collections.singletonList("SIMPLE-API-USER");
    private static final String USER_USERNAME = "user.test";
    private static final String USER_PASSWORD = "123";
}
