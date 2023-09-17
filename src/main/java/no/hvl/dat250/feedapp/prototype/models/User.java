package no.hvl.dat250.feedapp.prototype.models;

import java.util.Date;
import java.util.Set;

import jakarta.persistence.*;

@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.REMOVE)
    private Set<Poll> polls;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String given_name;

    @Column(nullable = false)
    private String family_name;

    @Column(updatable = false, nullable = false)
    private Date created;

    @Column(nullable = false)
    private Date updated;

    @PrePersist
    protected void onCreate() {
        Date date = new Date();
        this.created = date;
        this.updated = date;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updated = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Poll> getPolls() {
        return polls;
    }

    public void setPolls(Set<Poll> polls) {
        this.polls = polls;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGivenName() {
        return given_name;
    }

    public void setGivenName(String given_name) {
        this.given_name = given_name;
    }

    public String getFamilyName() {
        return family_name;
    }

    public void setFamilyName(String family_name) {
        this.family_name = family_name;
    }

    public Date getCreated() {
        return created;
    }

    public Date getUpdated() {
        return updated;
    }
}
