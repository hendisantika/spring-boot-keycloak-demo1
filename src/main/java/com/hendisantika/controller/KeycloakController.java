package com.hendisantika.controller;

import com.hendisantika.dto.UserDTO;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-keycloak-demo1
 * User: powercommerce
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 21/12/21
 * Time: 12.46
 * To change this template use File | Settings | File Templates.
 */
@RestController
@RequestMapping("/keycloak/users")
public class KeycloakController {
    @Value("${keycloak.resource}")
    private String keycloakClient;

    @Value("${keycloak.auth-server-url}")
    private String keycloakUrl;

    @Value("${keycloak.realm}")
    private String keycloakRealm;

    @Value("${is.keycloak.admin.user}")
    private String keycloakAdminUser;

    @Value("${is.keycloak.admin.password}")
    private String keycloakAdminPassword;

    private Keycloak getKeycloakInstance() {
        return Keycloak.getInstance(
                keycloakUrl,
                "master",
                keycloakAdminUser,
                keycloakAdminPassword,
                "admin-cli");
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserDTO userDTO) {
        if (StringUtils.isEmpty(userDTO.getUsername()) || StringUtils.isEmpty(userDTO.getPassword())) {
            return ResponseEntity.badRequest().body("Empty username or password");
        }
        CredentialRepresentation credentials = new CredentialRepresentation();
        credentials.setType(CredentialRepresentation.PASSWORD);
        credentials.setValue(userDTO.getPassword());
        credentials.setTemporary(false);
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(userDTO.getUsername());
        userRepresentation.setEmail(userDTO.getEmail());
        userRepresentation.setFirstName(userDTO.getFirstName());
        userRepresentation.setLastName(userDTO.getLastName());

        userRepresentation.setCredentials(Arrays.asList(credentials));
        userRepresentation.setEnabled(true);
        Map<String, List<String>> attributes = new HashMap<>();
        attributes.put("description", Arrays.asList(userDTO.getDescription()));
        attributes.put("businessID", userDTO.getBusinessId());
        userRepresentation.setAttributes(attributes);

        userRepresentation.setRealmRoles(Arrays.asList("user"));
        Keycloak keycloak = getKeycloakInstance();

        Response result = keycloak.realm(keycloakRealm).users().create(userRepresentation);
        // Tambahan start
//        RealmResource realmResource = keycloak.realm(keycloakRealm);
//        UsersResource userResource = realmResource.users();
//        RoleRepresentation testerRealmRole = realmResource.roles().get("tester").toRepresentation();
//
//        String userId = result.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
//        userResource.get(userId).roles().realmLevel().add(Arrays.asList(testerRealmRole));
//
//        ClientRepresentation app1Client = realmResource.clients().findByClientId("app1").get(0);
//        RoleRepresentation userClientRole = realmResource.clients().get(app1Client.getId()).roles().get("user").toRepresentation();
//
//        userResource.get(userId).roles().clientLevel(app1Client.getId()).add(Arrays.asList(userClientRole));
        // Tambahan end

        return new ResponseEntity<>(HttpStatus.valueOf(result.getStatus()));
    }

    @GetMapping
    public List<UserRepresentation> getUsers() {
        Keycloak keycloak = getKeycloakInstance();
        List<UserRepresentation> userRepresentations = keycloak.realm(keycloakRealm).users().list();
        return userRepresentations;
    }

    @PutMapping
    public ResponseEntity<UserRepresentation> updateUserDescriptionAttribute(@RequestParam String username,
                                                                             @RequestParam String description) {
        Keycloak keycloak = getKeycloakInstance();
        Optional<UserRepresentation> user = keycloak.realm(keycloakRealm).users().search(username).stream()
                .filter(u -> u.getUsername().equals(username)).findFirst();
        if (user.isPresent()) {
            UserRepresentation userRepresentation = user.get();
            UserResource userResource = keycloak.realm(keycloakRealm).users().get(userRepresentation.getId());
            Map<String, List<String>> attributes = new HashMap<>();
            attributes.put("description", Arrays.asList(description));
            userRepresentation.setAttributes(attributes);
            userResource.update(userRepresentation);
            return ResponseEntity.ok().body(userRepresentation);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping
    public void deleteUser(String username) {
        Keycloak keycloak = getKeycloakInstance();
        UsersResource users = keycloak.realm(keycloakRealm).users();
        users.search(username).stream()
                .forEach(user -> keycloak.realm(keycloakRealm).users().delete(user.getId()));
    }

    @GetMapping("/roles")
    public ResponseEntity<List<RoleRepresentation>> getRoles() {
        Keycloak keycloak = getKeycloakInstance();
        ClientRepresentation clientRepresentation = keycloak.realm(keycloakRealm).clients().findByClientId(keycloakClient).get(0);
        List<RoleRepresentation> roles = keycloak.realm(keycloakRealm).clients().get(clientRepresentation.getId()).roles().list();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/roles-by-user")
    public ResponseEntity<List<RoleRepresentation>> getRolesByUser(@RequestParam String username) {
        Keycloak keycloak = getKeycloakInstance();
        Optional<UserRepresentation> user = keycloak.realm(keycloakRealm).users().search(username).stream()
                .filter(u -> u.getUsername().equals(username)).findFirst();
        if (user.isPresent()) {
            UserRepresentation userRepresentation = user.get();
            UserResource userResource = keycloak.realm(keycloakRealm).users().get(userRepresentation.getId());
            ClientRepresentation clientRepresentation = keycloak.realm(keycloakRealm).clients().findByClientId(keycloakClient).get(0);
            List<RoleRepresentation> roles = userResource.roles().clientLevel(clientRepresentation.getId()).listAll();
            return ResponseEntity.ok(roles);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

//    @GetMapping("/roles2")
//    public List<String> getAllRoles(){
//        List<String> availableRoles = keycloak
//                .realm(realm)
//                .roles()
//                .list()
//                .stream()
//                .map(role -> role.getName())
//                .collect(Collectors.toList());
//        return availableRoles;
//    }

}
