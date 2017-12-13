package ir.aligorji.androidinfrastructure.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.design.widget.TextInputLayout;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import ir.aligorji.androidinfrastructure.R;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


public class MyTextInputLayout extends TextInputLayout {

    private Typeface mTypefaceError = null;
    private boolean initErrorView = false;


    public MyTextInputLayout(Context context) {
        super(context);
    }

    public MyTextInputLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFontPath(attrs);
    }

    public MyTextInputLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFontPath(attrs);
    }

    private void setFontPath(AttributeSet attrs) {
        int attrId = CalligraphyConfig.get().getAttrId();
        String fontPath = pullHintFontPathFromTextAppearance(getContext(), attrs, attrId);
        if (fontPath == null)
        {
            return;
        }
        mTypefaceError = Typeface.createFromAsset(getContext().getAssets(), fontPath);
    }


    static String pullHintFontPathFromTextAppearance(final Context context, AttributeSet attrs, int attributeId) {
        if (attributeId == -1 || attrs == null) {
            return null;
        }

        int textAppearanceId = -1;
        final TypedArray typedArrayAttr = context.obtainStyledAttributes(attrs, new int[]{android.support.design.R.attr.errorTextAppearance});
        if (typedArrayAttr != null) {
            try {
                textAppearanceId = typedArrayAttr.getResourceId(0, -1);
            } catch (Exception ignored) {
                // Failed for some reason
                return null;
            } finally {
                typedArrayAttr.recycle();
            }
        }

        final TypedArray textAppearanceAttrs = context.obtainStyledAttributes(textAppearanceId, new int[]{attributeId});
        if (textAppearanceAttrs != null) {
            try {
                return textAppearanceAttrs.getString(0);
            } catch (Exception ignore) {
                // Failed for some reason.
                return null;
            } finally {
                textAppearanceAttrs.recycle();
            }
        }
        return null;
    }

    @Override
    public final void setErrorEnabled(boolean enabled)
    {
        super.setErrorEnabled(enabled);

        if (!enabled)
        {
            initErrorView = false;
            return;
        }

        if (initErrorView || mTypefaceError == null)
        {
            return;
        }

        TextView errorView = (TextView) findViewById(R.id.textinput_error);

        if (errorView == null)
        {
            return;
        }

        errorView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        errorView.setGravity(Gravity.RIGHT | Gravity.TOP);
        errorView.setTypeface(mTypefaceError);

        initErrorView = true;
    }


}