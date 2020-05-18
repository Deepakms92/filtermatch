package com.sparknetworks.filtermatch.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparknetworks.filtermatch.model.City;
import com.sparknetworks.filtermatch.model.Matches;
import com.sparknetworks.filtermatch.request.DistanceRange;
import com.sparknetworks.filtermatch.request.FilterRequest;
import com.sparknetworks.filtermatch.request.Range;
import com.sparknetworks.filtermatch.service.MatchesServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(MockitoJUnitRunner.class)
public class MatchesFilterControllerTest {

    @Mock
    private MatchesServiceImpl matchesService;

    private MockMvc mockMvc;

    @InjectMocks
    private MatchesFilterController matchesFilterController;

    private Matches matches;

    private static final ObjectMapper mapper = new ObjectMapper();

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
        matches.setDisplayName("Deepak");
        mockMvc = MockMvcBuilders.standaloneSetup(matchesFilterController).dispatchOptions(true).build();
    }

    @Test
    public void testSaveJsonForDb() throws Exception {
        List<Matches> matchesList = new ArrayList<>();
        matchesList.add(matches);

        MockHttpServletResponse response =
                mockMvc
                        .perform(
                                post("/v1/matches")
                                        .content(mapper.writeValueAsString(matchesList))
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isCreated())
                        .andReturn().getResponse();

        verify(matchesService, times(1)).saveAllFromJson(matchesList);
        ;

    }

    @Test
    public void testFilterCriteriaForMatches() throws Exception {

        FilterRequest filterRequest = new FilterRequest();
        filterRequest.setInContact("Yes");
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
        DistanceRange distanceRange = new DistanceRange();
        distanceRange.setLatitude(51.23231);
        distanceRange.setLongitude(-1.12343);
        distanceRange.setMin(46.0);
        distanceRange.setMax(250.0);
        filterRequest.setDistance(distanceRange);

        List<Matches> matchesListFilter = new ArrayList<>();
        matchesListFilter.add(matches);
        when(matchesService.findMatchesFilterCriteria(filterRequest)).thenReturn(matchesListFilter);

        MockHttpServletResponse response =
                mockMvc
                        .perform(
                                post("/v1/matches/search")
                                        .content(mapper.writeValueAsString(filterRequest))
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andReturn().getResponse();

        Assert.assertTrue(response.getContentAsString().contains("Deepak"));
        verify(matchesService, times(1)).findMatchesFilterCriteria(any(FilterRequest.class));
    }

}
