package com.etoak.common.entity.user;

import com.blade.jdbc.annotation.Table;
import com.blade.jdbc.core.ActiveRecord;
import lombok.Data;

import java.util.Date;

@Table(value = "user" , pk = "userid")
@Data
public class User  extends ActiveRecord {

    private String userid ;
    private String username ;
    private String password ;
    private String phone ;
    private String email ;
    private Date registerdate ;
    private String inviterphone ;

}
