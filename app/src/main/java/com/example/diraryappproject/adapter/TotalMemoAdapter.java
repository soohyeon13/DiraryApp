package com.example.diraryappproject.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.diraryappproject.R;
import com.example.diraryappproject.data.MemoCollection;

import java.util.List;

public class TotalMemoAdapter extends RecyclerView.Adapter<TotalMemoAdapter.MemoViewHolder> {

    private final Activity context;
    private final List<MemoCollection> memoList;

    public TotalMemoAdapter(Activity context, List<MemoCollection> memoList) {
        this.context = context;
        this.memoList = memoList;
    }
    @NonNull
    @Override
    public MemoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_total_item,viewGroup,false);
        MemoViewHolder mvh = new MemoViewHolder(view,context);
        return mvh;
    }

    @Override
    public void onBindViewHolder(@NonNull MemoViewHolder memoViewHolder, int i) {
        memoViewHolder.memoTotalTitle.setText("제목 :" +" "+memoList.get(i).getTitle());
        memoViewHolder.memoTotalDate.setText("날짜 :" + " " + memoList.get(i).getDate());
    }

    @Override
    public int getItemCount() {
        return memoList.size();
    }

    public class MemoViewHolder extends RecyclerView.ViewHolder {
        TextView memoTotalTitle,memoTotalDate;
        public MemoViewHolder(@NonNull View itemView, Activity context) {
            super(itemView);
            memoTotalTitle = itemView.findViewById(R.id.total_title);
            memoTotalDate = itemView.findViewById(R.id.total_date);
        }
    }
}
