package vote;

import java.util.Objects;

//immutable
public class VoteItem<C> {

	// ��ͶƱ������Եĺ�ѡ����
	private C candidate;
	// �Ժ�ѡ����ľ���ͶƱѡ����硰֧�֡��������ԡ���
	// ���豣�������ֵ����Ҫʱ���Դ�ͶƱ���VoteType�����в�ѯ�õ�
	private String value;

	// Rep Invariants
	// ѡ����value����Ϊ""�����в�������ֿո�.
	// Abstract Function
	// AF(candidate,value),��Ӧһ��ͶƱ��Ͷ����ѡ����candidateƱΪvalue.������һ
	//��ͶƱ�˶�һ����ѡ����ľ�������
	// Safety from Rep Exposure
	// ���е���������˽�е�

	/**
	 *
	 * @return
	 */
	private void checkRep() {
		assert !value.equals("");
		assert !value.contains(" ");
	}

	/**
	 * ����һ��ͶƱ����� ���磺��Ժ�ѡ������������ͶƱѡ���ǡ�֧�֡�
	 * 
	 * @param candidate ����Եĺ�ѡ����
	 * @param value     ��������ͶƱѡ��
	 */
	public VoteItem(C candidate, String value) {
		this.candidate = candidate;
		this.value = value;
		checkRep();
	}

	/**
	 * �õ���ͶƱѡ��ľ���ͶƱ��
	 * 
	 * @return
	 */
	public String getVoteValue() {
		return this.value;
	}

	/**
	 * �õ���ͶƱѡ������Ӧ�ĺ�ѡ��
	 * 
	 * @return
	 */
	public C getCandidate() {
		return this.candidate;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		VoteItem<?> voteItem = (VoteItem<?>) o;
		return Objects.equals(candidate, voteItem.candidate) && Objects.equals(value, voteItem.value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(candidate, value);
	}

	@Override
	public String toString() {
		return "VoteItem{" +
				"candidate=" + candidate +
				", value='" + value + '\'' +
				'}';
	}
}
