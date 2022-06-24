package pattern;

import vote.Vote;

import java.util.Map;

public interface SelectionStrategy<C> {
    /**
     * ���ǵ���ͬӦ����ʹ�õļ�Ʊ�������������죬�����ǵ�δ�����ܳ��ֵ���
     * ��Ӧ���л������µļ�Ʊ������ʹ�� Strategy ���ģʽ����������
     * @param statistics
     * @param quantity
     * @param voteIsLegal
     * @return
     */
    Map<C, Double> selection(Map<C, Double> statistics,int quantity,Map<Vote<C>,Boolean> voteIsLegal);

}
