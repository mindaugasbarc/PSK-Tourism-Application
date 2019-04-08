package com.tourism.psk.service;

import com.tourism.psk.model.Session;

public interface SessionService {
    Session authenticate(String token);
    Session create(long userId);
}
