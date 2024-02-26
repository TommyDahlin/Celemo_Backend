package sidkbk.celemo.security.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import sidkbk.celemo.security.services.UserDetailsImpl;

public class JwtUtils {
    // Logger och LoggerFactory är från slf4j
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwtExpirationMs}")
    private int jwtExpriationMs;

    @Value("${celemo.app.jwtCookieName}")
    private String jwtCookie;

    public ResponseCookie generateJwtCookie(UserDetailsImpl userPrincipal) {
        String jwt = generateTokenFromUsername(userPrincipal.getUsername())
    }







}
