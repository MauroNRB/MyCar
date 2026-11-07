package com.mycar.app.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

public class SpeedometerView extends View {

    private static final float MAX_SPEED = 240f;
    private static final float START_ANGLE = 150f;
    private static final float SWEEP_ANGLE = 240f;

    private float currentSpeed = 0f;
    private float targetSpeed = 0f;
    private ValueAnimator needleAnimator;

    private Paint arcBackgroundPaint;
    private Paint arcAccentPaint;
    private Paint arcWarningPaint;
    private Paint tickPaint;
    private Paint majorTickPaint;
    private Paint textPaint;
    private Paint speedValuePaint;
    private Paint labelPaint;
    private Paint needlePaint;
    private Paint centerCapPaint;

    private RectF arcBounds = new RectF();

    public SpeedometerView(Context context) {
        super(context);
        init();
    }

    public SpeedometerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SpeedometerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        arcBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        arcBackgroundPaint.setStyle(Paint.Style.STROKE);
        arcBackgroundPaint.setColor(Color.parseColor("#262630"));
        arcBackgroundPaint.setStrokeCap(Paint.Cap.ROUND);

        arcAccentPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        arcAccentPaint.setStyle(Paint.Style.STROKE);
        arcAccentPaint.setColor(Color.parseColor("#00D2FF"));
        arcAccentPaint.setStrokeCap(Paint.Cap.ROUND);

        arcWarningPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        arcWarningPaint.setStyle(Paint.Style.STROKE);
        arcWarningPaint.setColor(Color.parseColor("#FF3D00"));
        arcWarningPaint.setStrokeCap(Paint.Cap.ROUND);

        tickPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        tickPaint.setColor(Color.parseColor("#A0A0B0"));
        tickPaint.setStrokeWidth(3f);

        majorTickPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        majorTickPaint.setColor(Color.WHITE);
        majorTickPaint.setStrokeWidth(6f);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.parseColor("#A0A0B0"));
        textPaint.setTextAlign(Paint.Align.CENTER);

        speedValuePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        speedValuePaint.setColor(Color.WHITE);
        speedValuePaint.setTextAlign(Paint.Align.CENTER);
        speedValuePaint.setFakeBoldText(true);

        labelPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        labelPaint.setColor(Color.parseColor("#00D2FF"));
        labelPaint.setTextAlign(Paint.Align.CENTER);
        labelPaint.setFakeBoldText(true);

        needlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        needlePaint.setColor(Color.parseColor("#FF3D00"));
        needlePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        needlePaint.setStrokeWidth(4f);

        centerCapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        centerCapPaint.setColor(Color.parseColor("#00D2FF"));
        centerCapPaint.setStyle(Paint.Style.FILL);
    }

    public void setSpeed(float speedKmh) {
        if (speedKmh < 0) speedKmh = 0;
        if (speedKmh > MAX_SPEED) speedKmh = MAX_SPEED;

        this.targetSpeed = speedKmh;

        if (needleAnimator != null && needleAnimator.isRunning()) {
            needleAnimator.cancel();
        }

        needleAnimator = ValueAnimator.ofFloat(currentSpeed, targetSpeed);
        needleAnimator.setDuration(300);
        needleAnimator.setInterpolator(new DecelerateInterpolator());
        needleAnimator.addUpdateListener(animation -> {
            currentSpeed = (float) animation.getAnimatedValue();
            invalidate();
        });
        needleAnimator.start();
    }

    public float getCurrentSpeed() {
        return currentSpeed;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int size = Math.min(width, height);
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();
        float centerX = width / 2f;
        float centerY = height / 2f;
        float radius = Math.min(width, height) / 2f * 0.8f;
        float strokeWidth = radius * 0.1f;

        arcBackgroundPaint.setStrokeWidth(strokeWidth);
        arcAccentPaint.setStrokeWidth(strokeWidth);
        arcWarningPaint.setStrokeWidth(strokeWidth);

        arcBounds.set(centerX - radius, centerY - radius, centerX + radius, centerY + radius);

        // 1. Draw Background Gauge Arc
        canvas.drawArc(arcBounds, START_ANGLE, SWEEP_ANGLE, false, arcBackgroundPaint);

        // 2. Draw Active Speed Arc
        float currentSweep = (currentSpeed / MAX_SPEED) * SWEEP_ANGLE;
        if (currentSpeed <= 120f) {
            canvas.drawArc(arcBounds, START_ANGLE, currentSweep, false, arcAccentPaint);
        } else {
            float blueSweep = (120f / MAX_SPEED) * SWEEP_ANGLE;
            canvas.drawArc(arcBounds, START_ANGLE, blueSweep, false, arcAccentPaint);
            float redSweep = currentSweep - blueSweep;
            canvas.drawArc(arcBounds, START_ANGLE + blueSweep, redSweep, false, arcWarningPaint);
        }

        // 3. Draw Tick Marks and Numerical Labels
        int totalTicks = 24; // 10 km/h per tick
        textPaint.setTextSize(radius * 0.12f);

        for (int i = 0; i <= totalTicks; i++) {
            float tickValue = i * 10f;
            float angle = START_ANGLE + (i / (float) totalTicks) * SWEEP_ANGLE;
            double rad = Math.toRadians(angle);

            boolean isMajor = (i % 2 == 0); // Every 20 km/h
            float innerRadius = isMajor ? radius - strokeWidth * 1.5f : radius - strokeWidth * 1.1f;
            float outerRadius = radius - strokeWidth * 0.5f;

            float startX = (float) (centerX + innerRadius * Math.cos(rad));
            float startY = (float) (centerY + innerRadius * Math.sin(rad));
            float stopX = (float) (centerX + outerRadius * Math.cos(rad));
            float stopY = (float) (centerY + outerRadius * Math.sin(rad));

            canvas.drawLine(startX, startY, stopX, stopY, isMajor ? majorTickPaint : tickPaint);

            if (isMajor) {
                float labelRadius = radius - strokeWidth * 2.2f;
                float labelX = (float) (centerX + labelRadius * Math.cos(rad));
                float labelY = (float) (centerY + labelRadius * Math.sin(rad)) + (textPaint.getTextSize() / 3f);
                canvas.drawText(String.valueOf((int) tickValue), labelX, labelY, textPaint);
            }
        }

        // 4. Draw Central Speed Value & Unit Text
        speedValuePaint.setTextSize(radius * 0.35f);
        canvas.drawText(String.valueOf(Math.round(currentSpeed)), centerX, centerY + radius * 0.2f, speedValuePaint);

        labelPaint.setTextSize(radius * 0.14f);
        canvas.drawText("km/h", centerX, centerY + radius * 0.4f, labelPaint);

        // 5. Draw Needle
        float needleAngle = START_ANGLE + (currentSpeed / MAX_SPEED) * SWEEP_ANGLE;
        double needleRad = Math.toRadians(needleAngle);
        float needleLength = radius * 0.75f;

        float needleEndX = (float) (centerX + needleLength * Math.cos(needleRad));
        float needleEndY = (float) (centerY + needleLength * Math.sin(needleRad));

        Path needlePath = new Path();
        float baseRadius = radius * 0.08f;
        float leftAngle = (float) (needleRad - Math.PI / 2);
        float rightAngle = (float) (needleRad + Math.PI / 2);

        float baseLeftX = (float) (centerX + baseRadius * Math.cos(leftAngle));
        float baseLeftY = (float) (centerY + baseRadius * Math.sin(leftAngle));
        float baseRightX = (float) (centerX + baseRadius * Math.cos(rightAngle));
        float baseRightY = (float) (centerY + baseRadius * Math.sin(rightAngle));

        needlePath.moveTo(baseLeftX, baseLeftY);
        needlePath.lineTo(needleEndX, needleEndY);
        needlePath.lineTo(baseRightX, baseRightY);
        needlePath.close();

        canvas.drawPath(needlePath, needlePaint);

        // 6. Draw Center Cap
        canvas.drawCircle(centerX, centerY, radius * 0.12f, centerCapPaint);
    }
}
