package poll;

public class InvalidCadidatesException extends Exception{
    /**
     * 一张选票中包含了不在本次投票活动中的候选人
     */
    public InvalidCadidatesException() {
        super("一张选票中包含了不在本次投票活动中的候选人");
    }
}
