package com.flowiee.app.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountRequest {
	private Integer id;
    private String username;
    private String password;
    private String name;    
    private Boolean sex;    
    private String phone;    
    private String email;
    private String address;    
    private String avatar;    
    private String note;
    private String role;
    private Boolean status;
}