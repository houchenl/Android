package com.hc.customview.checkin;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by liu_lei on 2017/5/17.
 * <p>
 * 签到日历页
 * <p>
 * 根据当前时间，画出当月日历页。当前时间可由外部设置。
 * <p>
 * 签到日期由外部传入，存入List中。
 * 每个签到日期，定位到它在view中位置，然后画一个圆。
 * <p>
 * a. 绘制标题
 * <p>
 * b. 绘制星期
 * <p>
 * c. 背景色绘制
 * 1. 所有签到日期，都在其下边画一个圆
 * 2. 找到连续签到日期，并找到其起始日期，及起始日期的坐标，绘制一个矩形
 * 3. 绘制今天背景
 * 4. 绘制分隔线和tab线
 * <p>
 * d. 绘制日期
 */

public class CheckInCalendarView extends View {

    private static final String TAG = "CheckInCalendarView";

    private static final int DEFAULT_TEXT_COLOR_TITLE = Color.parseColor("#9CA7C2");
    private static final int DEFAULT_TEXT_COLOR_WEEK = Color.parseColor("#A483D1");
    private static final int DEFAULT_TEXT_COLOR_DAY = Color.parseColor("#9D9D9D");
    private static final int DEFAULT_BG_COLOR_ROUND = Color.parseColor("#7FB3FF");
    private static final int DEFAULT_BG_COLOR_ROUND_BORDER = Color.parseColor("#3788FF");
    private static final int DEFAULT_BG_COLOR_CHECK_IN = Color.parseColor("#DEEBFF");

    private static final int DEFAULT_TEXT_SIZE_TITLE = 16;
    private static final int DEFAULT_TEXT_SIZE_WEEK = 11;
    private static final int DEFAULT_TEXT_SIZE_DAY = 9;

    private static final int DEFAULT_RADIUS = 11;         // 半径
    private static final int DEFAULT_BORDER_WIDTH = 2;    // 边框宽度

    private static final String[] WEEKS = {
            "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"
    };

    private DisplayMetrics mDm;

    private int mCurrentYear;
    private int mCurrentMonth;
    private int mCurrentDay;

    /* title */
    private String mTitle;
    private TextPaint mTitlePaint;
    private StaticLayout mTitleLayout;
    private Point mTitlePoint;

    /* week */
    private TextPaint mWeekPaint;
    private List<StaticLayout> mWeekLayouts = new ArrayList<>();
    private List<Point> mWeekPoints = new ArrayList<>();

    /* 签到日期 */
    private List<String> mCheckInDates = new ArrayList<>();    // 签到日期列表，每个签到日期是一个字符串，格式为yyyymmdd
    private List<Point> mCheckInPoints = new ArrayList<>();
    private List<Rect> mCheckInRects = new ArrayList<>();
    private Paint mCheckInPaint;

    /* day */
    private TextPaint mDayPaint, mCurrentDayPaint;
    private List<StaticLayout> mDayLayouts = new ArrayList<>();
    private List<Point> mDayPoints = new ArrayList<>();

    /* border */
    private Paint mBorderPaint;
    private int mBorderRadius;
    private Point mCirclePoint;    // 圆心

    /* round */
    private Paint mCirclePaint;
    private int mRadius;

    public CheckInCalendarView(Context context) {
        this(context, null);
    }

    public CheckInCalendarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CheckInCalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mDm = getResources().getDisplayMetrics();

        // title
        int textSize = getSize(TypedValue.COMPLEX_UNIT_SP, DEFAULT_TEXT_SIZE_TITLE);
        mTitlePaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTitlePaint.setColor(DEFAULT_TEXT_COLOR_TITLE);
        mTitlePaint.setTextSize(textSize);

        // week
        textSize = getSize(TypedValue.COMPLEX_UNIT_SP, DEFAULT_TEXT_SIZE_WEEK);
        mWeekPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mWeekPaint.setColor(DEFAULT_TEXT_COLOR_WEEK);
        mWeekPaint.setTextSize(textSize);

        // check in
        mCheckInPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCheckInPaint.setColor(DEFAULT_BG_COLOR_CHECK_IN);

        // day
        textSize = getSize(TypedValue.COMPLEX_UNIT_SP, DEFAULT_TEXT_SIZE_DAY);
        mDayPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mDayPaint.setColor(DEFAULT_TEXT_COLOR_DAY);
        mDayPaint.setTextSize(textSize);
        mCurrentDayPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mCurrentDayPaint.setColor(Color.WHITE);
        mCurrentDayPaint.setTextSize(textSize);

