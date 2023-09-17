package no.hvl.dat250.feedapp.prototype.models;

import jakarta.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity(name = "polls")
public class Poll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private User owner;

    @OneToMany(mappedBy = "poll", cascade = CascadeType.REMOVE)
    private Set<Invite> invites;

    @OneToMany(mappedBy = "poll", cascade = CascadeType.REMOVE)
    private Set<Vote> votes;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String question;

    @Column(nullable = false)
    private boolean isPrivate = false;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status = Status.Before;

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

    public enum Status {
        Before,
        Ongoing,
        Closed
    }

    public Long getId() {
        return id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User user) {
        this.owner = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getCreated() {
        return created;
    }

    public Date getUpdated() {
        return updated;
    }

    public Set<Invite> getInvites() {
        return invites;
    }

    public Set<Vote> getVotes() {
        return votes;
    }
}
