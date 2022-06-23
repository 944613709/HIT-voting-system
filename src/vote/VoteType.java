package vote;

import java.util.*;

//immutable
public class VoteType {

	// keyΪѡ������valueΪѡ������Ӧ�ķ���
	private Map<String, Integer> options = new HashMap<>();

	// Rep Invariants
	// ѡ����key����Ϊ""�����в�������ֿո�.
	// Abstract Function
	// AF(String,Integer)->ѡ������keyΪѡ������valueΪѡ������Ӧ�ķ���
	// Safety from Rep Exposure
	// ���е���������˽�е�
	// ��Collections.unmodifiableMap()ת��Ϊ���ɱ����ͷ��ظ��ⲿ

	/**
	 * String����Ϊ""������Ϊnull
	 * @return
	 */
	//TODO
	private void checkRep() {
		for (Map.Entry<String, Integer> stringIntegerEntry : options.entrySet()) {
			String keyStr = stringIntegerEntry.getKey();
			assert !keyStr.equals("");
			assert !keyStr.contains(" ");
		}
	}

	/**
	 * ����һ��ͶƱ���Ͷ���
	 * 
	 * ����������Ƹ÷��������õĲ���
	 */
	public VoteType(Map<String,Integer> options) {
		this.options=new HashMap<>(options);
		checkRep();
	}

	/**
	 * ���������ض��﷨������ַ���������һ��ͶƱ���Ͷ���
	 *
	 * @param regex ��ѭ�ض��﷨�ġ�����ͶƱ������Ϣ���ַ�����������12�ٿ��ǣ�
	 */
	//	���ַ�
	//	�����﷨�������£�
	//			��ϲ����(2)|����ϲ����(0)|������ν��(1)
	//	���У���˫���������������ֲ�����һ��ͶƱѡ����Ȳ����� 5�����в�
	//	������ֿո���������������������ͶƱѡ���Ӧ�ķ�������������������0
	//	�����������ܴ�С��������������Ҫʹ�á�+��������������Ҫʹ�á�-������ͬ
	//	��ͶƱѡ��֮���á�|��������
	//	Ҳ������������ʽ��
	//			��֧�֡�|�����ԡ�|����Ȩ��
	//	�������������ȣ�������û�з��������������������ͶƱѡ���Ȩ����һ����
	public VoteType(String regex) {
		//TODO
		checkRep();
	}

	/**
	 * �ж�һ��ͶƱѡ���Ƿ�Ϸ�������Poll�ж�ѡƱ�ĺϷ��Լ�飩
	 * 
	 * ���磬ͶƱ�˸�����ͶƱѡ���ǡ�Strongly reject������options�в��������ѡ�������ǷǷ���
	 * 
	 * ���ܸĶ��÷����Ĳ���
	 * 
	 * @param option һ��ͶƱѡ��
	 * @return �Ϸ���true������false
	 */
	public boolean checkLegality(String option) {
		for (Map.Entry<String, Integer> stringIntegerEntry : options.entrySet())
		{
			if(option.equals(stringIntegerEntry.getKey()))
				return true;
		}
		return false;
	}

	/**
	 * ����һ��ͶƱѡ��õ����Ӧ�ķ���
	 * 
	 * ���磬ͶƱ�˸�����ͶƱѡ���ǡ�֧�֡�����ѯ�õ����Ӧ�ķ�����1
	 * 
	 * ���ܸĶ��÷����Ĳ���
	 * 
	 * @param option һ��ͶƱ��ȡֵ
	 * @return ��ѡ������Ӧ�ķ���
	 */
	//Ҫ�������ڸ�ѡ��
	public int getScoreByOption(String option) {
		return options.get(option);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		VoteType voteType = (VoteType) o;
		return Objects.equals(options, voteType.options);
	}

	@Override
	public int hashCode() {
		return Objects.hash(options);
	}

	/**
	 * �������,����options
	 * @return	options���ɱ�����
	 */
	public Map<String, Integer> getOptions() {
		return Collections.unmodifiableMap(options);
	}
}