        // round
        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setColor(DEFAULT_BG_COLOR_ROUND);
        mRadius = getSize(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_RADIUS);

        // border
        mBorderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBorderPaint.setColor(DEFAULT_BG_COLOR_ROUND_BORDER);
        int borderWidth = getSize(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_BORDER_WIDTH);
        mBorderRadius = mRadius + borderWidth;

        getTodayTime();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = View.resolveSize(getDesiredWidth(), widthMeasureSpec);
        int height = View.resolveSize(getDesiredHeight(), heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        updateContentBounds();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // draw title
        drawText(mTitleLayout, mTitlePoint, canvas);

        // draw week
        for (int i = 0; i < mWeekLayouts.size(); i++) {
            drawText(mWeekLayouts.get(i), mWeekPoints.get(i), canvas);
        }

        // draw check in circles
        for (Point point : mCheckInPoints) {
            drawCircle(point, mRadius, mCheckInPaint, canvas);
        }
        for (Rect rect : mCheckInRects) {
            canvas.drawRect(rect, mCheckInPaint);
        }

        // draw border
        drawCircle(mCirclePoint, mBorderRadius, mBorderPaint, canvas);

        // draw circle
        drawCircle(mCirclePoint, mRadius, mCirclePaint, canvas);

        // draw day
        for (int i = 0; i < mDayLayouts.size(); i++) {
            drawText(mDayLayouts.get(i), mDayPoints.get(i), canvas);
        }
    }

    private int getDesiredWidth() {
        int textWidth = getTextWidth(mWeekPaint, WEEKS[0]) * 7;
        int spaceWidth = getSize(TypedValue.COMPLEX_UNIT_DIP, 14) * 7;

        return getPaddingLeft() + getPaddingRight() + textWidth + spaceWidth;
    }

    private int getDesiredHeight() {
        int rows = getRows(mCurrentYear, mCurrentMonth);
        int textHeight = getTextTotalHeight(rows);
        int spaceHeight = getSize(TypedValue.COMPLEX_UNIT_DIP, 8) + getSize(TypedValue.COMPLEX_UNIT_DIP, 12) + getSize(TypedValue.COMPLEX_UNIT_DIP, 18) + getSize(TypedValue.COMPLEX_UNIT_DIP, 20) * rows;

        return getPaddingTop() + getPaddingBottom() + textHeight + spaceHeight;
    }

    private int getSize(int unit, int value) {
        return (int) TypedValue.applyDimension(unit, value, mDm);
    }

    private int getTextWidth(Paint paint, String text) {
        if (paint != null && null != text && !"".equals(text)) {
            return (int) paint.measureText(text);
        }

        return 0;
    }

    private int getTextTotalHeight(int row) {
        int titleHeight = getTextHeight(mTitlePaint);
        int weekHeight = getTextHeight(mWeekPaint);
        int dayHeight = getTextHeight(mDayPaint);

        return titleHeight + weekHeight + dayHeight * row;
    }

    private int getTextHeight(TextPaint paint) {
        int result = 0;

        if (paint != null) {
            Paint.FontMetrics fm = paint.getFontMetrics();
            result = (int) Math.ceil(fm.descent - fm.ascent) + 1;
        }

        return result;
    }

    // 根据系统时间，获取今天的年、月、日数值，以及标题
    private void getTodayTime() {
        Calendar calendar = Calendar.getInstance();

        mCurrentYear = calendar.get(Calendar.YEAR);
        mCurrentMonth = calendar.get(Calendar.MONTH) + 1;    // 返回的月份从0开始
        mCurrentDay = calendar.get(Calendar.DAY_OF_MONTH);

        updateTitle();
    }

    private void updateTitle() {
        mTitle = mCurrentYear + "年" + mCurrentMonth + "月" + mCurrentDay + "日";
    }

    public void setTitleTextSize(int size) {
        mTitlePaint.setTextSize(size);
        updateContentBounds();
        invalidate();
    }

    public void setTitleTextColor(int color) {
        mTitlePaint.setColor(color);
        updateContentBounds();
        invalidate();
    }

    public void setCurrentDate(int year, int month, int day) {
        mCurrentYear = year;
        mCurrentMonth = month;
        mCurrentDay = day;
        updateTitle();
        requestLayout();
    }

    /**
     * 格式需为：yyyymmdd
     */
    public void addCheckInDate(String date) {
        mCheckInDates.add(date);
        updateContentBounds();
        invalidate();
    }

