package com.example.frontend;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {

    private List<ExampleItem> mExampleList;
    private EntryListener mEntryListener;


    public static class ExampleViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;
        EntryListener myEntryListener;

        public ExampleViewHolder(View itemView,EntryListener pEntryListener) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageView);
            mTextView1 = itemView.findViewById(R.id.textView);
            mTextView2 = itemView.findViewById(R.id.textView2);

            myEntryListener = pEntryListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myEntryListener.onEntryClick(getAdapterPosition());
        }
    }
    public ExampleAdapter(List<ExampleItem> exampleList, EntryListener pEntryListener) {
        mExampleList = exampleList;
        mEntryListener = pEntryListener;
    }
    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.example_item, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v, mEntryListener);


        return evh;
    }
    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        ExampleItem currentItem = mExampleList.get(position);
        holder.mImageView.setImageResource(currentItem.getImageResource());
        holder.mTextView1.setText(currentItem.getText1());
        holder.mTextView2.setText(currentItem.getText2());
    }
    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    public interface EntryListener{
        void onEntryClick(int position);
    }
}