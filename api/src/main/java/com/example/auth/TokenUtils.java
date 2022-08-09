package com.example.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.utils.JsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Base64;

public final class TokenUtils {
    private static final String USER_ID_CLAIM = "user_id";
    private static final String APP_SECRET = "5!wjus1z)a&sjq-lh9h*g%gdma*v6l02tjlxnexs+j(8terme^";
    private static JWTVerifier verifier;

    static {
        initJWTVerifier();
    }

    private static void initJWTVerifier() {
        verifier = JWT.require(Algorithm.HMAC256(APP_SECRET)).build();
    }

    public static String generate(long userId) {
        JWTCreator.Builder builder = JWT.create()
            .withClaim(USER_ID_CLAIM, userId);
        return builder.sign(Algorithm.HMAC256(APP_SECRET));
    }

    public static TokenPayload verify(String token) throws JsonProcessingException {
        DecodedJWT decodedJWT = verifier.verify(token);
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payloadStr = new String(decoder.decode(decodedJWT.getPayload()));
        return JsonUtils.mapper.readValue(payloadStr, TokenPayload.class);
    }
}
