package com.example.gradebackend.model.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "positon")
public class Position extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private PositionTitle positionTitle;
}
