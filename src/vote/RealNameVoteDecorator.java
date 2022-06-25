package vote;

import auxiliary.Voter;

public class RealNameVoteDecorator<C> extends VoteDecorator<C>{
    //ͶƱ��
    private Voter voter;

    public RealNameVoteDecorator(Vote<C> vote, Voter voter) {
        super(vote);
        this.voter = voter;
    }

    /**
     * ���ڻ��ͶƱ����Ϣ
     * @return
     */
    public Voter getVoter() {
        return this.voter;
    }

    /**
     * �������ӣ�������Vote���þ��еķ���
     */
    @Override
    public void voteFunction() {
        super.voteFunction();
    }
}
