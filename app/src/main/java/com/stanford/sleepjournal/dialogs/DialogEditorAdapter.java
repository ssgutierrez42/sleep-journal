package com.stanford.sleepjournal.dialogs;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import com.stanford.sleepjournal.R;
import com.stanford.sleepjournal.utils.Editable;
import com.vi.swipenumberpicker.OnValueChangeListener;
import com.vi.swipenumberpicker.SwipeNumberPicker;

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
        return mValues.get(position).getType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType){
            case Editable.TYPE_TIME:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_time_editor, parent, false);
                return new TimeHolder(view);
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_number_editor, parent, false);
                return new NumberHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()){
            case Editable.TYPE_TIME:
                TimeHolder holder = (TimeHolder) viewHolder;
                holder.mItem = mValues.get(position);
                holder.mActionText.setText(holder.mItem.getName());
                holder.mActionButton.setText(holder.mItem.getDefault());
                break;
            case Editable.TYPE_NUMBER:
                NumberHolder num = (NumberHolder) viewHolder;
                num.mItem = mValues.get(position);
                num.mActionText.setText(num.mItem.getName());

                try {
                    num.mActionPicker.setValue(Integer.parseInt(num.mItem.getDefault()), true);
                } catch (NumberFormatException e){
                    e.printStackTrace();
                    num.mActionPicker.setValue(0, true);
                }
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class NumberHolder extends RecyclerView.ViewHolder implements OnValueChangeListener {
        public final TextView mActionText;
        public final SwipeNumberPicker mActionPicker;
        public Editable mItem;

        public NumberHolder(View view) {
            super(view);
            mActionText = (TextView) view.findViewById(R.id.dialog_info_text);
            mActionPicker = (SwipeNumberPicker) view.findViewById(R.id.dialog_info_button);
            mActionPicker.setOnValueChangeListener(this);
        }

        @Override
        public boolean onValueChange(SwipeNumberPicker view, int oldValue, int newValue) {
            if(newValue > 0) view.setMinValue(1);
            mListener.itemClicked(mItem, String.valueOf(newValue)); //could check oldval == newval but want to allow zero
            return true;
        }
    }

    public class TimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView mActionText;
        public final TextView mActionButton;
        public Editable mItem;

        public TimeHolder(View view) {
            super(view);
            mActionText = (TextView) view.findViewById(R.id.dialog_info_text);
            mActionButton = (TextView) view.findViewById(R.id.dialog_info_button);

            mActionButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Calendar c = Calendar.getInstance();
            TimePickerDialog picker = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    String time = get12HrTime(hourOfDay, minute);
                    mActionButton.setText(time);
                    mListener.itemClicked(mItem, time);
                }
            }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), false);
            picker.show();
        }

        private String get12HrTime(int hour, int minute){
            String amPm = "";

            Calendar datetime = Calendar.getInstance();
            datetime.set(Calendar.HOUR_OF_DAY, hour);
            datetime.set(Calendar.MINUTE, minute);

            if (datetime.get(Calendar.AM_PM) == Calendar.AM)
                amPm = "AM";
            else if (datetime.get(Calendar.AM_PM) == Calendar.PM)
                amPm = "PM";

            int hours = (datetime.get(Calendar.HOUR) == 0) ? 12 : datetime.get(Calendar.HOUR);
            int minutes = datetime.get(Calendar.MINUTE);
            return leftPad(hours) + ":" + leftPad(minutes) + " " + amPm;
        }

    }

    public static String leftPad(int val){
        if(val < 10) return "0" + val;
        return String.valueOf(val);
    }

    public interface OnItemClickListener {
        void itemClicked(Editable editable, String value);
    }
}
