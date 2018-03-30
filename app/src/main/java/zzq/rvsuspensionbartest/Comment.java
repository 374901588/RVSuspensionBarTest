package zzq.rvsuspensionbartest;

/**
 * Created by zengzhiqi on 2018/3/29.
 */

public class Comment extends TextViewBean{
    private int parentPostPosition;

    public Comment(String text,int parentPostPosition) {
        super(text);
        this.parentPostPosition = parentPostPosition;
    }

    public int getParentPostPosition() {
        return parentPostPosition;
    }

    public void setParentPostPosition(int parentPostPosition) {
        this.parentPostPosition = parentPostPosition;
    }
}
