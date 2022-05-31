package com.book.donation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.book.donation.R;
import com.book.donation.apicalls.model.SearchItemResBean;

import java.util.ArrayList;

public class ItemSearchListAdapter extends BaseAdapter{


    private ArrayList<SearchItemResBean.DataItem> mDisplayedValues;
    LayoutInflater inflater;
    ItemClickListener listener;

    public interface ItemClickListener{
        void onItemClick(int position);
    }

    public ItemSearchListAdapter(Context context, ArrayList<SearchItemResBean.DataItem> mProductArrayList, ItemClickListener listener) {
        this.mDisplayedValues = mProductArrayList;
        inflater = LayoutInflater.from(context);
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return mDisplayedValues.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        LinearLayout llContainer;
        TextView tvItemName;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.row_search, null);
            holder.tvItemName = convertView.findViewById(R.id.tvItemName);
            holder.llContainer = convertView.findViewById(R.id.llContainer);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvItemName.setText(mDisplayedValues.get(position).getProductName());
        holder.llContainer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                listener.onItemClick(position);
            }
        });

        return convertView;
    }
}
