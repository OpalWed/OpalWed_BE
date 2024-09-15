package com.exe201.opalwed.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "StaffSchedule")
@Data
public class StaffSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Information customer;

    @OneToOne
    private Information staff;

    private LocalDateTime startDateTime;

    private ScheduleStatus scheduleStatus;
}
