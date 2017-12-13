package ir.aligorji.androidinfrastructure.adapters;

public interface EndlessAdapter extends DefaultAdapter
{

    int getLoadedItemCount();

    int getTotalItems();

    void setTotalItems(int mTotalItems);

    void setUnlimitedItems();

    void setEndOfLoadItems();

    boolean isUnlimitedItems();

    boolean isEndOfLoadItems();

    void onErrorLoading(Object obj);

    void tryLoading();
}
