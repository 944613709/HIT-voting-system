package pattern;

import poll.GeneralPollImpl;
import poll.Poll;
import vote.Vote;

import java.util.Map;

/**
 * ������11�����ǵ�������ͶƱӦ�ÿ��ܳ��ָ���Ķ�ͶƱ����Ĵ�������
 * ADT ��������� Visitor ���ģʽ��Ԥ���ýӿ���չ�¹��ܡ����ڸ����ģʽ��
 * ʵ��һ��������Ϸ�ѡƱ������ѡƱ����ռ����������չ���ܡ��� Poll Ŀǰ��
 * ������Ͻ�����չ��ʵ���������ģʽ����չ���ܡ�
 *
 * ������ ����Ϸ�ѡƱ������ѡƱ����ռ����
 */
public class CountProportionVisitor<C> extends Visitor<C>{

    private Double data;//�Ϸ�ѡƱ������ѡƱ����ռ����

    /**
     * ����Ϸ�ѡƱ������ѡƱ����ռ����
     * @param generalPoll
     */
    @Override
    public void visit(GeneralPollImpl<C> generalPoll){
        Double allTickets = 0.0;
        Double legalTickets = 0.0;
        Map<Vote<C>, Boolean> voteIsLegal = generalPoll.getVoteIsLegal();
        for (Map.Entry<Vote<C>, Boolean> entry : voteIsLegal.entrySet()) {
            allTickets++;
            Vote<C> vote = entry.getKey();
            Boolean isLegal = entry.getValue();
            if(isLegal)
                legalTickets++;
        }
        data = legalTickets/allTickets;
    }

    /**
     * ���data
     * @return
     */
    @Override
    public double getData() {
        return data;
    }
}
