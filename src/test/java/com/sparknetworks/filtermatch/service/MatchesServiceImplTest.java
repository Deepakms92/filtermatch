package com.sparknetworks.filtermatch.service;

import com.querydsl.core.BooleanBuilder;
import com.sparknetworks.filtermatch.convertor.ScoreConvertor;
import com.sparknetworks.filtermatch.exception.InValidInputException;
import com.sparknetworks.filtermatch.exception.SearchTypeException;
import com.sparknetworks.filtermatch.model.City;
import com.sparknetworks.filtermatch.model.Matches;
import com.sparknetworks.filtermatch.repository.CityRepository;
import com.sparknetworks.filtermatch.repository.MatchesRepository;
import com.sparknetworks.filtermatch.request.DistanceRange;
import com.sparknetworks.filtermatch.request.FilterRequest;
import com.sparknetworks.filtermatch.request.Range;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MatchesServiceImplTest {

    @Mock
    private MatchesRepository matchesRepository;

    @Mock
    private CityRepository cityRepository;

    @Mock
    private ScoreConvertor scoreConvertor;

    @InjectMocks
    private MatchesServiceImpl matchesService;

    private Matches matches;

    @Before
    public void setUp() {
        City city = new City();
        city.setId(BigInteger.ONE);
        city.setName("Leeds");
        city.setLatitude(51.2132323);
        city.setLongitude(-1.233434);
        matches = new Matches();
        matches.setId(BigInteger.ONE);
        matches.setAge(36);
        matches.setCity(city);
    }

    @Test
    public void testSaveMatchesforDb() throws Exception {
        List<Matches> matchesList = new ArrayList<>();
        matchesList.add(matches);
        matchesService.saveAllFromJson(matchesList);
        verify(matchesRepository, times(matchesList.size())).save(any(Matches.class));
        verify(cityRepository, times(matchesList.size())).save(any(City.class));
    }

    @Test(expected = InValidInputException.class)
    public void test_FilterCriteriaWhenWrongHasPhotoValue() {
        FilterRequest filterRequest = new FilterRequest();
        filterRequest.setHasPhoto("2234456");
        matchesService.findMatchesFilterCriteria(filterRequest);
    }

    @Test(expected = InValidInputException.class)
    public void test_FilterCriteriaWhenWrongInContactValue() {
        FilterRequest filterRequest = new FilterRequest();
        filterRequest.setInContact("2234456");
        matchesService.findMatchesFilterCriteria(filterRequest);
    }

    @Test(expected = InValidInputException.class)
    public void test_FilterCriteriaWhenWrongISFavValue() {
        FilterRequest filterRequest = new FilterRequest();
        filterRequest.setIsFavorite("2234456");
        matchesService.findMatchesFilterCriteria(filterRequest);
    }


    @Test(expected = SearchTypeException.class)
    public void test_FilterCriteriaWhenNullCompScoreRange() {
        FilterRequest filterRequest = new FilterRequest();
        Range compRange = new Range();
        compRange.setMin(null);
        compRange.setMax(null);
        filterRequest.setCompatibilityScore(compRange);
        matchesService.findMatchesFilterCriteria(filterRequest);
    }

    @Test(expected = SearchTypeException.class)
    public void test_FilterCriteriaWhenNullAgeRange() {
        FilterRequest filterRequest = new FilterRequest();
        Range ageRange = new Range();
        ageRange.setMin(null);
        ageRange.setMax(null);
        filterRequest.setAge(ageRange);
        matchesService.findMatchesFilterCriteria(filterRequest);
    }

    @Test(expected = SearchTypeException.class)
    public void test_FilterCriteriaWhenNullHeightRange() {
        FilterRequest filterRequest = new FilterRequest();
        Range heightRange = new Range();
        heightRange.setMin(null);
        heightRange.setMax(null);
        filterRequest.setAge(heightRange);
        matchesService.findMatchesFilterCriteria(filterRequest);
    }

    @Test(expected = SearchTypeException.class)
    public void test_FilterCriteriaWhenNullDistanceRange() {
        FilterRequest filterRequest = new FilterRequest();
        DistanceRange distanceRange = new DistanceRange();
        distanceRange.setLatitude(null);
        distanceRange.setLongitude(null);
        distanceRange.setMin(null);
        distanceRange.setMax(null);
        filterRequest.setDistance(distanceRange);
        matchesService.findMatchesFilterCriteria(filterRequest);
    }


    @Test
    public void testFindFilterCritireaWHenAllValuesAreFineandYes() throws Exception {
        List<Matches> matchesList = new ArrayList<>();
        matchesList.add(matches);
        List<Matches> distanceList = new ArrayList<>();
        distanceList.add(matches);
        FilterRequest filterRequest = new FilterRequest();
        filterRequest.setInContact("Yes");
        filterRequest.setHasPhoto("Yes");
        filterRequest.setIsFavorite("Yes");
        Range ageRange = new Range();
        ageRange.setMin(36.0);
        ageRange.setMax(54.0);
        Range compRange = new Range();
        compRange.setMin(3.0);
        compRange.setMax(54.0);
        Range heightRange = new Range();
        heightRange.setMin(139.0);
        heightRange.setMax(154.0);
        filterRequest.setCompatibilityScore(compRange);
        filterRequest.setHeight(heightRange);
        filterRequest.setAge(ageRange);
        DistanceRange distanceRange = new DistanceRange();
        distanceRange.setLatitude(51.23231);
        distanceRange.setLongitude(-1.12343);
        distanceRange.setMin(46.0);
        distanceRange.setMax(250.0);
        filterRequest.setDistance(distanceRange);
        when(matchesRepository.findAll(any(BooleanBuilder.class))).thenReturn(matchesList);
        when(matchesRepository.findAllListOfCitiesByDistance(any(), any(), any(), any())).thenReturn(distanceList);
        when(scoreConvertor.percentageToDecimalConvertor(filterRequest.getCompatibilityScore().getMin())).thenReturn(any(Double.class));
        when(scoreConvertor.percentageToDecimalConvertor(filterRequest.getCompatibilityScore().getMin())).thenReturn(any(Double.class));
        matchesService.findMatchesFilterCriteria(filterRequest);
        verify(matchesRepository, times(1)).findAll(any(BooleanBuilder.class));
        verify(matchesRepository, times(1)).findAllListOfCitiesByDistance(any(), any(), any(), any());
        verify(scoreConvertor, times(2)).percentageToDecimalConvertor(any(Double.class));
    }

    @Test
    public void testFindFilterCritireaWHenAllFineAndNo() throws Exception {
        List<Matches> matchesList = new ArrayList<>();
        matchesList.add(matches);
        List<Matches> distanceList = new ArrayList<>();
        distanceList.add(matches);
        FilterRequest filterRequest = new FilterRequest();
        filterRequest.setInContact("No");
        filterRequest.setHasPhoto("No");
        filterRequest.setIsFavorite("No");
        Range ageRange = new Range();
        ageRange.setMin(36.0);
        ageRange.setMax(54.0);
        Range compRange = new Range();
        compRange.setMin(3.0);
        compRange.setMax(54.0);
        Range heightRange = new Range();
        heightRange.setMin(139.0);
        heightRange.setMax(154.0);
        filterRequest.setCompatibilityScore(compRange);
        filterRequest.setHeight(heightRange);
        filterRequest.setAge(ageRange);
        DistanceRange distanceRange = new DistanceRange();
        distanceRange.setLatitude(51.23231);
        distanceRange.setLongitude(-1.12343);
        distanceRange.setMin(46.0);
        distanceRange.setMax(250.0);
        filterRequest.setDistance(distanceRange);
        when(matchesRepository.findAll(any(BooleanBuilder.class))).thenReturn(matchesList);
        when(matchesRepository.findAllListOfCitiesByDistance(any(), any(), any(), any())).thenReturn(distanceList);
        when(scoreConvertor.percentageToDecimalConvertor(filterRequest.getCompatibilityScore().getMin())).thenReturn(any(Double.class));
        when(scoreConvertor.percentageToDecimalConvertor(filterRequest.getCompatibilityScore().getMin())).thenReturn(any(Double.class));
        matchesService.findMatchesFilterCriteria(filterRequest);
        verify(matchesRepository, times(1)).findAll(any(BooleanBuilder.class));
        verify(matchesRepository, times(1)).findAllListOfCitiesByDistance(any(), any(), any(), any());
        verify(scoreConvertor, times(2)).percentageToDecimalConvertor(any(Double.class));
    }

    @Test
    public void testFindFilterCritireaWHenAllFineandAllOutofRange() throws Exception {
        List<Matches> matchesList = new ArrayList<>();
        matchesList.add(matches);
        List<Matches> distanceList = new ArrayList<>();
        distanceList.add(matches);
        FilterRequest filterRequest = new FilterRequest();
        filterRequest.setInContact("Yes");
        filterRequest.setHasPhoto("Yes");
        filterRequest.setIsFavorite("Yes");
        Range ageRange = new Range();
        ageRange.setMin(16.0);
        ageRange.setMax(100.0);
        Range compRange = new Range();
        compRange.setMin(0.0);
        compRange.setMax(100.0);
        Range heightRange = new Range();
        heightRange.setMin(100.0);
        heightRange.setMax(300.0);
        filterRequest.setCompatibilityScore(compRange);
        filterRequest.setHeight(heightRange);
        filterRequest.setAge(ageRange);
        DistanceRange distanceRange = new DistanceRange();
        distanceRange.setLatitude(51.23231);
        distanceRange.setLongitude(-1.12343);
        distanceRange.setMin(10.0);
        distanceRange.setMax(550.0);
        filterRequest.setDistance(distanceRange);
        when(matchesRepository.findAll(any(BooleanBuilder.class))).thenReturn(matchesList);
        when(matchesRepository.findAllListOfCitiesByDistance(any(), any(), any(), any())).thenReturn(distanceList);
        matchesService.findMatchesFilterCriteria(filterRequest);
        verify(matchesRepository, times(1)).findAll(any(BooleanBuilder.class));
        verify(matchesRepository, times(1)).findAllListOfCitiesByDistance(any(), any(), any(), any());
        verify(scoreConvertor, times(0)).percentageToDecimalConvertor(any(Double.class));
    }


}
