package com.rahulpateldev.service;

import com.rahulpateldev.config.YMLPropertyReader;
import com.rahulpateldev.exception.custom.TokenExpiredException;
import com.rahulpateldev.model.entity.RefreshToken;
import com.rahulpateldev.model.entity.User;
import com.rahulpateldev.repository.RefreshTokenRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class JwtTokenService {


    private final JwtEncoder jwtEncoder;
    private final YMLPropertyReader reader;
    private final RefreshTokenRepository refreshTokenRepository;

    public JwtTokenService(YMLPropertyReader reader,
                           JwtEncoder jwtEncoder,
                           RefreshTokenRepository refreshTokenRepository) {
        this.reader = reader;
        this.jwtEncoder = jwtEncoder;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public String createToken(User user) {
        var claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now()
                        .plus(this.reader.getJwtAccessTokenExpiration(),
                                ChronoUnit.MINUTES))
                .subject(user.getUsername())
                .claim("scope", this.createScope(user))
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claims))
                .getTokenValue();
    }

    private String createScope(User user) {
        return user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken createRefreshToken(User user) {
        Optional<RefreshToken> fetchExistingToken = this.refreshTokenRepository.findByUser(user);
        RefreshToken refreshToken = null;
        refreshToken = fetchExistingToken.orElseGet(RefreshToken::new);
        refreshToken.setUser(user);
        refreshToken.setExpiryDate(Instant.now()
                .plus(this.reader.getJwtRefreshTokenExpiration(),
                        ChronoUnit.DAYS));
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenExpiredException(token.getToken(),
                    "Refresh token was expired. Please make a new sign in request");
        }
        return token;
    }

}
