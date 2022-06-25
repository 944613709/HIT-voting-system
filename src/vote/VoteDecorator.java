package vote;

public abstract class VoteDecorator<C>implements VoteInterface<C>{
    private final Vote<C> vote;

    public VoteDecorator(Vote<C> vote) {
        this.vote = vote;
    }

    /**
     * //仅举例子，代表是Vote本该具有的方法
     */
    @Override
    public void voteFunction() {
        //仅举例子，代表是Vote本该具有的方法
    }
}
