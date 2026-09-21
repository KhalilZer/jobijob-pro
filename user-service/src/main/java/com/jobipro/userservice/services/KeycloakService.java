package com.jobipro.userservice.services;

import com.jobipro.common.UserRole;
import com.jobipro.common.exceptions.ResourceAlreadyExist;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KeycloakService {

    @Value("${keycloak.admin.realm}")
    private String realm;
    private final Keycloak keycloak;

    public String createUser(
            String email,
            String firstName,
            String lastName
    ) {

        UserRepresentation user = new UserRepresentation();

        user.setUsername(email);
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEnabled(true);
        user.setEmailVerified(false);

        UsersResource usersResource =
                keycloak.realm(realm).users();

        try (Response response = usersResource.create(user)) {

            if (response.getStatus() == 409) {
                throw new ResourceAlreadyExist(
                        "A user with this email already exists"
                );
            }
            if (response.getStatus() != 201) {
                throw new RuntimeException(
                        "Failed to create Keycloak user. Status: "
                                + response.getStatus()
                );
            }

            String location = response.getLocation().getPath();

            return location.substring(
                    location.lastIndexOf('/') + 1
            );
        }
    }

    public void assignRole(
            String keycloakId,
            UserRole role
    ) {

        RealmResource realmResource =
                keycloak.realm(realm);

        RoleRepresentation roleRepresentation =
                realmResource
                        .roles()
                        .get(role.name())
                        .toRepresentation();

        realmResource
                .users()
                .get(keycloakId)
                .roles()
                .realmLevel()
                .add(List.of(roleRepresentation));
    }
}