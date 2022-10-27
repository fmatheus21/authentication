package com.fmatheus.app.model.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;


@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "role", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id"})})
public class Role implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "role", nullable = false, length = 100)
    private String role;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @ToString.Exclude
    @JoinTable(name = "user_role_join", joinColumns = {@JoinColumn(name = "id_role", referencedColumnName = "id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "id_user", referencedColumnName = "id", nullable = false)})
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<User> users;

    @JoinColumn(name = "id_application", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Application application;


}
