package vote;

import auxiliary.Voter;

public class RealNameVoteDecorator<C> extends VoteDecorator<C>{
    //Õ∂∆±»À
    private Voter voter;

    public RealNameVoteDecorator(Vote<C> vote, Voter voter) {
        super(vote);
        this.voter = voter;
    }
    public Voter getVoter() {
        return this.voter;
    }
}
