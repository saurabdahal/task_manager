package com.casperinv.service.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "job")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    private String title;
    @Column(name = "company")
    private String company;
    @Column(name = "link")
    private String link;

    @Column(name = "serial_id")
    private String serialid;

    @Column(name = "response")
    private boolean response;

    @Column(name = "interview")
    private boolean interview;

    @Column(name = "jd")
    private String jd;

    @Column(name = "applied_date")
    private LocalDate appliedDate;

    @Column(name = "created_at")
    private LocalDate createdAt;
    @Column(name = "updated_at")
    private LocalDate updatedAt;


}
