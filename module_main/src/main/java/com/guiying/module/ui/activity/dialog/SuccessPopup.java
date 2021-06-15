package com.guiying.module.ui.activity.dialog;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.guiying.module.main.R;


public class SuccessPopup extends PopupWindow implements View.OnClickListener {
    private Activity mActivty;
    private View view;
    private OnConfirm listener;
    private TextView tv_number, tv_phone, tv_cancel, tv_save;
    private  int user_registration_model;

    public SuccessPopup(Activity context) {
        super(context);
        this.mActivty = context;
        initViewData();
    }

    private void initViewData() {
        view = View.inflate(mActivty, R.layout.popup_success, null);
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
    }


    @Override
    public void onClick(View v) {
   if (v.getId()==R.id.tv_send){
       if (listener==null)
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
