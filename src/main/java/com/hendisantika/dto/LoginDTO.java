package com.hendisantika.dto;

import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-keycloak-demo1
 * User: powercommerce
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 04/02/22
 * Time: 08.27
 * To change this template use File | Settings | File Templates.
 */
@Data
public class LoginDTO {
    private String username;
    private String password;
}
