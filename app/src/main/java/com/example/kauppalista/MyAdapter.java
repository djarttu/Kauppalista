package com.example.kauppalista;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class MyAdapter extends ArrayAdapter<Item>   {

    ArrayList<Item> ostosLista = new ArrayList<>();
    private int k;
    public MyAdapter(Context context, int textViewResourceId, ArrayList<Item> objects) {
        super(context, textViewResourceId, objects);
        ostosLista = objects;

    }

    @Override
    public int getCount() {
        return super.getCount();
    }
    public void addItem(String ostos){
        ostosLista.add(new Item(ostos));
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.list_view_items, null);
        TextView textView = (TextView) v.findViewById(R.id.teksti);
        textView.setText(ostosLista.get(position).getOstos());
        final CheckBox cb = (CheckBox) v.findViewById(R.id.checkbox);
        cb.setChecked(false);
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean arg1){
                if(cb.isChecked())
                    MainActivity.delLista.add(position);
                if(!cb.isChecked())
                    for(int i=0; i<MainActivity.delLista.size(); i++){
                        k=MainActivity.delLista.get(i);
                        if(k==position)
                            MainActivity.delLista.remove(i);
                    }

            }

        });
        return v;

    }


 }