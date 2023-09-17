package no.hvl.dat250.feedapp.prototype;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import no.hvl.dat250.feedapp.prototype.models.Poll;
import no.hvl.dat250.feedapp.prototype.models.User;
import no.hvl.dat250.feedapp.prototype.services.InviteDao;
import no.hvl.dat250.feedapp.prototype.services.PollDao;
import no.hvl.dat250.feedapp.prototype.services.UserDao;
import no.hvl.dat250.feedapp.prototype.services.VoteDao;

public class Main {

  static final String PERSISTENCE_UNIT_NAME = "feedapp-prototype";

  public static void main(String[] args) {
    try (EntityManagerFactory factory = Persistence.createEntityManagerFactory(
        PERSISTENCE_UNIT_NAME); EntityManager em = factory.createEntityManager()) {
      em.getTransaction().begin();
      createObjects(em);
      em.getTransaction().commit();
    }
  }

  private static void createObjects(EntityManager em) {
    UserDao userDao = new UserDao(em);
    PollDao pollDao = new PollDao(em);
    InviteDao inviteDao = new InviteDao(em, pollDao);
    VoteDao voteDao = new VoteDao(em, pollDao, userDao);

    User eirik = userDao.createUser("eirik.maaseidvaag@gmail.com", "Eirik", "Måseidvåg");

    Poll poll = pollDao.createPoll("Databases", "PostgreSQL is better than MySQL", eirik, true);

    inviteDao.inviteUser(poll.getId(), "eirikmaseidvag@adventuretech.no");

    voteDao.vote(poll.getId(), eirik.getId(), true);
  }
}
