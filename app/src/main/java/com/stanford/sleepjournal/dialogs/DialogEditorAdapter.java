package com.stanford.sleepjournal.dialogs;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import com.stanford.sleepjournal.R;
import com.stanford.sleepjournal.utils.Editable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Santiago Gutierrez on 5/14/16.
 */
public class DialogEditorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Editable> mValues;

    private OnItemClickListener mListener;
    private final Activity mContext;

    public DialogEditorAdapter(Activity context) {
        this.mValues = new ArrayList<>();
        this.mListener = (OnItemClickListener) context;
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
        switch (viewType){
            case Editable.TYPE_TIME:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_info_editor, parent, false);
                return new TimeHolder(view);
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_info_editor, parent, false);
                return new TimeHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if(viewHolder.getItemViewType() == Editable.TYPE_TIME){
            TimeHolder holder = (TimeHolder) viewHolder;
            holder.mItem = mValues.get(position);
            holder.mActionText.setText(holder.mItem.getName());
            holder.mActionButton.setText(holder.mItem.getDefault());
        }
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
            Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);
            TimePickerDialog picker = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    String hour = "" + leftPad(hourOfDay) + ":" + leftPad(minute);
                    mActionButton.setText(hour);
                    mListener.itemClicked(mItem, hour);
                }
            }, hour, minute, false);
            picker.show();
        }

    }

    public String leftPad(int val){
        if(val < 10) return "0" + val;
        return String.valueOf(val);
    }

    public interface OnItemClickListener {
        void itemClicked(Editable editable, String value);
    }
}