package com.example.entity;

import com.example.dto.PhoneDto;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "phone_data", schema = "public")
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "phone_data_id_seq")
    @SequenceGenerator(name = "phone_data_id_seq", sequenceName = "phone_data_id_seq", allocationSize = 1)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "phone")
    private String phone;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Phone() {
    }

    public Phone(String phone, User user) {
        this.phone = phone;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Phone{" +
            "id=" + id +
            ", phone='" + phone + '\'' +
            ", user=" + (user == null ? null : user.getId()) +
            '}';
    }

    public PhoneDto dto() {
        return new PhoneDto()
            .id(id)
            .phone(phone)
            .userId(user == null ? null : user.getId());
    }
}
