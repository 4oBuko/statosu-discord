package com.ua.statosudiscord.persistence.repositories;

import com.ua.statosudiscord.persistence.entities.Statistic;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StatisticRepository extends MongoRepository<Statistic, Long> {
}
