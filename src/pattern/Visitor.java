package pattern;

import poll.GeneralPollImpl;
import poll.Poll;

public abstract class Visitor<C> {
    public abstract Double visit(GeneralPollImpl<C> generalPoll);
}
