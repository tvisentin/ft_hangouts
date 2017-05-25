package com.tvisenti.ft_hangouts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by tvisenti on 5/22/17.
 */

public class CustomAdapter extends ArrayAdapter<Contact> implements View.OnClickListener {
    private ArrayList<Contact> arrayContact;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtFirstName;
        TextView txtLastName;
        TextView txtPhone;
    }

    public CustomAdapter(ArrayList<Contact> data, Context context) {
        super(context, R.layout.row_list_layout, data);
        this.arrayContact = data;
        this.mContext=context;

    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        Contact dataModel=(Contact)object;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Contact contact = getItem(position);
        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_list_layout, parent, false);
            viewHolder.txtFirstName = (TextView) convertView.findViewById(R.id.txtFirstName);
            viewHolder.txtLastName = (TextView) convertView.findViewById(R.id.txtLastName);
            viewHolder.txtPhone = (TextView) convertView.findViewById(R.id.txtPhone);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        viewHolder.txtFirstName.setText(contact.getFirstName());
        viewHolder.txtLastName.setText(contact.getLastName());
        viewHolder.txtPhone.setText(contact.getPhone());
        return convertView;
    }
}