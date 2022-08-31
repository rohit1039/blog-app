package com.blog.api.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author - Rohit Parida
 *
 * @year - 2022
 */
@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @Column(name = "role_id")
    private Integer id;
    @Column(name = "name",length = 40, nullable = false, unique = true)
    private String name;
    @Column(name= "description",length = 150, nullable = false)
    private String description;

}
