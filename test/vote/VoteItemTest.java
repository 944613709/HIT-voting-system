package vote;

import static org.junit.jupiter.api.Assertions.*;

import auxiliary.Person;
import org.junit.jupiter.api.Test;

class VoteItemTest {

	// test strategy
	// TODO

	/**
	 *
	 */
	@Test
	void voteItemTest() {
		//��Ժ�ѡ������������ͶƱѡ���ǡ�֧�֡�
		Person person = new Person("����", 15);
		VoteItem<Object> voteItem = new VoteItem<>(person, "֧��");
		String voteValue = voteItem.getVoteValue();
		assertEquals("֧��", voteValue);
		Object candidate = voteItem.getCandidate();
		assertEquals(person, candidate);
	}

	@Test
	void getVoteValueTest() {
		Person person = new Person("person1", 18);
		VoteItem<Object> voteItem = new VoteItem<>(person, "֧��");
		String voteValue = voteItem.getVoteValue();
		assertEquals("֧��", voteValue);
		Object candidate = voteItem.getCandidate();
		assertEquals(person, candidate);
	}

	@Test
	void getCandidateTest() {
		Person person = new Person("person1", 18);
		VoteItem<Object> voteItem = new VoteItem<>(person, "֧��");
		String voteValue = voteItem.getVoteValue();
		assertEquals("֧��", voteValue);
		Object candidate = voteItem.getCandidate();
		assertEquals(person, candidate);
	}

	/**
	 * ���Թ��췽��
	 */
	@Test
	public void VoteItemTest() {
		Person person = new Person("person1", 18);
		VoteItem<Object> voteItem = new VoteItem<>(person, "֧��");
		String voteValue = voteItem.getVoteValue();
		assertEquals("֧��", voteValue);
		Object candidate = voteItem.getCandidate();
		assertEquals(person, candidate);
	}

}
