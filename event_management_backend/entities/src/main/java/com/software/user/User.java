package com.software.user;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    private String id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "is_admin", nullable = false)
    private boolean isAdmin = false;

}
