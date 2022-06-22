package vote;

public abstract class VoteDecorator<C> extends Vote<C>{
    private final Vote<C> vote;

    public VoteDecorator(Vote<C> vote) {
        this.vote = vote;
    }

}
