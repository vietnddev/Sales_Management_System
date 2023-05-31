package com.flowiee.app.hethong.model;

import lombok.Data;

import java.util.List;

@Data
public class Profile {
    private int id;
    private String username;
    private String password;
    private String name;
    private String phone;
    private String email;
    private String address;
    private List<String> role;
    private List<Integer> donHangId;
}