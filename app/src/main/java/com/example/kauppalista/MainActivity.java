package com.example.kauppalista;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Constraints;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.widget.ListPopupWindow.WRAP_CONTENT;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static List<Integer> delLista= new ArrayList<Integer>();
    ListView simpleList;
    ArrayList<Item> ostosLista = new ArrayList<>();
    MyAdapter myAdapter;
    private Context context;
    FileOutputStream stream;
    File path; // = getApplicationContext().getFilesDir();
    File file; // = new File(path, "lista.txt");
    BufferedWriter bw;
    private int k;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.lisaaOstos).setOnClickListener(this);
        findViewById(R.id.tulosta).setOnClickListener(this);
        findViewById(R.id.poistaValitut).setOnClickListener(this);
        path = new File(String.valueOf(getApplicationContext().getFilesDir()));
        file= new File(path, "lista.txt");
        if (file.exists()) {
            //StringBuilder text = new StringBuilder();
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;
                while ((line = br.readLine()) != null) {
                    ostosLista.add(new Item(line));
                    //myAdapter.addItem(line);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        simpleList = (ListView) findViewById(R.id.simpleListView);
        myAdapter = new MyAdapter(this, R.layout.list_view_items, ostosLista);
        simpleList.setAdapter(myAdapter);
        //simpleList.smoothScrollToPosition(simpleList.getCount() - 1);
        //simpleList.setSelection(myAdapter.getCount()-1);

    }
    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.lisaaOstos) {
            lisaaOstos1();

            }


        if(v.getId()==R.id.tulosta){
            ostosLista.clear();
            file.delete();
            myAdapter.notifyDataSetChanged();
        }
        if(v.getId()== R.id.poistaValitut){
            poistaOstos();
        }
    }
    private  String getFieldTextText(){
        EditText editor=findViewById(R.id.ostosTextField);
        String text = editor.getText().toString();
        editor.setText(null);
        return text;
    }
    private void paivitaOstosListaTiedostoon(){
        file.delete();
        for(int i=0; i<ostosLista.size(); i++){
            String k=ostosLista.get(i).getOstos();
            writeToFile(k);
        }
    }
    private  void lisaaOstos1() {

        String tavara = getFieldTextText();
        if (tavara != null && tavara.length() > 0) {
            {

                ostosLista.add(new Item(tavara));
                //simpleList.setAdapter(myAdapter);
                myAdapter.notifyDataSetChanged();
                writeToFile(tavara);


            }
        }
    }
    public void poistaOstos(){
        Collections.sort(delLista);
        Collections.reverse(delLista);
        for (int i = 0; i < delLista.size(); i++) {
            k = delLista.get(i);
            ostosLista.remove(k);
            TextView text = findViewById(R.id.numerot);
            text.setText(text.getText()+delLista.get(i).toString()+" ");

            myAdapter.notifyDataSetChanged();
        }
        paivitaOstosListaTiedostoon();
        delLista.clear();
    }
    private void writeToFile(String data) {
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            stream = new FileOutputStream(file, true);
            bw = new BufferedWriter(new OutputStreamWriter(stream));
            bw.write(data);
            bw.newLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
