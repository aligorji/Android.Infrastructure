package ir.aligorji.androidinfrastructure.example.viewholder;


import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.pnikosis.materialishprogress.ProgressWheel;

import ir.aligorji.androidinfrastructure.adapters.BindingRecyclerPagingAdapter;
import ir.aligorji.androidinfrastructure.example.R;

public class LoadingViewHolder extends BindingRecyclerPagingAdapter.InnerLoadingViewHolder
{

    public final ProgressWheel progress;
    public final View loadingPanel;
    public final Button reloadButton;
    public final TextView textError;


    public LoadingViewHolder(View itemView)
    {
        super(itemView);
        progress = (ProgressWheel) itemView.findViewById(R.id.progress_loading);
        loadingPanel = itemView.findViewById(R.id.panel_loading);
        textError = (TextView) itemView.findViewById(R.id.txv_error_message);
        reloadButton = (Button) itemView.findViewById(R.id.reloadbutton);
    }

    public void loading()
    {
        textError.setVisibility(View.GONE);
        reloadButton.setVisibility(View.GONE);
        reloadButton.setOnClickListener(null);

        progress.spin();
        progress.setVisibility(View.VISIBLE);
        loadingPanel.setVisibility(View.VISIBLE);
    }


    public void error(String message, View.OnClickListener clickListener)
    {
        textError.setText(message);
        textError.setVisibility(View.VISIBLE);
        reloadButton.setVisibility(View.VISIBLE);
        reloadButton.setOnClickListener(clickListener);

        progress.stopSpinning();
        progress.setVisibility(View.GONE);
        loadingPanel.setVisibility(View.GONE);
    }
}

