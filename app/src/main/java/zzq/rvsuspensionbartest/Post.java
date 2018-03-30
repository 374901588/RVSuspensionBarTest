package zzq.rvsuspensionbartest;

import java.util.List;

/**
 * Created by zengzhiqi on 2018/3/29.
 */

public class Post extends TextViewBean {
    public List<Comment> comments;

    public Post(String text) {
        super(text);
    }
}
