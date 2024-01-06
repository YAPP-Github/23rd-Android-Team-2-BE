package com.moneymong.domain.agency.api.response;

import com.moneymong.domain.agency.entity.Agency;
import com.moneymong.domain.agency.entity.enums.AgencyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AgencyResponse {
    private Long id;
    private String name;
    private int headCount;
    private AgencyType type;

    public static AgencyResponse from(Agency agency) {
        return AgencyResponse.builder()
                .id(agency.getId())
                .name(agency.getAgencyName())
                .headCount(agency.getHeadCount())
                .type(agency.getAgencyType())
                .build();
    }
}
