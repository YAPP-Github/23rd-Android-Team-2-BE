package com.moneymong.domain.agency.api.request;

import com.moneymong.domain.agency.entity.enums.AgencyType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateAgencyRequest {
    private String name;
    private String description;
    private AgencyType agencyType;
    private String thumbnailImage;
}
