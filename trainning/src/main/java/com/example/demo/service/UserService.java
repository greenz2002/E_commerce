package com.example.demo.service;

import com.example.demo.dto.request.AuthenicationRequest;
import com.example.demo.dto.request.UserCreateRequest;
import com.example.demo.dto.request.UserUpdateRequest;
import com.example.demo.dto.request.introspecRequest;
import com.example.demo.dto.response.AuthenticationResponse;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.dto.response.introspecResponse;
import com.example.demo.entity.Users;
import com.example.demo.enums.RolesEnum;
import com.example.demo.exception.AppException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.maper.UserMaper;
import com.example.demo.repository.RolesRepository;
import com.example.demo.repository.UserRepository;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;


@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private UserMaper userMaper;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGN_KEY;

    PasswordEncoder encoder = new BCryptPasswordEncoder(10);

    public List<Users> getAll() {
        return userRepository.findAll();
    }

    public List<UserResponse> getUsers() {
        return userRepository.findAll().stream().map(userMaper::userResponse).toList();
    }

//    @PostAuthorize("returnObject.id == authentication.name")
    public UserResponse getByID(Long id) {
        return userMaper.userResponse(userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Users not found")));
    }
    public UserResponse getMyinfo(){
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        Users users = userRepository.findByUsername(name).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXITS));
        return userMaper.userResponse(users);
    }

    public Users createUser(UserCreateRequest request) {;
        if (userRepository.existsUserByUsername(request.getUsername()))
            throw new AppException(ErrorCode.USER_EXITS);
        Users users = userMaper.toUser(request);
        users.setPassword(encoder.encode(request.getPassword()));

        HashSet<String> roles = new HashSet<>();
        roles.add(RolesEnum.USER.name());
        users.setRoles(roles);

        return userRepository.save(users);
    }

    public UserResponse updateUser(UserUpdateRequest request, Long id) {
        Users users = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Users not found"));
        userMaper.updateUser(users, request);
        users.setPassword(encoder.encode(request.getPassword()));


        HashSet<String> roles = new HashSet<>();
        roles.add(RolesEnum.USER.name());
        users.setRoles((Set<String>) roles);

        return userMaper.userResponse(userRepository.save(users));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public AuthenticationResponse authenticate(AuthenicationRequest request) {
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXITS));
        boolean authentication = encoder.matches(request.getPassword(), user.getPassword());

        if (!authentication) throw new AppException(ErrorCode.UNCCATEGORIZEd);
        var token = generraterToken(user);

        return AuthenticationResponse.builder()
                .token(token)
                .authentication(true)
                .build();
    }

    private String generraterToken(Users users) {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(users.getUsername())
                .issuer("thang.com")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now()
                        .plus(1, ChronoUnit.HOURS).toEpochMilli()))
                .claim("scope", buildScope(users))
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
    private String buildScope(Users users){
        StringJoiner stringJoiner = new StringJoiner(" ");
        if (!CollectionUtils.isEmpty(users.getRoles()))
            users.getRoles().forEach(stringJoiner::add);
        return stringJoiner.toString();
    }
}