    public void addCheckInData(int year, int month, int day) {
        String date = String.valueOf(year);
        if (month < 10) {
            date += "0" + month;
        } else {
            date += month;
        }
        if (day < 10) {
            date += "0" + day;
        } else {
            date += day;
        }

        mCheckInDates.add(date);
        updateContentBounds();
        invalidate();
    }

    public void addCheckInDates(List<String> dates) {
        mCheckInDates.addAll(dates);
        updateContentBounds();
        invalidate();
    }

    private void updateContentBounds() {
        int left = (getWidth() - getDesiredWidth() - getPaddingLeft() - getPaddingRight()) / 2;
        int top = (getHeight() - getDesiredHeight() - getPaddingTop() - getPaddingBottom()) / 2;

        // get title layout and position
        float textWidth = mTitlePaint.measureText(mTitle, 0, mTitle.length());
        mTitleLayout = new StaticLayout(mTitle, mTitlePaint, (int) textWidth,
                Layout.Alignment.ALIGN_CENTER, 1f, 0f, true);

        int x = left + (getDesiredWidth() - getTextWidth(mTitlePaint, mTitle)) / 2;
        int y = top + getSize(TypedValue.COMPLEX_UNIT_DIP, 8);
        mTitlePoint = new Point(x, y);

        // get week layout and position
        x = left + getSize(TypedValue.COMPLEX_UNIT_DIP, 7);
        y = y + getTextHeight(mTitlePaint) + getSize(TypedValue.COMPLEX_UNIT_DIP, 12);

        mWeekLayouts.clear();
        mWeekPoints.clear();

        for (String text : WEEKS) {
            // add layout
            textWidth = mWeekPaint.measureText(text, 0, text.length());
            StaticLayout layout = new StaticLayout(text, mWeekPaint, (int) textWidth,
                    Layout.Alignment.ALIGN_CENTER, 1f, 0f, true);
            mWeekLayouts.add(layout);

            // add position
            Point point = new Point(x, y);
            mWeekPoints.add(point);

            x += getTextWidth(mWeekPaint, text) + getSize(TypedValue.COMPLEX_UNIT_DIP, 14);
        }

        int dayTop = y + getTextHeight(mWeekPaint) + getSize(TypedValue.COMPLEX_UNIT_DIP, 18);

        // get check in circle positions
        getCheckInDatePosition(mCheckInDates, left, dayTop);

        // get continuous check in positions
        getCoutinuousCheckInPosition(mCheckInDates, left, dayTop);

        // get day layout and position
        int days = getDaysOfMonth(mCurrentYear, mCurrentMonth);

        mDayLayouts.clear();
        mDayPoints.clear();

        for (int i = 0; i < days; i++) {
            int day = i + 1;    // 第1天为1

            // add layout
            String text = String.valueOf(day);
            textWidth = mDayPaint.measureText(text, 0, text.length());
            StaticLayout layout = new StaticLayout(text, mDayPaint, (int) textWidth,
                    Layout.Alignment.ALIGN_CENTER, 1f, 0f, true);
            if (day == mCurrentDay) {
                layout = new StaticLayout(text, mCurrentDayPaint, (int) textWidth,
                        Layout.Alignment.ALIGN_CENTER, 1f, 0f, true);
            }
            mDayLayouts.add(layout);

            // add position
            x = getDayX(mCurrentYear, mCurrentMonth, day, left);
            y = getDayY(mCurrentYear, mCurrentMonth, day, dayTop);
            Point point = new Point(x, y);
            mDayPoints.add(point);
        }

        // get round center position
        mCirclePoint = getCenterPosition(mCurrentYear, mCurrentMonth, mCurrentDay, left, dayTop);
    }

    private void drawText(StaticLayout layout, Point point, Canvas canvas) {
        if (layout != null && point != null && canvas != null) {
            canvas.save();
            canvas.translate(point.x, point.y);

            layout.draw(canvas);

            canvas.restore();
        }
    }

    /**
     * 画圆形
     */
    private void drawCircle(Point point, int radius, Paint paint, Canvas canvas) {
        canvas.drawCircle(point.x, point.y, radius, paint);
    }

