package com.example.gradebackend.util;

import com.example.gradebackend.model.dto.UserDTO;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class KeycloakUtils {

    private static Keycloak keycloak;
    private static RealmResource realmResource;
    private static UsersResource usersResource;

    @Value("${keycloak.auth-server-url}")
    private String serverURL;
    @Value("${keycloak.realm}")
    private String realm;
    @Value("${keycloak.resource}")
    private String clientID;
    @Value("${keycloak.credentials.secret}")
    private String clientSecret;

    @PostConstruct
    public Keycloak initKeycloak() {
        if (keycloak == null) {

            keycloak = KeycloakBuilder.builder()
                    .realm(realm)
                    .serverUrl(serverURL)
                    .clientId(clientID)
                    .clientSecret(clientSecret)
                    .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                    .build();

            realmResource = keycloak.realm(realm);
            usersResource = realmResource.users();
        }
        return keycloak;
    }

    public Response createKeycloakUser(UserDTO user) {
        CredentialRepresentation credentialRepresentation = createPasswordCredentials(user.getPassword());

        UserRepresentation kcUser = new UserRepresentation();
        kcUser.setUsername(user.getUsername());
        kcUser.setCredentials(Collections.singletonList(credentialRepresentation));
        kcUser.setEmail(user.getEmail());
        kcUser.setEnabled(true);
        kcUser.setEmailVerified(true);

        Response response = usersResource.create(kcUser);

        return response;
    }

    private CredentialRepresentation createPasswordCredentials(String password) {
        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setTemporary(true);
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(password);
        return passwordCredentials;
    }

    public void deleteKeycloakUser(String userId) {
        UserResource uniqueUserResource = usersResource.get(userId);
        uniqueUserResource.remove();
    }

    public void updateKeycloakUser(UserDTO userDTO) {
        CredentialRepresentation credentialRepresentation = createPasswordCredentials(userDTO.getPassword());

        UserRepresentation kcUser = new UserRepresentation();
        kcUser.setUsername(userDTO.getUsername());
        kcUser.setCredentials(Collections.singletonList(credentialRepresentation));
        kcUser.setEmail(userDTO.getEmail());

        UserResource uniqueUserResource = usersResource.get(userDTO.getId());
        uniqueUserResource.update(kcUser);
    }

    public UserRepresentation findUserById(String userId) {
        return usersResource.get(userId).toRepresentation();
    }

    public List<UserRepresentation> findAll() {
        var users = usersResource.list();
        return users;
    }

    public List<UserRepresentation> searchKeycloakUsers(String text) {
        return usersResource.searchByAttributes(text);

    }

    public void addRoles(String userId, List<String> roles) {
        List<RoleRepresentation> kcRoles = new ArrayList<>();

        for (String role : roles) {
            RoleRepresentation roleRep = realmResource.roles().get(role).toRepresentation();
            kcRoles.add(roleRep);
        }

        UserResource uniqueUserResource = usersResource.get(userId);
        uniqueUserResource.roles().realmLevel().add(kcRoles);
    }
}