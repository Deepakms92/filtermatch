package com.sparknetworks.filtermatch.service;

import com.sparknetworks.filtermatch.model.Matches;
import com.sparknetworks.filtermatch.request.FilterRequest;

import java.util.List;

public interface MatchesService {

    void saveAllFromJson(List<Matches> matchesList);

    List<Matches> findMatchesFilterCriteria(FilterRequest params);
}
