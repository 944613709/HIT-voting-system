package vote;

import static org.junit.jupiter.api.Assertions.*;

import auxiliary.Person;
import org.junit.jupiter.api.Test;

class VoteItemTest {

	// test strategy
//
// voteItem���캯������
//  ���Բ��ԶԱ�voteValue��candidate
//  1.����������value
//  2.δ����������value
//  2.1valueΪ��
//  2.2value���ո�
//
//	 ����getVoteValue
//	 ���Բ��ԶԱ�VoteValue
//
//	 ����getCandidate
//	  �ԱȲ��ԶԱ�Candidate
//


	/**
	 *voteItem���캯������
	 * ���Բ��ԶԱ�voteValue��candidate
	 * 1.����������value
	 * 2.δ����������value
	 * 2.1valueΪ��
	 * 2.2value���ո�
	 * */
	@Test
	void voteItemTest() {
		//��Ժ�ѡ������������ͶƱѡ���ǡ�֧�֡�
		Person person = new Person("����", 15);
		VoteItem<Object> voteItem = new VoteItem<>(person, "֧��");
		String voteValue = voteItem.getVoteValue();
		assertEquals("֧��", voteValue);
		Object candidate = voteItem.getCandidate();
		assertEquals(person, candidate);
		//����δ����������value,��Ϊassert������ʾ����ע��
//		Person person2 = new Person("����", 15);
//		VoteItem<Object> voteItem2 = new VoteItem<>(person2, "֧ ��");
//		String voteValue2 = voteItem2.getVoteValue();
//		Person person3 = new Person("����", 15);
//		VoteItem<Object> voteItem3 = new VoteItem<>(person3, "");
//		String voteValue3 = voteItem3.getVoteValue();

	}

	/**
	 * ����getVoteValue
	 * ���Բ��ԶԱ�VoteValue
	 */
	@Test
	void getVoteValueTest() {
		Person person = new Person("person1", 18);
		VoteItem<Object> voteItem = new VoteItem<>(person, "֧��");
		String voteValue = voteItem.getVoteValue();
		assertEquals("֧��", voteValue);
		Object candidate = voteItem.getCandidate();
	}

	/**
	 * ����getCandidate
	 * �ԱȲ��ԶԱ�Candidate
	 */
	@Test
	void getCandidateTest() {
		Person person = new Person("person1", 18);
		VoteItem<Object> voteItem = new VoteItem<>(person, "֧��");
		String voteValue = voteItem.getVoteValue();
		Object candidate = voteItem.getCandidate();
		assertEquals(person, candidate);
	}

}
