package com.akari.quark.ui.helper;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.akari.quark.R;

public class CommentHelper implements TextWatcher, View.OnClickListener {
    private final EditText mContent;
    private final ImageButton mSubmit;
    private final View mRootView;
    private final OnCommentListener mListener;

    public CommentHelper(View view, OnCommentListener listener) {
        mRootView = view;
        mListener = listener;

        mContent = ((EditText) mRootView.findViewById(R.id.content));
        mSubmit = ((ImageButton) mRootView.findViewById(R.id.submit));

        mContent.addTextChangedListener(this);
        mSubmit.setOnClickListener(this);

        afterTextChanged(mContent.getText());
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) { }

    @Override
    public void afterTextChanged(Editable s) {
        final boolean isEmpty = TextUtils.isEmpty(s);
        mSubmit.setEnabled(!isEmpty);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.submit) {
            mListener.onReply(mContent.getText());
        }
    }

    public interface OnCommentListener {
        void onReply(CharSequence content);
    }

    public void setContent(CharSequence content) {
        mContent.setText(content);
    }

    public Editable getContent() {
        return mContent.getText();
    }

    public void requestFocus() {
        mContent.requestFocus();
    }
}
