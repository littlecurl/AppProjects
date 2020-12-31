package com.hjq.widget.view.editspinner;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * SimpleAdapter
 *
 * @author WrBug
 * @since 2017/2/25
 */
public class SimpleAdapter extends BaseEditSpinnerAdapter implements com.hjq.widget.view.editspinner.EditSpinnerFilter {
    private final Context mContext;
    private final List<String> mSpinnerData;
    private final List<String> mCacheData;
    private final int[] indexs;

    public SimpleAdapter(Context context, List<String> spinnerData) {
        this.mContext = context;
        this.mSpinnerData = spinnerData;
        mCacheData = new ArrayList<>(spinnerData);
        indexs = new int[mSpinnerData.size()];
    }

    @Override
    public com.hjq.widget.view.editspinner.EditSpinnerFilter getEditSpinnerFilter() {
        return this;
    }

    @Override
    public String getItemString(int position) {
        return mSpinnerData.get(indexs[position]);
    }

    @Override
    public int getCount() {
        return mCacheData == null ? 0 : mCacheData.size();
    }

    @Override
    public String getItem(int position) {
        return mCacheData == null ? "" : mCacheData.get(position) == null ? "" : mCacheData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView;
        if (convertView == null) {
            textView = (TextView) LayoutInflater.from(mContext).inflate(android.R.layout.simple_spinner_item, null);
        } else {
            textView = (TextView) convertView;
        }
        textView.setText(Html.fromHtml(getItem(position)));
        return textView;
    }

    @Override
    public boolean onFilter(String keyword) {
        mCacheData.clear();
        // 空值匹配全部
        if (TextUtils.isEmpty(keyword.trim())) {
            mCacheData.addAll(mSpinnerData);
            for (int i = 0; i < indexs.length; i++) {
                indexs[i] = i;
            }
        }
        // 实现全量模糊匹配（优先匹配原词）
        else {
            StringBuilder builder = new StringBuilder();
            String[] keywordSplit = keyword.split("");
            builder.append(".*");
            for (String word : keywordSplit) {
                if (TextUtils.isEmpty(word)) {
                    continue;
                }
                word = replaceMetacharacter(word);
                builder.append(word).append(".*");
            }

            Pattern originPattern = Pattern.compile(replaceMetacharacter(keyword));
            Pattern fullPattern = Pattern.compile(builder.toString());

            for (int i = 0; i < mSpinnerData.size(); i++) {
                String itemStr = mSpinnerData.get(i);
                Matcher originMatcher = originPattern.matcher(itemStr);
               /*
               优先匹配原词
               比如，在 2223444 中匹配 234
               优先匹配 22 '234' 44
                */
                if (originMatcher.find()) {
                    coloringItemStr(true, i, itemStr, keyword);
                }
                // 匹配不到原词的话，全量最左寻找
                else {
                    Matcher fullMatcher = fullPattern.matcher(itemStr);
                    if (fullMatcher.find()) {
                        coloringItemStr(false, i, itemStr, keyword);
                    }
                }
            }
        }
        notifyDataSetChanged();
        return mCacheData.size() <= 0;
    }

    private void coloringItemStr(boolean origin, int i, String itemStr, String keyword) {
        indexs[mCacheData.size()] = i;
        StringBuilder colorStr = new StringBuilder();
        if (origin) {
            colorStr.append(itemStr.replaceFirst(keyword, "<font color=\"#aa0000\">" + keyword + "</font>"));
        } else {
            indexs[mCacheData.size()] = i;
            String beforeMatchedIndexStr = "";
            String afterMatchedIndexStr = "";

            int matchedStrLen = itemStr.length();
            String[] keywordSplit = keyword.split("");
            for (String highLightStr : keywordSplit) {
                if (TextUtils.isEmpty(highLightStr)) {
                    continue;
                }
                int index = itemStr.indexOf(highLightStr);
                if (index < matchedStrLen - 1) {
                    beforeMatchedIndexStr = itemStr.substring(0, index + 1);
                    afterMatchedIndexStr = itemStr.substring(index + 1);
                    itemStr = afterMatchedIndexStr;
                }
                if (!TextUtils.isEmpty(beforeMatchedIndexStr)) {
                    colorStr.append(beforeMatchedIndexStr.replaceFirst(highLightStr, "<font color=\"#aa0000\">" + highLightStr + "</font>"));
                }
            }
            colorStr.append(afterMatchedIndexStr);
        }
        mCacheData.add(colorStr.toString());
    }

    private String replaceMetacharacter(String keyword) {
        StringBuilder transferredKeyword = new StringBuilder();
        for (int i = 0, len = keyword.length(); i < len; i++) {
            String ch = String.valueOf(keyword.charAt(i));
            switch (ch) {
                case "\\":
                    ch = "\\\\";
                    break;
                case "^":
                    ch = "\\^";
                    break;
                case "$":
                    ch = "\\$";
                    break;
                case "*":
                    ch = "\\*";
                    break;
                case "+":
                    ch = "\\+";
                    break;
                case "?":
                    ch = "\\?";
                    break;
                case "{":
                    ch = "\\{";
                    break;
                case "}":
                    ch = "\\}";
                    break;
                case "[":
                    ch = "\\[";
                    break;
                case "]":
                    ch = "\\]";
                    break;
                case "(":
                    ch = "\\(";
                    break;
                case ")":
                    ch = "\\)";
                    break;
                case "|":
                    ch = "\\|";
                    break;
                case ".":
                    ch = "\\.";
                    break;
                case "!":
                    ch = "\\!";
                    break;
                default:
            }
            transferredKeyword.append(ch);
        }
        return transferredKeyword.toString();

    }
}
