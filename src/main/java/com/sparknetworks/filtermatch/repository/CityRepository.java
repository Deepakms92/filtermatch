package com.sparknetworks.filtermatch.repository;
/**
 * Repository class for City .
 *
 * @author Deepak Srinivas
 */

import com.sparknetworks.filtermatch.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface CityRepository extends JpaRepository<City, BigInteger> {


}
