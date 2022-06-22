package poll;

public class InvalidCadidatesException extends Exception{
    public InvalidCadidatesException() {
        super("一张选票中包含了不在本次投票活动中的候选人");
    }
}
