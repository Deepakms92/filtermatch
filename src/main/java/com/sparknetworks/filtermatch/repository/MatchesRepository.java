package com.sparknetworks.filtermatch.repository;

/**
 * Repository class for Matches .
 *
 * @author Deepak Srinivas
 */

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


    /**
     * Method to find the distance between two location using the latitude and the longitude.
     *
     * @param personLat   latitude of the person location
     * @param personLong  longitude of the person location
     * @param distanceMin distance lower bound
     * @param distanceMax diatnce upper bound
     * @return list of matches Object
     */
    @Query("SELECT f from Matches f INNER JOIN City c on f.city=c.id where acos(sin(:personLat) * sin(c.latitude) + cos(:personLat) * cos(c.latitude) * cos(c.longitude - (:personLong)))*6371 >=:distanceMin and acos(sin(:personLat) * sin(c.latitude) + cos(:personLat) * cos(c.latitude) * cos(c.longitude - (:personLong)))*6371<=:distanceMax")
    List<Matches> findAllListOfCitiesByDistance(@Param("personLat") Double personLat, @Param("personLong") Double personLong, @Param("distanceMin") Double distanceMin, @Param("distanceMax") Double distanceMax);
}
