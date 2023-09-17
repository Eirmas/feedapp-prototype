package no.hvl.dat250.feedapp.prototype.services;

import jakarta.persistence.EntityManager;
import no.hvl.dat250.feedapp.prototype.models.Poll;
import no.hvl.dat250.feedapp.prototype.models.User;

public class PollDao {
  private EntityManager em;

  public PollDao(EntityManager em) {
    this.em = em;
  }

  public Poll getPollById(Long id) {
    return em.find(Poll.class, id);
  }

  public Poll createPoll(String title, String question, User owner, Boolean isPrivate) {
    Poll poll = new Poll();
    poll.setTitle(title);
    poll.setQuestion(question);
    poll.setOwner(owner);
    poll.setPrivate(isPrivate);

    em.persist(poll);
    return poll;
  }

  public Poll updatePoll(Poll poll) {
    em.merge(poll);
    return poll;
  }

  public void deletePollById(Long id) {
    em.createQuery("DELETE FROM Poll p WHERE p.id = :id")
        .setParameter("id", id)
        .executeUpdate();
  }

  public int getPollYesById(Long id) {
    return em.createQuery("SELECT COUNT(v) FROM Vote v WHERE v.poll.id = :id AND v.answer = true", Long.class)
        .setParameter("id", id)
        .getSingleResult().intValue();
  }

  public int getPollNoById(Long id) {
    return em.createQuery("SELECT COUNT(v) FROM Vote v WHERE v.poll.id = :id AND v.answer = false", Long.class)
        .setParameter("id", id)
        .getSingleResult().intValue();
  }
}
