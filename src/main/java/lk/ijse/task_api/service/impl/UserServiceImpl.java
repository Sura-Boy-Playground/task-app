package lk.ijse.task_api.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.task_api.entity.User;
import lk.ijse.task_api.repository.UserRepository;
import lk.ijse.task_api.service.UserService;
import lk.ijse.task_api.service.exception.ServiceException;
import lk.ijse.task_api.to.UserTo;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;

    public UserServiceImpl(UserRepository userRepository, ModelMapper mapper, PasswordEncoder passwordEncoder, JwtEncoder jwtEncoder) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoder = jwtEncoder;
    }

    @Override
    public void signUp(UserTo userTo) {
        User userEntity = mapper.map(userTo, User.class);
        if (userRepository.existsById(userEntity.getUsername())) {
            throw new ServiceException(ServiceException.Type.DUPLICATE, "Username already exists");
        }
        userEntity.setPassword(passwordEncoder.encode(userTo.getPassword()));
        userRepository.save(userEntity);
    }

    @Override
    public String signIn(UserTo userTo) {
        User userEntity = userRepository.findById(userTo.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User does not exist"));
        log.debug("DB Hashed: {}", userEntity.getPassword());
        log.debug("Provided Hash: {}", passwordEncoder.encode(userTo.getPassword()));
        if (!passwordEncoder.matches(userTo.getPassword(), userEntity.getPassword())) {
            throw new BadCredentialsException("Bad login credentials");
        }
        Instant now = Instant.now();
        JwsHeader header = JwsHeader.with(() -> "HS256").build();
        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .subject(userEntity.getUsername())
                .claim("name", userEntity.getDisplayName())
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .build();
        JwtEncoderParameters parameters = JwtEncoderParameters.from(header, claimsSet);
        return jwtEncoder.encode(parameters).getTokenValue();
    }
}
