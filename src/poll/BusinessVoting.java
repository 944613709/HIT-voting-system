package poll;

import auxiliary.Proposal;

public class BusinessVoting extends GeneralPollImpl<Proposal> implements Poll<Proposal> {
    // Rep Invariants
    //name����Ϊ����
    //date����Ϊnull
    //quantity>0;
    //��candidates.size()>=1;
    //��Ҫѡ��������quantity>=candidates.size() =statistics.size() =results.size()

    //votes��VoteItem��ͶƱ��ѡ�ˣ�Ҫ�պø��ǵ������е�candidate��ѡ��
    //votes��VoteItem��ͶƱ��ѡ�ˣ�������������ѡ��
    //votes��VoteItem��valueͶƱѡ����ܰ���VoteType.options��Keys����֮���
    //candidates.size()=statistics.size()=results.size()

    //statistics��results��keyҪ�պø��ǵ������е�candidate��ѡ��
    //statistics��results��key������������ѡ��
    //candidates.size()=statistics.size()=results.size()

    // Abstract Function
    // TODO
    // Safety from Rep Exposure
    // TODO

	//TODO
    super.name =
}