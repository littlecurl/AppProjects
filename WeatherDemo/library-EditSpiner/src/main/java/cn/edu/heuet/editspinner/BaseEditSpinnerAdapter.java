package cn.edu.heuet.editspinner;

import android.widget.BaseAdapter;

/**
 * BaseEditSpinnerAdapter
 *
 * @author WrBug
 * @since 2017/2/25
 */
public abstract class BaseEditSpinnerAdapter extends BaseAdapter {
    /**
     * editText输入监听
     *
     * @return
     */
    public abstract EditSpinnerFilter getEditSpinnerFilter();

    /**
     * 获取需要填入editText的字符串
     * @param position
     * @return
     */
    public abstract String getItemString(int position);

}
