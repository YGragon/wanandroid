package com.dong.wanandroid.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dong.wanandroid.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/2/28.
 */

public class HomeFuncGridViewAdapter extends BaseAdapter {
    private Context mContext ;
    private ArrayList<String> funcTitle = new ArrayList<>() ;
    public HomeFuncGridViewAdapter(Context context, ArrayList<String> titles) {
        this.mContext = context ;
        this.funcTitle = titles ;
    }

    @Override
    public int getCount() {
        return funcTitle.size() > 0 ? funcTitle.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return funcTitle.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.home_func_grid_item,null) ;
        }
        TextView funcTitleTv = convertView.findViewById(R.id.func_title_tv) ;
        funcTitleTv.setText(funcTitle.get(position));
        return convertView;
    }
}
