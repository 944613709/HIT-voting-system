package poll;

public class InvalidVoteException extends Exception{
    public InvalidVoteException() {
        super("一张选票中出现了本次投票不允许的选项值");
    }
}
