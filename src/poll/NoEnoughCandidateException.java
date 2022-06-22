package poll;

public class NoEnoughCandidateException extends Exception{
    public NoEnoughCandidateException() {
        super("一张选票中没有包含本次投票活动中的所有候选人");
    }
}
