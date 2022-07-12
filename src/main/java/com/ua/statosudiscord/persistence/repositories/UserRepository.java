package com.ua.statosudiscord.persistence.repositories;

import com.ua.statosudiscord.persistence.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, Long> {
}
