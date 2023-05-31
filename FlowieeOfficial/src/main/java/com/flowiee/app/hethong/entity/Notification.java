package com.flowiee.app.hethong.entity;

import javax.persistence.*;

@Entity
@Table(name = "notification")
public class Notification {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
}