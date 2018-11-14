package com.example.saidi.custombottobar.view

import android.content.Context
import android.graphics.*
import android.support.design.widget.BottomNavigationView
import android.util.AttributeSet

class CurvedBottomNavigationView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BottomNavigationView(context, attrs, defStyleAttr) {

    private val mPath: Path
    private val mPaint: Paint

    /** the radius represent the radius of the fab button  */
    private var radius: Int = 0
    // the coordinates of the first curve
    private val mFirstCurveStartPoint = Point()
    private val mFirstCurveEndPoint = Point()
    private val mFirstCurveControlPoint1 = Point()
    private val mFirstCurveControlPoint2 = Point()

    //the coordinates of the second curve
    private var mSecondCurveStartPoint = Point()
    private val mSecondCurveEndPoint = Point()
    private val mSecondCurveControlPoint1 = Point()
    private val mSecondCurveControlPoint2 = Point()

    // Navigation bar bounds (width & height)
    private var mNavigationBarWidth: Int = 0
    private var mNavigationBarHeight: Int = 0

    init {
        // radius of fab button
        radius = 256 / 2
        mPath = Path()
        mPaint = Paint()
        with(mPaint) {
            style = Paint.Style.FILL_AND_STROKE
            color = Color.WHITE
        }
        setBackgroundColor(Color.TRANSPARENT)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        // get width and height of navigation bar
        mNavigationBarWidth = width
        mNavigationBarHeight = height
        // the coordinates (x,y) of the start point before curve
        mFirstCurveStartPoint.set(mNavigationBarWidth / 2 - radius * 2 - radius / 3, 0)
        // the coordinates (x,y) of the end point after curve
        mFirstCurveEndPoint.set(mNavigationBarWidth / 2, radius + radius / 4)
        // same thing for the second curve
        mSecondCurveStartPoint = mFirstCurveEndPoint
        mSecondCurveEndPoint.set(mNavigationBarWidth / 2 + radius * 2 + radius / 3, 0)

        // the coordinates (x,y)  of the 1st control point on a cubic curve
        mFirstCurveControlPoint1.set(
            mFirstCurveStartPoint.x + radius + radius / 4,
            mFirstCurveStartPoint.y
        )
        // the coordinates (x,y)  of the 2nd control point on a cubic curve
        mFirstCurveControlPoint2.set(
            mFirstCurveEndPoint.x - radius * 2 + radius,
            mFirstCurveEndPoint.y
        )

        mSecondCurveControlPoint1.set(
            mSecondCurveStartPoint.x + radius * 2 - radius,
            mSecondCurveStartPoint.y
        )
        mSecondCurveControlPoint2.set(
            mSecondCurveEndPoint.x - (radius + radius / 4),
            mSecondCurveEndPoint.y
        )

        mPath.apply {
            reset()
            moveTo(0f, 0f)
            lineTo(mFirstCurveStartPoint.x.toFloat(), mFirstCurveStartPoint.y.toFloat())

            cubicTo(
                mFirstCurveControlPoint1.x.toFloat(), mFirstCurveControlPoint1.y.toFloat(),
                mFirstCurveControlPoint2.x.toFloat(), mFirstCurveControlPoint2.y.toFloat(),
                mFirstCurveEndPoint.x.toFloat(), mFirstCurveEndPoint.y.toFloat()
            )

            cubicTo(
                mSecondCurveControlPoint1.x.toFloat(), mSecondCurveControlPoint1.y.toFloat(),
                mSecondCurveControlPoint2.x.toFloat(), mSecondCurveControlPoint2.y.toFloat(),
                mSecondCurveEndPoint.x.toFloat(), mSecondCurveEndPoint.y.toFloat()
            )

            lineTo(mNavigationBarWidth.toFloat(), 0f)
            lineTo(mNavigationBarWidth.toFloat(), mNavigationBarHeight.toFloat())
            lineTo(0f, mNavigationBarHeight.toFloat())
            close()
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawPath(mPath, mPaint)
    }
}