package no.hvl.dat250.feedapp.prototype.models;

import java.util.Date;

import jakarta.persistence.*;

@Entity
public class Invite {
    @Id
    @ManyToOne
    @JoinColumn(name = "poll_id")
    private Poll poll;

    @Id
    private String email;

    @Column(updatable = false)
    private Date created;

    @PrePersist
    protected void onCreate() {
        this.created = new Date();
    }

    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreated() {
        return created;
    }
}
