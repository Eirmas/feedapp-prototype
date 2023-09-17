package no.hvl.dat250.feedapp.prototype.services;

import jakarta.persistence.EntityManager;
import no.hvl.dat250.feedapp.prototype.models.Vote;

public class VoteDao {
  private EntityManager em;
  private PollDao pollDao;
  private UserDao userDao;

  public VoteDao(EntityManager em, PollDao pollDao, UserDao userDao) {
    this.em = em;
    this.pollDao = pollDao;
    this.userDao = userDao;
  }

  public void vote(Long pollId, Long userId, Boolean answer) {
    Vote vote = em.createQuery("SELECT v FROM Vote v WHERE v.poll.id = :pollId AND v.user.id = :userId", Vote.class)
        .setParameter("pollId", pollId)
        .setParameter("userId", userId)
        .getResultStream().findFirst().orElse(null);

    if (vote == null) {
      vote = new Vote();
      vote.setPoll(this.pollDao.getPollById(pollId));
      vote.setUser(this.userDao.getUserById(userId));
      vote.setAnswer(answer);
    } else {
      vote.setAnswer(answer);
    }

    em.persist(vote);
  }
}
