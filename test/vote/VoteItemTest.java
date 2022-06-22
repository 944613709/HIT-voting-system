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
		//针对候选对象“张三”，投票选项是“支持”
		Person person = new Person("张三", 15);
		VoteItem<Object> voteItem = new VoteItem<>(person, "支持");
		String voteValue = voteItem.getVoteValue();
		assertEquals("支持", voteValue);
		Object candidate = voteItem.getCandidate();
		assertEquals(person, candidate);
	}

	@Test
	void getVoteValueTest() {
		Person person = new Person("person1", 18);
		VoteItem<Object> voteItem = new VoteItem<>(person, "支持");
		String voteValue = voteItem.getVoteValue();
		assertEquals("支持", voteValue);
		Object candidate = voteItem.getCandidate();
		assertEquals(person, candidate);
	}

	@Test
	void getCandidateTest() {
		Person person = new Person("person1", 18);
		VoteItem<Object> voteItem = new VoteItem<>(person, "支持");
		String voteValue = voteItem.getVoteValue();
		assertEquals("支持", voteValue);
		Object candidate = voteItem.getCandidate();
		assertEquals(person, candidate);
	}

	/**
	 * 测试构造方法
	 */
	@Test
	public void VoteItemTest() {
		Person person = new Person("person1", 18);
		VoteItem<Object> voteItem = new VoteItem<>(person, "支持");
		String voteValue = voteItem.getVoteValue();
		assertEquals("支持", voteValue);
		Object candidate = voteItem.getCandidate();
		assertEquals(person, candidate);
	}

}
