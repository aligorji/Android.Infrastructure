package ir.aligorji.androidinfrastructure.models;


import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.graphics.drawable.Drawable;

import ir.aligorji.androidinfrastructure.BR;

public class ContentPlaceholderViewModel extends BaseObservable
{
    private boolean isVisible;
    private String message;
    private Drawable icon;

    @Bindable
    public boolean isVisible()
    {
        return isVisible;
    }

    public void setVisible(boolean visible)
    {
        this.isVisible = visible;
        notifyPropertyChanged(BR.visible);
    }

    @Bindable
    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
        notifyPropertyChanged(BR.message);
    }

    @Bindable
    public Drawable getIcon()
    {
        return icon;
    }

    public void setIcon(Drawable icon)
    {
        this.icon = icon;
        notifyPropertyChanged(BR.icon);
    }

}
