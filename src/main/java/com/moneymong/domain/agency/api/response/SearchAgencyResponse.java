package com.moneymong.domain.agency.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class SearchAgencyResponse {
    private List<AgencyResponse> agencyList;
    private long totalCount;
}
