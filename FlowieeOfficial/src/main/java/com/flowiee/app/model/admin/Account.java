package com.flowiee.app.model.admin;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Account")

public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private String Username;
    private String Password;
    private String Name;
    private boolean Gender;
    private String Phone;
    private String Email;
    private boolean IsAdmin;
    private String Avatar;
    private String Notes;
    private boolean Status;
}
