package com.guiying.module.ui.activity.adapter;

import com.guiying.module.main.R;
import com.guiying.module.ui.activity.base.BaseRVHolder;
import com.guiying.module.ui.activity.base.SelectedAdapter;
import com.voiceai.voicedna.bean.dto.IdnTaskDetail;

import java.text.DecimalFormat;

public class VerificationSuccessAdapter extends SelectedAdapter<IdnTaskDetail.ExecResultItem> {
    public VerificationSuccessAdapter() {
        super(R.layout.adapter_verificationsuccess);
    }

    @Override
    public void onBindVH(BaseRVHolder holder, IdnTaskDetail.ExecResultItem data, int position) {
        DecimalFormat df = new DecimalFormat("#.00");
        String Score = df.format(Double.valueOf(data.getScore()));
        holder.setText(R.id.tv_name, data.getTargetUser());
        holder.setText(R.id.tv_score,  Score+"%");
    }
}
