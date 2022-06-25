package vote;

import java.util.*;

//immutable
public class Vote<C> {

	// ȱʡΪ��������ͶƱ��������Ҫ����ͶƱ�˵���Ϣ

	// һ��ͶƱ�˶����к�ѡ�����ͶƱ���
	private Set<VoteItem<C>> voteItems = new HashSet<>();
	// ͶƱʱ��
	private Calendar date = Calendar.getInstance();

	// Rep Invariants
	// voteItems.size()>=1
	// Abstract Function
	// AF(voteItem,date),һ��ͶƱ�˶����к�ѡ�����ͶƱ���,�Ҽ�¼��ͶƱʱ��
	// Safety from Rep Exposure
	// ���е���������˽�е�
	// ��Collections.unmodifiableSet()ת��Ϊ���ɱ����ͷ��ظ��ⲿ
	// ʹ�����(Calendar) date.clone();
	// ���캯���������ʱ������Կ���

	/**
	 * ��鲻����
	 */
	private void checkRep() {
		assert voteItems.size()>=1;
	}

	/**
	 * ����һ��ѡƱ����
	 * 
	 *
	 * @param Set<VoteItem<C>> voteItems	���к�ѡ�����ͶƱ��
	 * @param date    ͶƱʱ��
	 */
	public Vote(Set<VoteItem<C>> voteItems,Calendar date) {
		this.voteItems = new HashSet<>(voteItems);
		this.date= date;
		checkRep();
	}

	/**
	 * �޲������캯��
	 * �����ں��������ģʽ�Ĺ��캯����
	 */
	public Vote() {
	}

	/**
	 * ��ѯ��ѡƱ�а���������ͶƱ��
	 * �Լ���д����
	 * @return ����ͶƱ��
	 */
	public Set<VoteItem<C>> getVoteItems() {
		return Collections.unmodifiableSet(voteItems);
	}

	/**
	 * һ���ض���ѡ���Ƿ������ѡƱ��
	 * �Լ���д����
	 * @param candidate ����ѯ�ĺ�ѡ��
	 * @return �������ú�ѡ�˵�ͶƱ��򷵻�true������false
	 */
	public boolean candidateIncluded(C candidate) {
		for (VoteItem<C> voteItem : voteItems) {
			if(voteItem.getCandidate().equals(candidate))
				return true;
		}
		return false;
	}

	/**
	 * ��ѯͶƱ����
	 * @return  ͶƱ����
	 */
	public Calendar getDate()
	{
		return (Calendar) date.clone();
	}

	@Override
	public String toString() {
		return "Vote{" +
				"voteItems=" + voteItems +
				", date=" + date +
				'}';
	}

}
