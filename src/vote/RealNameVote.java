package vote;

import auxiliary.Voter;

import java.util.Calendar;
import java.util.Set;

//immutable
public class RealNameVote<C> extends Vote<C>{

	//ͶƱ��
	private Voter voter;
	//TODO
	// //��д��
	public RealNameVote(Set<VoteItem<C>> voteItems, Calendar date) {
		super(voteItems,date);
		// TODO
	}

	public Voter getVoter() {
		return this.voter;
	}
}
