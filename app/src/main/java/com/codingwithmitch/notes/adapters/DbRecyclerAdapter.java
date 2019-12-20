package com.codingwithmitch.notes.adapters;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codingwithmitch.notes.R;

import java.util.ArrayList;

public class DbRecyclerAdapter extends RecyclerView.Adapter<DbRecyclerAdapter.ViewHolder> {

    private static final String TAG = "DbRecyclerAdapter";

    private ArrayList<String> dataBases;
    private OnDbListener mOnDbListener;

    public DbRecyclerAdapter(ArrayList<String> dataBases, OnDbListener mOnDbListener) {
        this.dataBases = dataBases;
        this.mOnDbListener = mOnDbListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_note_list_item, parent, false);
        return new ViewHolder(view, mOnDbListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        try{
            holder.name.setText(dataBases.get(position));

        }catch (NullPointerException e){
            Log.e(TAG, "onBindViewHolder: Null Pointer: " + e.getMessage() );
        }
    }

    @Override
    public int getItemCount() {
        return dataBases.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name;
        OnDbListener mOnDbListener;

        public ViewHolder(View itemView, OnDbListener onDbListener) {
            super(itemView);
            name = itemView.findViewById(R.id.node_title);
            mOnDbListener = onDbListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.d(TAG, "onClick: " + getAdapterPosition());
            mOnDbListener.onNoteClick(getAdapterPosition());
        }
    }

    public interface OnDbListener{
        void onNoteClick(int position);
    }
 }


















