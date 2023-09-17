package no.hvl.dat250.feedapp.prototype;

import org.junit.Before;
import org.junit.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import no.hvl.dat250.feedapp.prototype.models.*;
import no.hvl.dat250.feedapp.prototype.models.Poll.Status;
import no.hvl.dat250.feedapp.prototype.services.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class FeedAppTest {

    private EntityManagerFactory factory;

    @Before
    public void setUp() {
        factory = Persistence.createEntityManagerFactory(Main.PERSISTENCE_UNIT_NAME);
    }

    @Test
    public void testDomainModelPersistence() {
        // Run the main class to persist the objects.
        Main.main(new String[] {});

        EntityManager em = factory.createEntityManager();
        UserDao userDao = new UserDao(em);
        PollDao pollDao = new PollDao(em);
        InviteDao inviteDao = new InviteDao(em, pollDao);

        // Load user
        User eirik = userDao.getUserByEmail("eirik.maaseidvaag@gmail.com");

        // Test user
        assertThat(eirik.getGivenName(), is("Eirik"));
        assertThat(eirik.getFamilyName(), is("Måseidvåg"));

        // Test poll
        assertThat(eirik.getPolls().size(), is(1));
        Poll poll = eirik.getPolls().iterator().next();

        assertThat(poll.getTitle(), is("Databases"));
        assertThat(poll.getQuestion(), is("PostgreSQL is better than MySQL"));
        assertThat(poll.getStatus(), is(Status.Before));
        assertThat(poll.isPrivate(), is(true));

        // Test invite
        assertThat(poll.getInvites().size(), is(1));
        Invite invite = inviteDao.getInvite(poll.getId(), "eirikmaseidvag@adventuretech.no");
        assertThat(invite.getEmail(), is("eirikmaseidvag@adventuretech.no"));

        // Test vote
        assertThat(poll.getVotes().size(), is(1));
        Vote vote = poll.getVotes().iterator().next();
        assertThat(vote.getAnswer(), is(true));

        // Test poll answers
        assertThat(pollDao.getPollYesById(poll.getId()), is(1));
        assertThat(pollDao.getPollNoById(poll.getId()), is(0));
    }
}