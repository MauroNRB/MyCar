package com.mycar.app.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;

import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class AccelerationGraphView extends View {

    private final List<Float> dataPoints = new ArrayList<>();
    private int maxDataPoints = 50;
    private float maxValue = 20f; // Max m/s² scale

    private Paint gridPaint;
    private Paint linePaint;
    private Paint fillPaint;
    private Paint textPaint;

    private Path linePath = new Path();
    private Path fillPath = new Path();

    public AccelerationGraphView(Context context) {
        super(context);
        init();
    }

    public AccelerationGraphView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AccelerationGraphView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        gridPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        gridPaint.setColor(Color.parseColor("#2A2A36"));
        gridPaint.setStrokeWidth(2f);

        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(Color.parseColor("#00D2FF"));
        linePaint.setStrokeWidth(5f);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setPathEffect(new CornerPathEffect(10f));

        fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaint.setColor(Color.parseColor("#2000D2FF"));
        fillPaint.setStyle(Paint.Style.FILL);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.parseColor("#A0A0B0"));
        textPaint.setTextSize(28f);
    }

    public void addSample(float value) {
        dataPoints.add(value);
        if (dataPoints.size() > maxDataPoints) {
            dataPoints.remove(0);
        }
        invalidate();
    }

    public void setSamples(List<Float> samples) {
        dataPoints.clear();
        if (samples != null) {
            dataPoints.addAll(samples);
        }
        invalidate();
    }

    public void clearData() {
        dataPoints.clear();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        float paddingLeft = 60f;
        float paddingBottom = 40f;
        float paddingTop = 20f;
        float paddingRight = 20f;

        float chartWidth = width - paddingLeft - paddingRight;
        float chartHeight = height - paddingTop - paddingBottom;

        // 1. Draw Grid Lines & Y-Axis Labels
        int gridRows = 4;
        for (int i = 0; i <= gridRows; i++) {
            float y = paddingTop + (chartHeight / gridRows) * i;
            canvas.drawLine(paddingLeft, y, width - paddingRight, y, gridPaint);

            float labelValue = maxValue - (maxValue / gridRows) * i;
            canvas.drawText(String.format("%.0f", labelValue), 10f, y + 10f, textPaint);
        }

        // 2. Empty State Message
        if (dataPoints.isEmpty()) {
            textPaint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText("Aguardando dados dos sensores...", width / 2f, height / 2f, textPaint);
            textPaint.setTextAlign(Paint.Align.LEFT);
            return;
        }

        // 3. Draw Plot Line
        linePath.reset();
        fillPath.reset();

        float xStep = chartWidth / (maxDataPoints - 1);

        for (int i = 0; i < dataPoints.size(); i++) {
            float val = dataPoints.get(i);
            if (val > maxValue) val = maxValue;

            float x = paddingLeft + (i * xStep);
            float y = paddingTop + chartHeight - ((val / maxValue) * chartHeight);

            if (i == 0) {
                linePath.moveTo(x, y);
                fillPath.moveTo(x, paddingTop + chartHeight);
                fillPath.lineTo(x, y);
            } else {
                linePath.lineTo(x, y);
                fillPath.lineTo(x, y);
            }
        }

        float lastX = paddingLeft + ((dataPoints.size() - 1) * xStep);
        fillPath.lineTo(lastX, paddingTop + chartHeight);
        fillPath.close();

        canvas.drawPath(fillPath, fillPaint);
        canvas.drawPath(linePath, linePaint);
    }
}
