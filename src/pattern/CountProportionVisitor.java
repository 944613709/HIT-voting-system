package pattern;

import poll.Poll;

/**
 * 【任务11】考虑到将来的投票应用可能出现更多的对投票结果的处理，请在
 * ADT 设计中引入 Visitor 设计模式，预留好接口扩展新功能。基于该设计模式，
 * 实现一个“计算合法选票在所有选票中所占比例”的扩展功能。在 Poll 目前代
 * 码基础上进行扩展，实现上述设计模式和扩展功能。
 *
 * 访问者 计算合法选票在所有选票中所占比例
 */
public class CountProportionVisitor extends Visitor{
    @Override
    public void visit(Poll poll) {
        //TODO
    }
}
