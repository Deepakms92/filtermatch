package com.sparknetworks.filtermatch.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Objects;

@Entity
@Table(name = "CITY")
@Getter
@Setter
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private BigInteger id;

    @Column(name = "name")
    private String name;

    @JsonProperty("lat")
    @Column(name = "latitude")
    private Double latitude;

    @JsonProperty("lon")
    @Column(name = "longitude")
    private Double longitude;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return Objects.equals(id, city.id) &&
                Objects.equals(name, city.name) &&
                Objects.equals(latitude, city.latitude) &&
                Objects.equals(longitude, city.longitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, latitude, longitude);
    }
}
