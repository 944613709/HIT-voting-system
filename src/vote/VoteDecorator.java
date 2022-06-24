package vote;

public abstract class VoteDecorator<C>implements VoteInterface<C>{
    private final Vote<C> vote;

    public VoteDecorator(Vote<C> vote) {
        this.vote = vote;
    }

}
