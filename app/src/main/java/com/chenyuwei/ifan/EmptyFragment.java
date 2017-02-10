package com.chenyuwei.ifan;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by vivi on 2016/7/18.
 */
public class EmptyFragment extends Fragment implements View.OnClickListener{

    private int bg;
    private View view;
    private  TextView tvCount;
    private MyHandler handler = new MyHandler();


    class MyHandler extends Handler{
        public void handleMessage(Message msg) {
            if (msg.what == 1){
                if (tvCount != null){
                    tvCount.setText(String.valueOf(msg.arg1));
                }
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_empty,null);
        view.setBackgroundResource(bg);
        view.findViewById(R.id.btnQuit).setOnClickListener(this);
        tvCount = (TextView) view.findViewById(R.id.tvCount);
        new Thread(new Runnable() {
            int count = 1;
            @Override
            public void run() {
                while (true){
                    try {
                        Message message = new Message();
                        message.what = 1;
                        message.arg1 = count++;
                        handler.sendMessage(message);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        return view;
    }

    public EmptyFragment setBg(int bg) {
        this.bg = bg;
        return this;
    }

    @Override
    public void onClick(View v) {
        ((MainActivity) getActivity()).toSmall();
    }
}
