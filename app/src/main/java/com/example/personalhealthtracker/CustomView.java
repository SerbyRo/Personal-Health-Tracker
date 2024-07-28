package com.example.personalhealthtracker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CustomView extends View {
    private Paint paint;

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setAntiAlias(true); // To smooth the edges of the drawing
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        // Background
        canvas.drawColor(Color.WHITE);

        // Draw face circle
        paint.setColor(Color.YELLOW);
        paint.setStyle(Paint.Style.FILL);
        float centerX = getWidth() / 2f;
        float centerY = getHeight() / 2f;
        float radius = Math.min(centerX, centerY) * 0.8f;
        canvas.drawCircle(centerX, centerY, radius, paint);

        // Draw eyes
        paint.setColor(Color.BLACK);
        float eyeRadius = radius / 8f;
        float eyeOffsetX = radius / 3f;
        float eyeOffsetY = radius / 4f;
        canvas.drawCircle(centerX - eyeOffsetX, centerY - eyeOffsetY, eyeRadius, paint);
        canvas.drawCircle(centerX + eyeOffsetX, centerY - eyeOffsetY, eyeRadius, paint);

        // Draw smile
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10f);
        float smileRadius = radius / 2f;
        float smileOffsetY = radius / 3f;
        canvas.drawArc(centerX - smileRadius, centerY - smileOffsetY, centerX + smileRadius, centerY + smileOffsetY, 0, 180, false, paint);
    }
}
