package com.sparknetworks.filtermatch.controller;

/**
 * MatchesFilter controller , all operations with MatchesFilter goes here
 *
 * @author Deepak Srinivas
 */

import com.sparknetworks.filtermatch.model.Matches;
import com.sparknetworks.filtermatch.request.FilterRequest;
import com.sparknetworks.filtermatch.service.MatchesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/matches")
public class MatchesFilterController {

    private final MatchesServiceImpl matchesService;

    @Autowired
    public MatchesFilterController(final MatchesServiceImpl matchesService) {
        this.matchesService = matchesService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createMatchesForDb(@RequestBody List<Matches> matchesList) {
        matchesService.saveAllFromJson(matchesList);
    }


    @PostMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<Matches> findFilterCriteriaMatches(@RequestBody FilterRequest params) {
        return matchesService.findMatchesFilterCriteria(params);
    }

}
