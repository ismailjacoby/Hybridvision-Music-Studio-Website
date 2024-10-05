package be.technobel.makerhub_backend.pl.config.security;

import be.technobel.makerhub_backend.dal.models.entities.UserEntity;
import be.technobel.makerhub_backend.dal.models.enums.UserRole;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import com.auth0.jwt.JWT;

import java.time.Instant;

@Component
public class JWTProvider {
    private static final String JWT_SECRET = "idNwzvdSnUgGrBjIzHLGuyyLFvb2Hpep16uONqMQvc2w5Hh5lf";
    private static final long EXPIRES_AT = 900_000;
    private static final String AUTH_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";

    private final UserDetailsService userDetailsService;

    public JWTProvider(UserDetailsService userDetailsService){
        this.userDetailsService = userDetailsService;
    }

    public String generateToken(String username, UserRole role){
        return TOKEN_PREFIX + JWT.create()
                .withSubject(username)
                .withClaim("role", role.toString())
                .withExpiresAt(Instant.now().plusMillis(EXPIRES_AT))
                .sign(Algorithm.HMAC512(JWT_SECRET));
    }

    public String extractToken(HttpServletRequest request){
        String header = request.getHeader(AUTH_HEADER);

        if(header == null || !header.startsWith(TOKEN_PREFIX)){
            return null;
        }

        return header.replaceFirst(TOKEN_PREFIX,"");
    }

    public boolean validateToken(String token){
        try{
            DecodedJWT jwt = JWT.require(Algorithm.HMAC512(JWT_SECRET))
                    .acceptExpiresAt(EXPIRES_AT)
                    .withClaimPresence("sub")
                    .withClaimPresence("role")
                    .build()
                    .verify(token);

            String username = jwt.getSubject();
            UserEntity user = (UserEntity) userDetailsService.loadUserByUsername(username);
            if(!user.isEnabled()){
                return false;
            }

            UserRole tokenRole = UserRole.valueOf(jwt.getClaim("role").asString());

            return user.getRole() == tokenRole;
        } catch (JWTVerificationException | UsernameNotFoundException ex){
            return false;
        }
    }

    public Authentication createAuthentication(String token){
        DecodedJWT jwt = JWT.decode(token);
        String username = jwt.getSubject();
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        return new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(),null,userDetails.getAuthorities()
        );
    }

}
