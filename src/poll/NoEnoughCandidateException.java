package poll;

/**
 * 一张选票中没有包含本次投票活动中的所有候选人
 */
public class NoEnoughCandidateException extends Exception{
    /**
     * 一张选票中没有包含本次投票活动中的所有候选人
     */
    public NoEnoughCandidateException() {
        super("一张选票中没有包含本次投票活动中的所有候选人");
    }
}
