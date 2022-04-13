package com.juliansanchez.tenpochallenge.security.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private ERole name;

    @Override
    public String getAuthority() {
        return name.name();
    }
}
