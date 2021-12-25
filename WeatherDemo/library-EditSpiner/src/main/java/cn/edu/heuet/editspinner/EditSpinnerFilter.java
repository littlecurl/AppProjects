package cn.edu.heuet.editspinner;

/**
 * EditSpinnerFilter
 *
 * @author WrBug
 * @since 2017/2/25
 */
public interface EditSpinnerFilter {
    /**
     * editText输入监听
     * @param keyword
     * @return
     */
    boolean onFilter(String keyword);
}
