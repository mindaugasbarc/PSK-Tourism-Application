package com.tourism.psk.service.impl;

import com.tourism.psk.constants.SessionStatus;
import com.tourism.psk.exception.InvalidTokenException;
import com.tourism.psk.exception.SessionExpiredException;
import com.tourism.psk.model.Session;
import com.tourism.psk.repository.SessionRepository;
import com.tourism.psk.repository.UserRepository;
import com.tourism.psk.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@Service
public class SessionServiceImpl implements SessionService {
    private SessionRepository sessionRepository;
    private UserRepository userRepository;

    @Value("${token-length}")
    private int tokenLength;
    @Value("${session-duration}")
    private int sessionDuration;

    @Autowired
    public SessionServiceImpl(SessionRepository sessionRepository, UserRepository userRepository) {
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Session authenticate(String authHeader) {
        Optional<Session> session = sessionRepository.findByToken(authHeader);
        Date now = new Date(System.currentTimeMillis());
        if (!session.isPresent()) {
            throw new InvalidTokenException();
        }
        if (session.get().getStatus() == SessionStatus.ACTIVE && session.get().getExpires().after(now)) {
            session.get().setExpires(new Date(System.currentTimeMillis() + sessionDuration));
            return sessionRepository.save(session.get());
        }
        else {
            session.get().setStatus(SessionStatus.INACTIVE);
            sessionRepository.save(session.get());
            throw new SessionExpiredException();
        }
    }

    @Override
    public Session create(long userId) {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[tokenLength];
        random.nextBytes(bytes);
        Base64.Encoder encoder = Base64.getEncoder().withoutPadding();
        String token = encoder.encodeToString(bytes);
        Session session = new Session(
                token,
                SessionStatus.ACTIVE,
                new Date(),
                new Date(System.currentTimeMillis() + sessionDuration)
        );
        session.setUser(userRepository.findById(userId));
        return sessionRepository.save(session);
    }
}
