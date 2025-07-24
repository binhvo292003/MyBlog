package com.brianvo.myblog.service;

import com.brianvo.myblog.domain.entity.User;

import java.util.UUID;

public interface UserService {

    User getUserById(UUID id);
}
