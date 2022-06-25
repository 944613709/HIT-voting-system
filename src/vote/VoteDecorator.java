package vote;

public abstract class VoteDecorator<C>implements VoteInterface<C>{
    private final Vote<C> vote;

    public VoteDecorator(Vote<C> vote) {
        this.vote = vote;
    }

    /**
     * //�������ӣ�������Vote���þ��еķ���
     */
    @Override
    public void voteFunction() {
        //�������ӣ�������Vote���þ��еķ���
    }
}
