package com.tourism.psk.service.impl;

import com.tourism.psk.constants.SessionStatus;
import com.tourism.psk.exception.InvalidTokenException;
import com.tourism.psk.exception.SessionExpiredException;
import com.tourism.psk.model.Session;
import com.tourism.psk.model.User;
import com.tourism.psk.repository.SessionRepository;
import com.tourism.psk.repository.UserRepository;
import com.tourism.psk.service.SessionService;
import org.h2.util.DateTimeUtils;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

@Service
public class SessionServiceImpl implements SessionService {
    private SessionRepository sessionRepository;
    private UserRepository userRepository;

    @Autowired
    public SessionServiceImpl(SessionRepository sessionRepository, UserRepository userRepository) {
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Session authenticate(String token) {
        Session session = sessionRepository.findByToken(token);
        Date now = new Date(System.currentTimeMillis());
        if (session == null) {
            throw new InvalidTokenException();
        }
        if (session.getStatus() == SessionStatus.ACTIVE && session.getExpires().after(now)) {
            session.setExpires(new Date(System.currentTimeMillis() + 3 * 60 * 1000));
            return sessionRepository.save(session);
        }
        else {
            session.setStatus(SessionStatus.INACTIVE);
            sessionRepository.save(session);
            throw new SessionExpiredException();
        }
    }

    @Override
    public Session create(long userId) {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[32];
        random.nextBytes(bytes);
        Base64.Encoder encoder = Base64.getEncoder().withoutPadding();
        String token = encoder.encodeToString(bytes);
        Session session = new Session(
                token,
                SessionStatus.ACTIVE,
                new Date(),
                new Date(System.currentTimeMillis() + 3 * 60 * 1000)
        );
        session.setUser(userRepository.findById(userId));
        return sessionRepository.save(session);
    }
}
