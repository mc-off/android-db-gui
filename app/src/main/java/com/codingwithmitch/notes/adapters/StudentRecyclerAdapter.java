package com.codingwithmitch.notes.adapters;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codingwithmitch.notes.R;
import com.codingwithmitch.notes.models.Student;

import java.util.ArrayList;

public class StudentRecyclerAdapter extends RecyclerView.Adapter<StudentRecyclerAdapter.ViewHolder> {

    private static final String TAG = "PersonRecyclerAdapter";

    private ArrayList<Student> studentArrayList = new ArrayList<>();
    private OnStudentListener mOnStudentListener;

    public StudentRecyclerAdapter(ArrayList<Student> studentArrayList, OnStudentListener mOnStudentListener) {
        this.studentArrayList = studentArrayList;
        this.mOnStudentListener = mOnStudentListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_row, parent, false);
        return new ViewHolder(view, mOnStudentListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        try{
            holder.id.setText(String.valueOf(studentArrayList.get(position).getId()));
            holder.personId.setText(String.valueOf(studentArrayList.get(position).getPersonId()));
            holder.university.setText(studentArrayList.get(position).getUniversity());
        }catch (NullPointerException e){
            Log.e(TAG, "onBindViewHolder: Null Pointer: " + e.getMessage() );
        }
    }

    @Override
    public int getItemCount() {
        return studentArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView id;
        TextView personId;
        TextView university;

        OnStudentListener mOnStudentListener;

        public ViewHolder(View itemView, OnStudentListener mOnStudentListener) {
            super(itemView);
            id = itemView.findViewById(R.id.id);
            personId = itemView.findViewById(R.id.person_id);
            university = itemView.findViewById(R.id.university);
            this.mOnStudentListener = mOnStudentListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.d(TAG, "onClick: " + getAdapterPosition());
            mOnStudentListener.onNoteClick(getAdapterPosition());
        }
    }

    public interface OnStudentListener {
        void onNoteClick(int position);
    }
 }


















