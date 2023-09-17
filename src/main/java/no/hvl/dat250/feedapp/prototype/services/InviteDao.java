package no.hvl.dat250.feedapp.prototype.services;

import jakarta.persistence.EntityManager;
import no.hvl.dat250.feedapp.prototype.models.Invite;
import no.hvl.dat250.feedapp.prototype.models.Poll;

public class InviteDao {
  private EntityManager em;
  private PollDao pollDao;

  public InviteDao(EntityManager em, PollDao pollDao) {
    this.em = em;
    this.pollDao = pollDao;
  }

  public Invite getInvite(Long pollId, String email) {
    return em.createQuery("SELECT i FROM Invite i WHERE i.poll.id = :pollId AND i.email = :email", Invite.class)
        .setParameter("pollId", pollId)
        .setParameter("email", email)
        .getSingleResult();
  }

  public Invite inviteUser(Long pollId, String email) {
    Invite invite = new Invite();

    Poll poll = this.pollDao.getPollById(pollId);

    invite.setPoll(poll);
    invite.setEmail(email);

    em.persist(invite);

    return invite;
  }

  public void deleteInvite(Long pollId, String email) {
    em.createQuery("DELETE FROM Invite i WHERE i.poll.id = :pollId AND i.user.email = :email")
        .setParameter("pollId", pollId)
        .setParameter("email", email)
        .executeUpdate();
  }
}
