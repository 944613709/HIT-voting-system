package poll;

import auxiliary.Proposal;

public class BusinessVoting extends GeneralPollImpl<Proposal> implements Poll<Proposal> {
    // Rep Invariants
    //name不能为“”
    //date不能为null
    //quantity>0;
    //且candidates.size()>=1;
    //需要选出的数量quantity>=candidates.size() =statistics.size() =results.size()

    //votes的VoteItem的投票候选人，要刚好覆盖到了所有的candidate候选人
    //votes的VoteItem的投票候选人，不能有其他候选人
    //votes的VoteItem的value投票选项，不能包含VoteType.options的Keys集合之外的
    //candidates.size()=statistics.size()=results.size()

    //statistics与results的key要刚好覆盖到了所有的candidate候选人
    //statistics与results的key不能有其他候选人
    //candidates.size()=statistics.size()=results.size()

    // Abstract Function
    // TODO
    // Safety from Rep Exposure
    // TODO

	//TODO
    super.name =
}