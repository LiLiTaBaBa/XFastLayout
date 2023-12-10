package com.zlj.fastlayout.helper;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * Created by zlj on 2021/4/25.
 * @Word：Thought is the foundation of understanding
 * @since 1.0.0
 * 验证码倒计时辅助类
 *
 * 具体的使用实例：
 * CodeTimer codeTimer=
 *		 new CodeTimer.Builder((TextView) view)
 *	 	.setStartText(" 获取验证码")
 *		 .setDynamicText(" 秒后重新获取")
 *	 	.setStartColor(getResources().getColor(R.color.main_color))
 *		 .setDynamicColor(Color.parseColor("#999999"))
 *		 .setTime(60)
 *		 .setIntervalTime(1)
 *		 .builder();
 *  codeTimer.start();
 */
public class CodeTimer extends CountDownTimer {
    private Builder builder;
    private CodeTimer(Builder builder) {
        super(builder.time*1000L,builder.intervalTime*1000);
        this.builder=builder;
        resetCodeTv();
    }

    /**
     * 重置CodeTv
     */
    private void resetCodeTv() {
        this.builder.codeTv.setEnabled(true);
        this.builder.codeTv.setText(this.builder.startText);
        this.builder.codeTv.setTextColor(this.builder.startColor);
    }

    @Override
    public void onTick(long millisUntilFinished) {
        this.builder.codeTv.setEnabled(false);
        this.builder.codeTv.setText(millisUntilFinished/1000 + this.builder.dynamicText);
        this.builder.codeTv.setTextColor(this.builder.dynamicColor);
    }


    @Override
    public void onFinish() {
        resetCodeTv();
    }

    public static class Builder{
        /**需要绑定的TextView*/
        private TextView codeTv;
        /**回复的时间*/
        private int time=60;
        /**重复的时间间隔*/
        private int intervalTime=1;
        /**默认的文本显示*/
        private String startText="获取验证码";
        /**动态的文本显示*/
        private String dynamicText=" 秒后重新获取";
        /**默认的文本颜色*/
        private int startColor= Color.parseColor("#ff0000");
        /**动态的文本颜色*/
        private int dynamicColor=Color.parseColor("#999999");

        public Builder(TextView codeTv){
            this.codeTv=codeTv;
        }

        public Builder setDynamicColor(int dynamicColor) {
            this.dynamicColor = dynamicColor;
            return this;
        }

        public Builder setCodeTv(TextView codeTv) {
            this.codeTv = codeTv;
            return this;
        }

        public Builder setDynamicText(String dynamicText) {
            this.dynamicText = dynamicText;
            return this;
        }

        public Builder setIntervalTime(int intervalTime) {
            this.intervalTime = intervalTime;
            return this;
        }

        public Builder setStartColor(int startColor) {
            this.startColor = startColor;
            return this;
        }

        public Builder setStartText(String startText) {
            this.startText = startText;
            return this;
        }

        public Builder setTime(int time) {
            this.time = time;
            return this;
        }

        /**
         * 构建者模式
         * @return
         */
        public CodeTimer builder(){
            return new CodeTimer(this);
        }
    }


}
