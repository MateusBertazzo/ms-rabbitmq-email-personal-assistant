package com.ms.email.sevices;

import org.springframework.stereotype.Service;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.token.KeyBasedPersistenceTokenService;
import org.springframework.security.core.token.SecureRandomFactoryBean;
import org.springframework.security.core.token.Token;

@Service
public class TokenService {

    @Value("${secret}")
    private String secret;

    @SneakyThrows
    public String generateTokenToResetPassword(String email) {
        KeyBasedPersistenceTokenService tokenService = new KeyBasedPersistenceTokenService();

        tokenService.setServerSecret(secret);
        tokenService.setServerInteger(3);
        tokenService.setSecureRandom(new SecureRandomFactoryBean().getObject());

        Token token = tokenService.allocateToken(email);

        return token.getKey();
    }
}
