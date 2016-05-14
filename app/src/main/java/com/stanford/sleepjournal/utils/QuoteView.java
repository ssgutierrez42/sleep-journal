package com.stanford.sleepjournal.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.stanford.sleepjournal.R;

/**
 * Created by Santiago Gutierrez on 5/8/16.
 */
public class QuoteView extends LinearLayout {

    private TextView mQuote;
    private TextView mAuthor;

    public QuoteView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public QuoteView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    public QuoteView(Context context) {
        super(context);
        initialize();
    }

    private void initialize(){
        inflate(getContext(), R.layout.object_quote_view, this);
        this.mQuote = (TextView) findViewById(R.id.object_quote_item);
        this.mAuthor = (TextView) findViewById(R.id.object_quote_author);
    }

    public void setQuote(String text, String author){
        if(mQuote==null || mAuthor == null) return;
        mQuote.setText(String.format(getContext().getString(R.string.format_quote), text));
        mAuthor.setText("-" + author);
    }

}
