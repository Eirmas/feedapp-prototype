package no.hvl.dat250.feedapp.prototype.services;

import jakarta.persistence.EntityManager;
import no.hvl.dat250.feedapp.prototype.models.User;

public class UserDao {
  private EntityManager em;

  public UserDao(EntityManager em) {
    this.em = em;
  }

  public User getUserById(Long id) {
    return em.find(User.class, id);
  }

  public User getUserByEmail(String email) {
    return em.createQuery("SELECT u FROM users u WHERE u.email = :email", User.class)
        .setParameter("email", email)
        .getSingleResult();
  }

  public User createUser(String email, String givenName, String familyName) {
    User user = new User();
    user.setEmail(email);
    user.setGivenName(givenName);
    user.setFamilyName(familyName);
    em.persist(user);
    return user;
  }

  public User updateUser(User user) {
    em.merge(user);
    return user;
  }

  public void deleteUserById(Long id) {
    em.createQuery("DELETE FROM users u WHERE u.id = :id")
        .setParameter("id", id)
        .executeUpdate();
  }
}
