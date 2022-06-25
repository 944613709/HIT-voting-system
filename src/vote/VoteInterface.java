package vote;
/**
 * 这个本来应该是要求Poll实现的
 * （根据装饰者模式要求，Decorator抽象类应该要实现Component接口，因此为了切合装饰者模式的设计而新加，需要修改整体设计构造）
 */
public interface VoteInterface<C> {


    /**
     * //仅举例子，代表是Vote本该具有的方法
     */
    void voteFunction();
}
