package com.moneymong.global.domain;

import static lombok.AccessLevel.*;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = PROTECTED)
@MappedSuperclass
public abstract class BaseEntity extends TimeBaseEntity{
    private boolean deleted;
}
