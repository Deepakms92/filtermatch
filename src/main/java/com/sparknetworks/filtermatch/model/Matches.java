package com.sparknetworks.filtermatch.model;

/**
 * Domain class for Matches .
 *
 * @author Deepak Srinivas
 */

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Table(name = "FILTER_MATCHES")
@Getter
@Setter
public class Matches {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigInteger id;

    @JsonProperty("display_name")
    @Column(name = "display_name")
    private String displayName;

    @Column(name = "age")
    private Integer age;

    @JsonProperty("job_title")
    @Column(name = "job_title")
    private String jobTitle;

    @JsonProperty("height_in_cm")
    @Column(name = "height_in_cm")
    private Double heightInCm;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    private City city;

    @JsonProperty("main_photo")
    @Column(name = "main_photo")
    private String photo;

    @JsonProperty("compatibility_score")
    @Column(name = "compatibility_score")
    private Double compatibilityScore;

    @JsonProperty("contacts_exchanged")
    @Column(name = "contacts_exchanged")
    private Integer contactsExchanged;

    @Column(name = "favourite")
    public Boolean favourite;

    @Column(name = "religion")
    public String religion;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matches matches = (Matches) o;
        return Objects.equals(id, matches.id) &&
                Objects.equals(displayName, matches.displayName) &&
                Objects.equals(age, matches.age) &&
                Objects.equals(jobTitle, matches.jobTitle) &&
                Objects.equals(heightInCm, matches.heightInCm) &&
                Objects.equals(photo, matches.photo) &&
                Objects.equals(compatibilityScore, matches.compatibilityScore) &&
                Objects.equals(contactsExchanged, matches.contactsExchanged) &&
                Objects.equals(favourite, matches.favourite) &&
                Objects.equals(religion, matches.religion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, displayName, age, jobTitle, heightInCm, photo, compatibilityScore, contactsExchanged, favourite, religion);
    }
}
