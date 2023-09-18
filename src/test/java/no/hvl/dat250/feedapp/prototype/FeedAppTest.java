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

import java.util.Iterator;

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
        VoteDao voteDao = new VoteDao(em, pollDao, userDao, inviteDao);

        // Load user
        User eirik = userDao.getUserByEmail("eirik.maaseidvaag@gmail.com");

        // Test user
        assertThat(eirik.getGivenName(), is("Eirik"));
        assertThat(eirik.getFamilyName(), is("Måseidvåg"));

        // Test poll
        assertThat(eirik.getPolls().size(), is(2));
        Iterator<Poll> it = eirik.getPolls().iterator();
        Poll poll = it.next();

        assertThat(poll.getTitle(), is("Databases"));
        assertThat(poll.getQuestion(), is("PostgreSQL is better than MySQL"));
        assertThat(poll.getStatus(), is(Status.Before));
        assertThat(poll.isPrivate(), is(false));

        // Test invite
        assertThat(poll.getInvites().size(), is(1));
        Invite invite = inviteDao.getInvite(poll.getId(), "eirikmaseidvag@adventuretech.no");
        assertThat(invite.getEmail(), is("eirikmaseidvag@adventuretech.no"));

        // Test votes
        assertThat(pollDao.getAnswerCountById(poll.getId()), is(4));
        assertThat(pollDao.getPollYesById(poll.getId()), is(3));
        assertThat(pollDao.getPollNoById(poll.getId()), is(1));

        // Test private poll
        Poll poll2 = it.next();
        assertThat(poll2.getTitle(), is("Towns"));
        assertThat(poll2.getQuestion(), is("Ålesund is better than Bergen"));
        assertThat(poll2.getStatus(), is(Status.Before));
        assertThat(poll2.isPrivate(), is(true));

        // assert voting on poll without invite throws exception
        try {
            voteDao.vote(poll2.getId(), true);
        } catch (RuntimeException e) {
            assertThat(e.getMessage(), is("User does not have access to this poll"));
        }
    }
}