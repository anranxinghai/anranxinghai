package com.channelsoft.android.aboutus.runnable;

import android.content.Context;
import android.os.Handler;

import com.channelsoft.android.aboutus.http.FeedbackCommit;

public class FeedbackCommitRunnable implements Runnable
{

    private String content    = null;
    private String contactTel = null;
    private Handler myHandler;

    public FeedbackCommitRunnable(String content, String contactTel,Handler myHandler)
    {
        super();
        this.myHandler = myHandler;
        this.content = content;
        this.contactTel = contactTel;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getContactTel()
    {
        return contactTel;
    }

    public void setContactTel(String contactTel)
    {
        this.contactTel = contactTel;
    }

    @Override
    public void run()
    {
        // TODO Auto-generated method stub
        FeedbackCommit feedbackCommit = new FeedbackCommit();
        feedbackCommit.conmmitFeedback(myHandler ,content, contactTel);
    }

}