    /**
     * 获取某年某月有多少天
     */
    private int getDaysOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);

        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取某年某月某日是周几
     */
    private int getWeekIndex(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, day);

        int index = cal.get(Calendar.DAY_OF_WEEK) - 1;

        return index < 0 ? 0 : index;
    }

    /**
     * @param day  用于确定在格式中间的位置
     * @param left 用于确定左边界
     *             <p>
     *             根据是周几来获取x坐标位置
     */
    private int getDayX(int year, int month, int day, int left) {
        int weekIndex = getWeekIndex(year, month, day);

        int gridWidth = getDesiredWidth() / 7;
        int textWidth = getTextWidth(mDayPaint, String.valueOf(day));
        int gridLeft = weekIndex * gridWidth;

        return left + gridLeft + (gridWidth - textWidth) / 2;
    }

    /**
     * 获取某天在月份中排第几排
     */
    private int getRow(int year, int month, int day) {
        /*
        * 先获取指定年月第1天是星期几，然后，日期day加上第1天的weekIndex，再减去1，除以7，即得行号
        * 行号从0开始
        * */
        int firstWeekIndex = getWeekIndex(year, month, 1);
        return (firstWeekIndex + day - 1) / 7;
    }

    /**
     * 获取某年某月显示几行
     */
    private int getRows(int year, int month) {
        // 获取该月有几天
        int days = getDaysOfMonth(year, month);
        // 获取最后一天显示在第几行
        int row = getRow(year, month, days);

        return row + 1;
    }

    /**
     * 获取某年某月某日在y方向的坐标
     */
    private int getDayY(int year, int month, int day, int top) {
        int rowIndex = getRow(year, month, day);
        int textHeight = getTextHeight(mDayPaint);
        int space = getSize(TypedValue.COMPLEX_UNIT_DIP, 20);

        return top + (textHeight + space) * rowIndex;
    }

    /**
     * 获取某天中心坐标位置
     */
    private Point getCenterPosition(int year, int month, int day, int left, int top) {
        int x = getCenterX(year, month, day, left);
        int y = getCenterY(year, month, day, top);
        return new Point(x, y);
    }

    private int getCenterX(int year, int month, int day, int left) {
        int weekIndex = getWeekIndex(year, month, day);
        int gridWidth = getDesiredWidth() / 7;
        int gridLeft = weekIndex * gridWidth;

        return left + gridLeft + gridWidth / 2;
    }

    private int getCenterY(int year, int month, int day, int top) {
        int y = top;

        int rowIndex = getRow(year, month, day);
        int textHeight = getTextHeight(mDayPaint);
        int space = getSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        y += (textHeight + space) * rowIndex;
        y += textHeight / 2;

        return y;
    }

    /**
     * 获取签到日期的位置列表
     */
    private void getCheckInDatePosition(List<String> dates, int left, int top) {
        mCheckInPoints.clear();

        for (String text : dates) {
            int year = Integer.parseInt(text.substring(0, 4));
            int month = Integer.parseInt(text.substring(4, 6));
            int day = Integer.parseInt(text.substring(6, 8));

            // 只有是当月时，才寻找坐标绘制
            if (month == mCurrentMonth)
                mCheckInPoints.add(getCenterPosition(year, month, day, left, top));
        }
    }

    /**
     * 获取连续签到背景位置
     */
    private void getCoutinuousCheckInPosition(List<String> dates, int left, int top) {
        // 1. 排序
        Collections.sort(dates, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });

        // 2. 遍历，获取本月连续签到日期的始末位置
        mCheckInRects.clear();
        int preDay = 0;
        Rect rect = new Rect();
        int continuousDays = 0;

        for (String text : dates) {
            int year = Integer.parseInt(text.substring(0, 4));
            int month = Integer.parseInt(text.substring(4, 6));
            int day = Integer.parseInt(text.substring(6, 8));

            // 只有是当月时，才寻找坐标绘制
            if (month == mCurrentMonth) {
                if (preDay == 0) {
                    preDay = day;
                    getRect(year, month, day, left, top, rect, true);
                }

                if (day == preDay + 1 && getWeekIndex(year, month, day) != 0) {
                    continuousDays++;
                    getRect(year, month, day, left, top, rect, false);
                } else {
                    if (continuousDays > 0)
                        mCheckInRects.add(rect);

                    // 开始新的一段
                    continuousDays = 0;
                    rect = new Rect();
                    getRect(year, month, day, left, top, rect, true);
                }

                preDay = day;
            }
        }
    }

    /**
     * 获取某天的x坐标和y坐标
     */
    private void getRect(int year, int month, int day, int left, int top, Rect rect, boolean isStart) {
        int x = getCenterX(year, month, day, left);
        int y = getCenterY(year, month, day, top);

        rect.top = y - mRadius;
        rect.bottom = y + mRadius;

        if (isStart) {
            rect.left = x;
        } else {
            rect.right = x;
        }
    }

}
