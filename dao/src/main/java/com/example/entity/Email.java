package com.example.entity;

import com.example.dto.EmailDto;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "email_data", schema = "public")
public class Email {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "email_data_id_seq")
    @SequenceGenerator(name = "email_data_id_seq", sequenceName = "email_data_id_seq", allocationSize = 1)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "email")
    private String email;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Email() {
    }

    public Email(String email, User user) {
        this.email = email;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Email{" +
            "id=" + id +
            ", email='" + email + '\'' +
            ", user=" + (user == null ? null : user.getId()) +
            '}';
    }

    public EmailDto dto() {
        return new EmailDto()
            .id(id)
            .email(email)
            .userId(user == null ? null : user.getId());
    }
}
