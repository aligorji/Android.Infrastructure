package ir.aligorji.androidinfrastructure.widget.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

import ir.aligorji.androidinfrastructure.R;


public class ScrollAwareFabBehavior extends FloatingActionButton.Behavior
{

    private static final int STEP = 10;

    private static final Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();
    private boolean mIsAnimatingOut = false;

    private int sumTransfer = 0;

    public ScrollAwareFabBehavior(Context context, AttributeSet attrs)
    {
        super();
    }

    @Override
    public boolean onStartNestedScroll(final CoordinatorLayout coordinatorLayout, final FloatingActionButton child,
                                       final View directTargetChild, final View target, final int nestedScrollAxes)
    {
        // Ensure we react to vertical scrolling
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL
                || super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
    }

    @Override
    public void onNestedScroll(final CoordinatorLayout coordinatorLayout, final FloatingActionButton child,
                               final View target, final int dxConsumed, final int dyConsumed,
                               final int dxUnconsumed, final int dyUnconsumed)
    {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);


        sumTransfer += dyConsumed;

        //Log.i("ALI", dxConsumed + " / " + dyConsumed + " / " + dxUnconsumed + " / " + dyUnconsumed + " / " + sumTransfer);

        if (sumTransfer > STEP)
        {
            sumTransfer = STEP;
        }
        else if (sumTransfer < -STEP)
        {
            sumTransfer = -STEP;
        }

        if (sumTransfer == STEP && !this.mIsAnimatingOut && child.getVisibility() == View.VISIBLE)
        {
            // User scrolled down and the FAB is currently visible -> hide the FAB
            animateOut(child);
        }
        else if (sumTransfer == -STEP && child.getVisibility() != View.VISIBLE)
        {
            // User scrolled up and the FAB is currently not visible -> show the FAB
            animateIn(child);
        }
    }

    // Same animation that FloatingActionButton.Behavior uses to hide the FAB when the AppBarLayout exits
    private void animateOut(final FloatingActionButton button)
    {
        /*if (Build.VERSION.SDK_INT >= 14)
        {
            ViewCompat.animate(button).scaleX(0.0F).scaleY(0.0F).alpha(0.0F).setInterpolator(INTERPOLATOR).withLayer()
                    .setListener(new ViewPropertyAnimatorListener()
                    {
                        public void onAnimationStart(View view)
                        {
                            ScrollAwareFABBehavior.this.mIsAnimatingOut = true;
                        }

                        public void onAnimationCancel(View view)
                        {
                            ScrollAwareFABBehavior.this.mIsAnimatingOut = false;
                        }

                        public void onAnimationEnd(View view)
                        {
                            ScrollAwareFABBehavior.this.mIsAnimatingOut = false;
                            view.setVisibility(View.GONE);
                        }
                    }).start();
        }
        else*/
        {
            Animation anim = AnimationUtils.loadAnimation(button.getContext(), R.anim.abc_slide_out_bottom);
            anim.setInterpolator(INTERPOLATOR);
            anim.setDuration(500L);
            anim.setAnimationListener(new Animation.AnimationListener()
            {
                public void onAnimationStart(Animation animation)
                {
                    ScrollAwareFabBehavior.this.mIsAnimatingOut = true;
                }

                public void onAnimationEnd(Animation animation)
                {
                    ScrollAwareFabBehavior.this.mIsAnimatingOut = false;
                    button.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onAnimationRepeat(final Animation animation)
                {
                }
            });
            button.startAnimation(anim);
        }
    }

    // Same animation that FloatingActionButton.Behavior uses to show the FAB when the AppBarLayout enters
    private void animateIn(FloatingActionButton button)
    {
        button.setVisibility(View.VISIBLE);
        /*if (Build.VERSION.SDK_INT >= 14)
        {
            ViewCompat.animate(button).scaleX(1.0F).scaleY(1.0F).alpha(1.0F)
                    .setInterpolator(INTERPOLATOR).withLayer().setListener(null)
                    .start();
        }
        else*/
        {
            Animation anim = AnimationUtils.loadAnimation(button.getContext(), R.anim.abc_slide_in_bottom);
            anim.setDuration(500L);
            anim.setInterpolator(INTERPOLATOR);
            button.startAnimation(anim);
        }
    }
}