package com.guiying.module.ui.activity.dialog;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.guiying.module.main.R;
import com.guiying.module.ui.activity.adapter.VerificationSuccessAdapter;
import com.guiying.module.ui.activity.base.MySPManager;
import com.voiceai.voicedna.bean.dto.IdnTaskDetail;

import java.util.List;


public class VerificationSuccessPopup extends PopupWindow implements View.OnClickListener {
    private Activity mActivty;
    private View view;
    private OnConfirm listener;
    private RecyclerView myRecyclerView;
    private VerificationSuccessAdapter adapter;
    private TextView tv_title;
    private int type;
    public VerificationSuccessPopup(Activity context) {
        super(context);
        this.mActivty = context;
        initViewData();
    }
    public VerificationSuccessPopup(Activity context,int type) {
        super(context);
        this.mActivty = context;
        this.type=type;
        initViewData();
    }
    private void initViewData() {
        view = View.inflate(mActivty, R.layout.popup_verificationsuccess, null);
        tv_title=view.findViewById(R.id.tv_title);
        this.setContentView(view);// 设置SelectPicPopupWindow的View
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);// 设置SelectPicPopupWindow弹出窗体的宽
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);// 设置SelectPicPopupWindow弹出窗体的高
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setAnimationStyle(R.style.AnimBelowSkip);// 设置SelectPicPopupWindow弹出窗体动画效果
        ColorDrawable dw = new ColorDrawable(0x00000000);// 实例化一个ColorDrawable颜色为半透明
        this.setBackgroundDrawable(dw);// 设置SelectPicPopupWindow弹出窗体的背景
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出
        view.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        WindowManager.LayoutParams params = mActivty.getWindow().getAttributes();
        params.alpha = 0.3f;
        mActivty.getWindow().setAttributes(params);

        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams params = mActivty.getWindow().getAttributes();
                params.alpha = 1f;
                mActivty.getWindow().setAttributes(params);
            }
        });
        view.findViewById(R.id.tv_send).setOnClickListener(this);
        myRecyclerView = view.findViewById(R.id.myRecyclerView);
        adapter = new VerificationSuccessAdapter();
        GridLayoutManager manager = new GridLayoutManager(mActivty, 1);
        myRecyclerView.setLayoutManager(manager);
        myRecyclerView.setAdapter(adapter);
        if (MySPManager.getAppType()==1){
            if (type == 0) {
                tv_title.setText("验证通过");
            } else if (type == 1) {
                tv_title.setText("验证不通过");
            } else if (type == 2) {
                tv_title.setText("匹配到声纹");
            } else if (type == 3) {
                tv_title.setText("未匹配到声纹");
            }
        }else  if (MySPManager.getAppType()==2){
            if (type==1){
                tv_title.setText("未匹配到声纹");
            }

        }


    }

    public void setData(List<IdnTaskDetail.ExecResultItem> data) {
        adapter.setNewData(data);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_send) {
            if (listener == null)
                return;
            listener.Confirm();
        }

    }

    public interface OnConfirm {
        void Confirm();
    }

    public void setOnConfirm(OnConfirm listener) {
        this.listener = listener;
    }
}
