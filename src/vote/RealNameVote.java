package vote;

import auxiliary.Voter;

import java.util.Calendar;
import java.util.Set;

//immutable
public class RealNameVote<C> extends Vote<C>{

	//Õ∂∆±»À
	private Voter voter;
	public RealNameVote(Set<VoteItem<C>> voteItems,Calendar date,Voter voter) {
		super(voteItems,date);
		this.voter=voter;
	}
	public Voter getVoter() {
		return this.voter;
	}
}
