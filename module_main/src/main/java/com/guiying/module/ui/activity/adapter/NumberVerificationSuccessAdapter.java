package com.guiying.module.ui.activity.adapter;

import com.guiying.module.main.R;
import com.guiying.module.ui.activity.base.BaseRVHolder;
import com.guiying.module.ui.activity.base.SelectedAdapter;
import com.voiceai.voicedna.bean.dto.DynamicNumerIdnTaskResult;
import com.voiceai.voicedna.bean.dto.IdnTaskDetail;

import java.text.DecimalFormat;

public class NumberVerificationSuccessAdapter extends SelectedAdapter<DynamicNumerIdnTaskResult.ExecResultItem> {
    public NumberVerificationSuccessAdapter() {
        super(R.layout.adapter_verificationsuccess);
    }

    @Override
    public void onBindVH(BaseRVHolder holder, DynamicNumerIdnTaskResult.ExecResultItem data, int position) {
        DecimalFormat df = new DecimalFormat("#.00");
        String Score = df.format(Double.valueOf(data.getScore()));
        holder.setText(R.id.tv_name, data.getTargetUser());
        holder.setText(R.id.tv_score,  Score+"%");
    }
}
