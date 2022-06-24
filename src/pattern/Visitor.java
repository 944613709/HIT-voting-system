package pattern;

import poll.GeneralPollImpl;
import poll.Poll;

/**
 * Visitor 设计模式，预留好接口扩展新功能
 * @param <C>
 */
public abstract class Visitor<C> {

    public abstract double getData();
    public abstract void visit(GeneralPollImpl<C> generalPoll);
}
