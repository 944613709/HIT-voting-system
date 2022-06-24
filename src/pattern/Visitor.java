package pattern;

import poll.GeneralPollImpl;
import poll.Poll;

/**
 * Visitor ���ģʽ��Ԥ���ýӿ���չ�¹���
 * @param <C>
 */
public abstract class Visitor<C> {

    public abstract double getData();
    public abstract void visit(GeneralPollImpl<C> generalPoll);
}
