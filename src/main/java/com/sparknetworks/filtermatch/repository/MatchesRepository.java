package com.sparknetworks.filtermatch.repository;

import com.sparknetworks.filtermatch.model.Matches;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface MatchesRepository extends JpaRepository<Matches, BigInteger>, QuerydslPredicateExecutor<Matches> {



    @Query("SELECT f from Matches f INNER JOIN City c on f.city=c.id where acos(sin(:personLat) * sin(c.latitude) + cos(:personLat) * cos(c.latitude) * cos(c.longitude - (:personLong)))*6371 >=:distanceMin and acos(sin(:personLat) * sin(c.latitude) + cos(:personLat) * cos(c.latitude) * cos(c.longitude - (:personLong)))*6371<=:distanceMax")
    List<Matches> findAllListOfCitiesByDistance(@Param("personLat") Double personLat, @Param("personLong") Double personLong, @Param("distanceMin") Double distanceMin, @Param("distanceMax") Double distanceMax);
}
