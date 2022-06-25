package pattern;

import poll.GeneralPollImpl;
import poll.Poll;
import vote.Vote;

import java.util.Map;

/**
 * 【任务11】考虑到将来的投票应用可能出现更多的对投票结果的处理，请在
 * ADT 设计中引入 Visitor 设计模式，预留好接口扩展新功能。基于该设计模式，
 * 实现一个“计算合法选票在所有选票中所占比例”的扩展功能。在 Poll 目前代
 * 码基础上进行扩展，实现上述设计模式和扩展功能。
 *
 * 访问者 计算合法选票在所有选票中所占比例
 */
public class CountProportionVisitor<C> extends Visitor<C>{

    private Double data;//合法选票在所有选票中所占比例

    /**
     * 计算合法选票在所有选票中所占比例
     * @param generalPoll
     */
    @Override
    public void visit(GeneralPollImpl<C> generalPoll){
        Double allTickets = 0.0;
        Double legalTickets = 0.0;
        Map<Vote<C>, Boolean> voteIsLegal = generalPoll.getVoteIsLegal();
        for (Map.Entry<Vote<C>, Boolean> entry : voteIsLegal.entrySet()) {
            allTickets++;
            Vote<C> vote = entry.getKey();
            Boolean isLegal = entry.getValue();
            if(isLegal)
                legalTickets++;
        }
        data = legalTickets/allTickets;
    }

    /**
     * 获得data
     * @return
     */
    @Override
    public double getData() {
        return data;
    }
}
