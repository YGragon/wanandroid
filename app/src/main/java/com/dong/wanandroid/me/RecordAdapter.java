package com.dong.wanandroid.me;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dong.wanandroid.R;
import com.dong.wanandroid.data.read_record.ReadRecordModel;

import java.util.List;

public class RecordAdapter extends BaseQuickAdapter<ReadRecordModel, BaseViewHolder> {

    public RecordAdapter(int layoutResId, @Nullable List<ReadRecordModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ReadRecordModel item) {
        TextView recordTv = helper.getView(R.id.record_content_tv) ;
        recordTv.setText(item.getTitle());
    }
}
