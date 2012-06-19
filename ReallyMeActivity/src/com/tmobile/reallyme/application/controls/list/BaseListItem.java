package com.tmobile.reallyme.application.controls.list;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;

import com.tmobile.reallyme.R;

/**
 * dku
 */
public class BaseListItem extends View implements View.OnClickListener{
    public Bitmap avatar = null;

    public int itemHeight = 40;
    public int itemWidth = 400;

    public String location = "";
    public String fname = "";
    public String lname = "";
    public String statusText = "";

    private int imageWidth = 0;
    private int baseLeftShift = 0;
    public BaseListItem(Context context, int itemWidth, int itemHeight) {
        super(context);
        setLayoutParams(new AbsListView.LayoutParams(
                    itemWidth, itemHeight));
        this.setOnClickListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint rectPaint = new Paint();        
        rectPaint.setARGB(255, 120, 120, 120);
        canvas.drawRect(new Rect(0,0,itemWidth, itemHeight), rectPaint);
        drawAvatar(canvas);
        drawLocationText(canvas);
    }
    void drawAvatar(Canvas canvas){
        avatar = BitmapFactory.decodeResource(getResources(),R.drawable.nobody);
        Paint avaPaint = new Paint();
        float ratio = avatar.getWidth()/((float)avatar.getHeight());
        imageWidth = Math.round(itemHeight*ratio);
        canvas.drawBitmap(avatar,  null, new Rect(0,0, imageWidth, itemHeight), avaPaint);
    }
    void drawLocationText(Canvas canvas){
        Paint textPaint = new Paint();
        textPaint.setAntiAlias(true);

        textPaint.setARGB(255, 240,240,240);
        textPaint.setTextSize(14);
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);
        String userName = fname+" "+lname;
        float userNameLangth  = textPaint.measureText(userName);
        canvas.drawText(userName, 5+imageWidth, itemHeight/10 -textPaint.ascent(), textPaint);

        textPaint.setARGB(255, 220,220,220);
        textPaint.setTextSize(14);
        textPaint.setTypeface(Typeface.DEFAULT);
        canvas.drawText(" @ "+location, 5+imageWidth + userNameLangth , itemHeight/10 -textPaint.ascent(), textPaint);


        textPaint.setARGB(255, 220,220,220);
        textPaint.setTextSize(12);
        textPaint.setTypeface(Typeface.DEFAULT);
        canvas.drawText(statusText,5+imageWidth ,itemHeight/2 -textPaint.ascent(), textPaint);
        
    }

    @Override
    protected void onMeasure(int i, int i1) {
        super.onMeasure(i, i1);        
    }

    public void onClick(View view) {
        //Log.i("Item", "Clicked");
        //(this).fname = "!!!";
        //this.invalidate();
        Log.i(this.getClass().getName(), "onClick");
     }

    public Bitmap getAvatar() {
        return avatar;
    }

    public void setAvatar(Bitmap avatar) {
        this.avatar = avatar;
    }

    public int getItemHeight() {
        return itemHeight;
    }

    public void setItemHeight(int itemHeight) {
        this.itemHeight = itemHeight;
    }

    public int getItemWidth() {
        return itemWidth;
    }

    public void setItemWidth(int itemWidth) {
        this.itemWidth = itemWidth;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {

        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }
}
