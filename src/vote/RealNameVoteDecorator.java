package vote;

import auxiliary.Voter;

public class RealNameVoteDecorator<C> extends VoteDecorator<C>{
    //投票人
    private Voter voter;

    public RealNameVoteDecorator(Vote<C> vote, Voter voter) {
        super(vote);
        this.voter = voter;
    }

    /**
     * 用于获得投票人信息
     * @return
     */
    public Voter getVoter() {
        return this.voter;
    }

    /**
     * 仅举例子，代表是Vote本该具有的方法
     */
    @Override
    public void voteFunction() {
        super.voteFunction();
    }
}
