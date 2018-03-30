package zzq.rvsuspensionbartest;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by zengzhiqi on 2018/3/27.
 */

public class TextViewBinder extends ItemViewBinder<TextViewBean,TextViewBinder.ViewHoler> {

    @NonNull
    @Override
    protected ViewHoler onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_text_layout, parent, false);
        return new ViewHoler(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHoler holder, @NonNull TextViewBean item) {
        holder.textView.setText(item.getText());
        Resources resources= holder.textView.getContext().getResources();
        if (item instanceof Post) {
            holder.textView.setGravity(Gravity.LEFT);
            holder.textView.setBackgroundColor(resources.getColor(R.color.colorAccent));
        } else {
            holder.textView.setGravity(Gravity.RIGHT);
            holder.textView.setBackgroundColor(resources.getColor(R.color.colorPrimary));
        }
    }

    static class ViewHoler extends RecyclerView.ViewHolder{
        @NonNull
        final TextView textView;

        public ViewHoler(View itemView) {
            super(itemView);
            textView= (TextView) itemView;
        }
    }


}
