package com.users.api.models.entities;

import com.users.api.models.entities.Phone;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "person")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    private String name;
    private String email;
    private String password;

    @Column(name = "created_date")
    private Date createdDate;
    @Column(name = "modified_date")
    private Date modifiedDate;
    @Column(name= "last_login_Date")
    private Date lastLoginDate;
    private String token;
    @Column(name = "is_active")
    private boolean isActive;

    @OneToMany(mappedBy = "user")
    private List<Phone> phones;
}
