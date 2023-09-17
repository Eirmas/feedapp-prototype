package no.hvl.dat250.feedapp.prototype.models;

import jakarta.persistence.*;

@Entity(name = "votes")
public class Vote {
    @Id
    @ManyToOne
    private Poll poll;

    @Id
    @ManyToOne
    private User user;

    @Column
    private boolean answer;

    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }

    public boolean getAnswer() {
        return answer;
    }

    public void setAnswer(boolean answer) {
        this.answer = answer;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
