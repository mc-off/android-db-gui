package com.codingwithmitch.notes.adapters;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codingwithmitch.notes.R;
import com.codingwithmitch.notes.models.Person;

import java.util.ArrayList;

public class PersonRecyclerAdapter extends RecyclerView.Adapter<PersonRecyclerAdapter.ViewHolder> {

    private static final String TAG = "PersonRecyclerAdapter";

    private ArrayList<Person> personArrayList = new ArrayList<>();
    private OnPersonListener mOnPersonListener;

    public PersonRecyclerAdapter(ArrayList<Person> personArrayList, OnPersonListener mOnPersonListener) {
        this.personArrayList = personArrayList;
        this.mOnPersonListener = mOnPersonListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_row, parent, false);
        return new ViewHolder(view, mOnPersonListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        try{
            holder.name.setText(personArrayList.get(position).getName());
            holder.surname.setText(personArrayList.get(position).getSurname());
            holder.id.setText(String.valueOf(personArrayList.get(position).getId()));
            holder.phone.setText(personArrayList.get(position).getPhone());
            holder.passport.setText(personArrayList.get(position).getPassport());


        }catch (NullPointerException e){
            Log.e(TAG, "onBindViewHolder: Null Pointer: " + e.getMessage() );
        }
    }

    @Override
    public int getItemCount() {
        return personArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name;
        TextView id;
        TextView surname;
        TextView passport;
        TextView phone;
        OnPersonListener mOnPersonListener;

        public ViewHolder(View itemView, OnPersonListener mOnPersonListener) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            surname = itemView.findViewById(R.id.surname);
            id = itemView.findViewById(R.id.id);
            passport = itemView.findViewById(R.id.passport);
            phone = itemView.findViewById(R.id.phone);
            this.mOnPersonListener = mOnPersonListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.d(TAG, "onClick: " + getAdapterPosition());
            mOnPersonListener.onNoteClick(getAdapterPosition());
        }
    }

    public interface OnPersonListener{
        void onNoteClick(int position);
    }
 }


















