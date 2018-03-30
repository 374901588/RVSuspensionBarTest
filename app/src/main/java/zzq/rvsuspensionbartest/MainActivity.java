package zzq.rvsuspensionbartest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public class MainActivity extends AppCompatActivity {
    private MultiTypeAdapter adapter;

    private RecyclerView rv;
    private TextView mSuspensionBar;

    private int mCurrentPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv = findViewById(R.id.rv);
        mSuspensionBar = findViewById(R.id.tv);
//        mSuspensionBar.setAlpha(0.5f);

        adapter = new MultiTypeAdapter();

        //模拟数据
        List<Post> list = new ArrayList<>();
        int index = 0;
        int parentPostPos;
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            Post post = new Post("pos = " + index);
            parentPostPos = index;

            list.add(post);
            index++;

            int k = random.nextInt(5);
            post.comments = new ArrayList<>();
            for (int j = 0; j < k; j++) {
                Comment comment = new Comment("pos = " + index, parentPostPos);
                post.comments.add(comment);
                index++;
            }
        }
        final Items items = new Items();
        items.addAll(flattenData(list));
        adapter.setItems(items);

        adapter.register(Post.class, new TextViewBinder());
        adapter.register(Comment.class, new TextViewBinder());

        adapter.setItems(items);
        rv.setAdapter(adapter);

        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) rv.getLayoutManager();
            int mSuspensionHeight;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                mSuspensionHeight = mSuspensionBar.getHeight();

                int firstVisPos = linearLayoutManager.findFirstVisibleItemPosition();

                Object firstVisibleItem = items.get(firstVisPos);
                Object nextItem = items.get(firstVisPos + 1);
                View nextView = linearLayoutManager.findViewByPosition(firstVisPos + 1);


                if (dy > 0) {
                    if (nextItem instanceof Post) {

                        if (nextView.getTop() <= mSuspensionHeight) {
                            //被顶掉的效果
                            mSuspensionBar.setY(-(mSuspensionHeight - nextView.getTop()));
                        } else {
                            mSuspensionBar.setY(0);
                        }
                    }

                    //判断是否需要更新悬浮条
                    if (mCurrentPosition != firstVisPos && firstVisibleItem instanceof Post) {
                        mCurrentPosition = firstVisPos;
                        //更新悬浮条
                        updateSuspensionBar();
                        mSuspensionBar.setY(0);
                    }
                } else {
                    // 1、nextItem -> Post and firstVisibleItem -> Comment       mCurrentPosition = ((Comment) firstVisibleItem).getParentPostPosition()
                    // 2、nextItem -> Post and firstVisibleItem -> Post          mCurrentPosition = firstVisPos
                    // 3、nextItem -> Comment and firstVisibleItem -> Comment    mSuspensionBar 不动
                    // 4、nextItem -> Comment and firstVisibleItem -> Post       mSuspensionBar 不动
                    if (nextItem instanceof Post) {
                        mCurrentPosition = firstVisibleItem instanceof Post ? firstVisPos : ((Comment) firstVisibleItem).getParentPostPosition();
                        updateSuspensionBar();

                        if (nextView.getTop() <= mSuspensionHeight) {
                            //被顶掉的效果
                            mSuspensionBar.setY(-(mSuspensionHeight - nextView.getTop()));
                        } else {
                            mSuspensionBar.setY(0);
                        }
                    }
                }
            }
        });

        //更新悬浮条
        updateSuspensionBar();

        rv.setAdapter(adapter);


    }

    private void updateSuspensionBar() {
        String s = ((TextViewBean) adapter.getItems().get(mCurrentPosition)).getText();
        mSuspensionBar.setText(s);
    }

    private List<Object> flattenData(List<Post> posts) {
        final List<Object> items = new ArrayList<>();
        for (Post post : posts) {
        /* 将 post 加进 items，Binder 内部拿到它的时候，
         * 我们无视它的 comments 内容即可 */
            items.add(post);
        /* 紧接着将 comments 拿出来插入进 items，
         * 评论就能正好处于该条 post 下面 */
            items.addAll(post.comments);
        }
        return items;
    }
}