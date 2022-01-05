package com.hendisantika.dto;

import lombok.Data;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-keycloak-demo1
 * User: powercommerce
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 21/12/21
 * Time: 13.11
 * To change this template use File | Settings | File Templates.
 */
@Data
public class UserDTO {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String description;
    private List<String> businessId;
    private String role;
}
