package no.hvl.dat250.feedapp.prototype.services;

import jakarta.persistence.EntityManager;
import no.hvl.dat250.feedapp.prototype.models.Poll;
import no.hvl.dat250.feedapp.prototype.models.User;
import no.hvl.dat250.feedapp.prototype.models.Vote;

public class VoteDao {
  private EntityManager em;
  private PollDao pollDao;
  private UserDao userDao;
  private InviteDao inviteDao;

  public VoteDao(EntityManager em, PollDao pollDao, UserDao userDao, InviteDao inviteDao) {
    this.em = em;
    this.pollDao = pollDao;
    this.userDao = userDao;
    this.inviteDao = inviteDao;
  }

  public void vote(Long pollId, Boolean answer) {
    this.verifyPermission(pollId);

    Vote vote = new Vote();
    vote.setPoll(this.pollDao.getPollById(pollId));
    vote.setAnswer(answer);

    em.persist(vote);
  }

  public void vote(Long pollId, Boolean answer, Long userId) {
    this.verifyPermission(pollId, userId);

    Vote vote = new Vote();
    vote.setPoll(this.pollDao.getPollById(pollId));
    vote.setUser(this.userDao.getUserById(userId));
    vote.setAnswer(answer);

    em.persist(vote);
  }

  public void verifyPermission(Long pollId, Long userId) {
    Poll poll = this.pollDao.getPollById(pollId);

    if (!poll.isPrivate()) {
      return;
    }

    User user = this.userDao.getUserById(userId);

    if (!this.inviteDao.hasInvite(pollId, user.getEmail()) && !poll.getOwner().getId().equals(user.getId())) {
      throw new RuntimeException("User does not have access to this poll");
    }
  }

  public void verifyPermission(Long pollId) {
    Poll poll = this.pollDao.getPollById(pollId);

    if (poll.isPrivate()) {
      throw new RuntimeException("User does not have access to this poll");
    }
  }
}
