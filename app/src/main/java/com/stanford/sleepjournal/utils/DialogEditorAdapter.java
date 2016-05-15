package com.stanford.sleepjournal.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.stanford.sleepjournal.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Santiago Gutierrez on 5/14/16.
 */
public class DialogEditorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Editable> mValues;

    private OnItemClickListener mListener;
    private final Context mContext;

    public DialogEditorAdapter(OnItemClickListener listener, Context context) {
        this.mValues = new ArrayList<>();
        this.mListener = listener;
        this.mContext = context;
    }

    public void setData(List<Editable> data) {
        this.mValues = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        //TODO
        return Editable.TYPE_TIME;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class TimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView mActionText;
        public final TextView mActionButton;
        public Editable mItem;

        public TimeHolder(View view) {
            super(view);
            mActionText = (TextView) view.findViewById(R.id.dialog_info_asleep_text);
            mActionButton = (TextView) view.findViewById(R.id.dialog_info_asleep);

            mActionButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
        }

    }

    public interface OnItemClickListener {
        void itemClicked(Object value, int type);
    }
}
