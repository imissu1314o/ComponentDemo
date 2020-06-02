package com.sc.componentdemo.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 2020/5/25.
 */

public class FlowLayout extends ViewGroup {
    //item行高
    private float itemHeight;
    //item横向间隔
    private float itemSpace;
    //列间隔
    private float dividerHeight;



    public FlowLayout(Context context) {
        super( context );
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super( context, attrs );
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super( context, attrs, defStyleAttr );
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren( widthMeasureSpec,heightMeasureSpec );
        int widthSize = MeasureSpec.getSize( widthMeasureSpec );  //包含padding
        int heightSize = MeasureSpec.getSize( heightMeasureSpec );
        int modeWidth = MeasureSpec.getMode( widthMeasureSpec );
        int modeHeight = MeasureSpec.getMode( heightMeasureSpec );

        int resultWidth=0;
        int resultHeight=0;

        Map<String,Integer> computeResult = compute( widthSize-getPaddingRight() );

        //如果是精确模式采用父布局指定的尺寸
        if(modeWidth == MeasureSpec.EXACTLY){
            resultWidth = widthSize;
        }else{ //wrap_content AT_MOST
            resultWidth = computeResult.get( "allChildWidth" );
        }
        if(modeHeight == MeasureSpec.EXACTLY){
            resultHeight = heightSize;
        }else{
            resultHeight = computeResult.get( "allChildHeight" );
        }

        setMeasuredDimension( resultWidth,resultHeight );

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        for(int i= 0;i<count;i++){
            View child=getChildAt( i );
            Rect rect = (Rect) child.getTag();
            child.layout( rect.left,rect.top,rect.right,rect.bottom );
        }
    }


    //测量过程
    //@param flowWidth 该view的宽度
    //*@return  返回子元素总所占宽度和高度（用于计算Flowlayout的AT_MOST模式设置宽高）
     private Map<String,Integer> compute(int flowWidth){
         boolean aRow = true; //是否是单行 ??
         MarginLayoutParams params;
         int lineWidth = getPaddingLeft(); //当前行已占宽度
         int lineHeight = getPaddingTop();
         int rowMaxHeight = 0; //当前行所有元素的最大高度

         int count =getChildCount();

         for(int i=0;i<count;i++){
             View child = getChildAt( i );

             params =(MarginLayoutParams) child.getLayoutParams();
             int childWidth = child.getMeasuredWidth() + params.rightMargin + params.leftMargin;
             int childHeight = child.getMeasuredHeight() + params.topMargin + params.bottomMargin;

             rowMaxHeight = Math.max(rowMaxHeight,childHeight);

             if(lineWidth + childWidth > flowWidth ){ //换行
                 lineHeight += rowMaxHeight;
                 rowMaxHeight = childHeight;
                 aRow = false;

                 //重置行宽度  ？？
                 lineWidth = getPaddingLeft();
             }
             lineWidth += childWidth;
             child.setTag( new Rect( lineWidth - childWidth + params.leftMargin,lineHeight + params.topMargin,lineWidth - params.rightMargin,lineHeight + childHeight - params.bottomMargin ) );
         }

         Map<String,Integer> flowMap = new HashMap<>(  );
         if(aRow){
             flowMap.put( "allChildWidth",lineWidth + getPaddingRight() );
         }else{
             flowMap.put( "allChildWidth",flowWidth + getPaddingRight() );
         }
         flowMap.put( "allChildHeight",lineHeight + rowMaxHeight+getPaddingBottom()  );
         return flowMap;
     }


}
