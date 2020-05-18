package com.sparknetworks.filtermatch.service;
/**
 * Service to encapsulate the link between model and controller and to have business logic for some matches filter specific things.
 *
 * @author Deepak Srinivas
 */


import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.sparknetworks.filtermatch.constants.Fields;
import com.sparknetworks.filtermatch.convertor.ScoreConvertor;
import com.sparknetworks.filtermatch.exception.InValidInputException;
import com.sparknetworks.filtermatch.exception.SearchException;
import com.sparknetworks.filtermatch.exception.SearchTypeException;
import com.sparknetworks.filtermatch.model.Matches;
import com.sparknetworks.filtermatch.model.QMatches;
import com.sparknetworks.filtermatch.repository.CityRepository;
import com.sparknetworks.filtermatch.repository.MatchesRepository;
import com.sparknetworks.filtermatch.request.DistanceRange;
import com.sparknetworks.filtermatch.request.FilterRequest;
import com.sparknetworks.filtermatch.request.Range;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class MatchesServiceImpl implements MatchesService {

    private final MatchesRepository matchesRepository;

    private final CityRepository cityRepository;

    private final ScoreConvertor scoreConvertor;

    @Autowired
    public MatchesServiceImpl(final MatchesRepository matchesRepository, final CityRepository cityRepository, final ScoreConvertor scoreConvertor) {
        this.matchesRepository = matchesRepository;
        this.cityRepository = cityRepository;
        this.scoreConvertor = scoreConvertor;
    }


    /**
     * Method to save all the items from the given JSON.
     *
     * @param matchesList list of the matches list given .
     */
    @Override
    public void saveAllFromJson(List<Matches> matchesList) {
        for (Matches matches : matchesList) {
            cityRepository.save(matches.getCity());
            matchesRepository.save(matches);
        }

    }


    /**
     * Method to filter out the matches based on the criterias
     *
     * @param params Filter Request which encapsulates the search request .
     * @return List of mtches object .
     */
    @Override
    public List<Matches> findMatchesFilterCriteria(FilterRequest params) {
        List<Matches> distanceMatchesList = new ArrayList<>();
        List<Matches> matchesList = new ArrayList<>();
        QMatches qMatches = QMatches.matches;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (params.getHasPhoto() != null) {
            if (params.getHasPhoto() instanceof String) {
                if (params.getHasPhoto().equalsIgnoreCase(Fields.NO)) {
                    BooleanExpression hasPhotoExpNo = qMatches.photo.isNull();
                    booleanBuilder.and(hasPhotoExpNo);
                } else if (params.getHasPhoto().equalsIgnoreCase(Fields.YES)) {
                    BooleanExpression hasPhotoExpYes = qMatches.photo.isNotNull();
                    booleanBuilder.and(hasPhotoExpYes);
                } else {
                    throw new InValidInputException("The input given for HASPHOTO is not a proper value neither i.e Yes or NO");
                }

            } else {
                throw new SearchException("Exception: Given hasphoto should be String character");
            }
        }
        if (params.getInContact() != null) {
            if (params.getInContact() instanceof String) {
                if (params.getInContact().equalsIgnoreCase(Fields.NO)) {
                    BooleanExpression inContactExpNo = qMatches.contactsExchanged.eq(0);
                    booleanBuilder.and(inContactExpNo);
                } else if (params.getInContact().equalsIgnoreCase(Fields.YES)) {
                    BooleanExpression inContactExpYes = qMatches.contactsExchanged.goe(0);
                    booleanBuilder.and(inContactExpYes);
                } else {
                    throw new InValidInputException("The input given for INCONTACT is not a proper value neither i.e Yes or NO");
                }
            } else {
                throw new SearchException("Exception: Given INCONTACT should be String character");
            }
        }
        if (params.getIsFavorite() != null) {
            if (params.getIsFavorite() instanceof String) {
                if (params.getIsFavorite().equalsIgnoreCase(Fields.NO)) {
                    BooleanExpression isFavExpNo = qMatches.favourite.eq(false);
                    booleanBuilder.and(isFavExpNo);
                } else if (params.getIsFavorite().equalsIgnoreCase(Fields.YES)) {
                    BooleanExpression isFavExpYes = qMatches.favourite.eq(true);
                    booleanBuilder.and(isFavExpYes);
                } else {
                    throw new InValidInputException("The input given for ISFAVORITE is not a proper value neither i.e Yes or NO");
                }
            } else {
                throw new SearchException("Exception: Given ISFAVORITE should be String character");
            }
        }

        if (params.getCompatibilityScore() != null) {
            if (params.getCompatibilityScore() instanceof Range) {
                if (params.getCompatibilityScore().getMin() instanceof Double
                        && params.getCompatibilityScore().getMax() instanceof Double) {
                    if (params.getCompatibilityScore().getMin() != null && params.getCompatibilityScore().getMax() != null) {
                        Double minCompScore = 0.0;
                        Double maxCompScore = 0.0;
                        if (params.getCompatibilityScore().getMin() >= Fields.MIN_COMPPERCTENTAGE) {
                            minCompScore = scoreConvertor.percentageToDecimalConvertor(params.getCompatibilityScore().getMin());
                        } else {
                            minCompScore = Fields.MIN_COMPTABILITYSCORE;
                        }
                        if (params.getCompatibilityScore().getMax() <= Fields.MAX_COMPPERCTENTAGE) {
                            maxCompScore = scoreConvertor.percentageToDecimalConvertor(params.getCompatibilityScore().getMax());
                        } else {
                            maxCompScore = Fields.MAX_COMPTABILITYSCORE;
                        }
                        BooleanExpression compatibilityScoreExp = qMatches.compatibilityScore.between(minCompScore, maxCompScore);
                        booleanBuilder.and(compatibilityScoreExp);
                    } else {
                        throw new InValidInputException("The input given for COMPATIBILITYSCORE is not a proper value , as its not in range");
                    }
                } else {
                    throw new SearchTypeException("Exception: Given COMPATIBILITYSCORE max and min is not of type Double");
                }
            } else {
                throw new SearchException("Exception: Given COMPATIBILITYSCORE should be of Range value");
            }
        }

        if (params.getAge() != null) {
            if (params.getAge() instanceof Range) {
                if (params.getAge().getMin() instanceof Double
                        && params.getAge().getMax() instanceof Double) {
                    if (params.getAge().getMin() != null && params.getAge().getMax() != null) {
                        Integer minAge = 0;
                        Integer mazAge = 0;
                        if (params.getAge().getMin() >= Fields.MIN_AGE) {
                            minAge = params.getAge().getMin().intValue();
                        } else {
                            minAge = Fields.MIN_AGE;
                        }
                        if (params.getAge().getMax() <= Fields.MAX_AGE) {
                            mazAge = params.getAge().getMax().intValue();
                        } else {
                            mazAge = Fields.MAX_AGE;
                        }
                        BooleanExpression ageExp = qMatches.age.between(minAge, mazAge);
                        booleanBuilder.and(ageExp);
                    } else {
                        throw new InValidInputException("The input given for AGE is not a proper value , as its not in range");
                    }
                } else {
                    throw new SearchTypeException("Exception: Given AGE max and min is not of type Double");
                }

            } else {
                throw new SearchException("Exception: Given AGE should be Range value");
            }
        }

        if (params.getHeight() != null) {
            if (params.getHeight() instanceof Range) {
                if (params.getHeight().getMin() instanceof Double
                        && params.getHeight().getMax() instanceof Double) {
                    if (params.getHeight().getMin() != null && params.getHeight().getMax() != null) {
                        Double minHeight = 0.0;
                        Double maxHeight = 0.0;
                        if (params.getHeight().getMin() >= Fields.MIN_HEIGHT) {
                            minHeight = params.getHeight().getMin();
                        } else {
                            minHeight = Fields.MIN_HEIGHT;
                        }
                        if (params.getHeight().getMax() <= Fields.MAX_AGE) {
                            maxHeight = params.getHeight().getMax();
                        } else {
                            maxHeight = Fields.MAX_HEIGHT;
                        }
                        BooleanExpression heightExp = qMatches.heightInCm.between(minHeight, maxHeight);
                        booleanBuilder.and(heightExp);
                    } else {
                        throw new InValidInputException("The input given for HEIGHT is not a proper value , as its not in range");
                    }
                } else {
                    throw new SearchTypeException("Exception: Given HEIGHT max and min is not of type Double");
                }

            } else {
                throw new SearchException("Exception: Given HEIGHT should be Range value");
            }
        }
        if (params.getDistance() != null) {
            if (params.getDistance() instanceof DistanceRange) {
                if (params.getDistance().getLatitude() instanceof Double
                        && params.getDistance().getLongitude() instanceof Double
                        && params.getDistance().getMin() instanceof Double
                        && params.getDistance().getMax() instanceof Double) {
                    if (params.getDistance().getLatitude() != null && params.getDistance().getLongitude() != null
                            && params.getDistance().getMin() != null && params.getDistance().getMax() != null) {
                        Double distanceLow = 0.0;
                        Double distanceHigh = 0.0;
                        if (params.getDistance().getMin() >= Fields.MIN_DISTANCE) {
                            distanceLow = params.getDistance().getMin();
                        } else {
                            distanceLow = Fields.MIN_DISTANCE.doubleValue();
                        }
                        if (params.getDistance().getMax() <= Fields.MAX_DISTANCE) {
                            distanceHigh = params.getDistance().getMax();
                        } else {
                            distanceHigh = Fields.MAX_DISTANCE.doubleValue();
                        }
                        //Assuming that the front end would pick the Latitude and Longitude.
                        distanceMatchesList = matchesRepository.findAllListOfCitiesByDistance(params.getDistance().getLatitude(), params.getDistance().getLongitude(),
                                distanceLow, distanceHigh);
                    } else {
                        throw new InValidInputException("The input given for DISTANCE is not a proper value , Can't be null");
                    }
                } else {
                    throw new SearchTypeException("Exception: Given DISTANCE max,min,latitude,longitude is not of type Double");
                }

            } else {
                throw new SearchException("Exception: Given HEIGHT should be DistanceRange value");
            }
        }
        if (booleanBuilder.hasValue()) {
            Iterable<Matches> iterableDriver = matchesRepository.findAll(booleanBuilder);
            iterableDriver.forEach(matchesList::add);
        }
        if (distanceMatchesList != null && !distanceMatchesList.isEmpty()) {
            matchesList.addAll(distanceMatchesList);
        }
        return matchesList;
    }
}
