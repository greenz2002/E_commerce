package com.example.demo.service;

import com.example.demo.dto.request.AuthenicationRequest;
import com.example.demo.dto.request.UserCreateRequest;
import com.example.demo.dto.request.UserUpdateRequest;
import com.example.demo.dto.request.introspecRequest;
import com.example.demo.dto.response.AuthenticationResponse;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.dto.response.introspecResponse;
import com.example.demo.entity.User;
import com.example.demo.exception.AppException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.maper.UserMaper;
import com.example.demo.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMaper userMaper;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGN_KEY;

    PasswordEncoder encoder = new BCryptPasswordEncoder(10);

    public List<User> getAll() {
        return userRepository.findAll();
    }

    @PostAuthorize("returnObject.id == authentication.name")
    public UserResponse getByID(Long id) {
        return userMaper.userResponse(userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("User not found")));
    }

    public User createUser(UserCreateRequest request) {
//        User user = new User();
        if (userRepository.existsUserByUsername(request.getUsername()))
            throw new AppException(ErrorCode.USER_EXITS);
        User user = userMaper.toUser(request);
        user.setPassword(encoder.encode(request.getPassword()));
//        user.setUsername(request.getUsername());
//        user.setPassword(request.getPassword());
//        user.setEmail(request.getEmail());

        return userRepository.save(user);
    }

    public UserResponse updateUser(UserUpdateRequest request, Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        userMaper.updateUser(user, request);
        user.setPassword(encoder.encode(request.getPassword()));
//        user.setUsername(request.getUsername());
//        user.setPassword(request.getPassword());
//        user.setEmail(request.getEmail());
//        user.setUpdated_at(LocalDateTime.now());

        return userMaper.userResponse(userRepository.save(user));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public AuthenticationResponse authenticate(AuthenicationRequest request) {
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXITS));
        boolean authentication = encoder.matches(request.getPassword(), user.getPassword());

        if (!authentication) throw new AppException(ErrorCode.UNCCATEGORIZEd);
        var token = generraterToken(request.getUsername());

        return AuthenticationResponse.builder()
                .token(token)
                .authentication(true)
                .build();
    }

    private String generraterToken(String username) {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(username)
                .issuer("thang.com")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now()
                        .plus(1, ChronoUnit.HOURS).toEpochMilli()))
                .claim("customClaim", "Custom")
                .build();
        Payload payload = new Payload(claimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);

        try {
            jwsObject.sign(new MACSigner(SIGN_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    public introspecResponse introspec(introspecRequest request) throws JOSEException, ParseException {
        var token = request.getToken();
        JWSVerifier jwsVerifier = new MACVerifier(SIGN_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(jwsVerifier);
        return introspecResponse.builder()
                .valid(verified && expirationTime.after(new Date()))
                .build();
    }
}
