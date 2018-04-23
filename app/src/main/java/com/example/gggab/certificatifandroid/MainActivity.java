package com.example.gggab.certificatifandroid;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private TextView txt_dbSize;
    private ArrayList<Employe> employeArrayList;
    private List<String> stringList;
    private ArrayAdapter<String> arrayAdapter;
    private DBAdapter dbAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        employeArrayList = new ArrayList<>();
        stringList = new ArrayList<>();

        listView = findViewById(R.id.listview);
        txt_dbSize = findViewById(R.id.txt_dbSize);

        initList();

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, stringList);

        EmployesToString("all");

        ConnectToDatabase();
        new GetWebData().execute();
    }

    public void UpdateListView(String fonction) {
        EmployesToString(fonction);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserDetails(employeArrayList.get(position));
            }
        });

        arrayAdapter.notifyDataSetChanged();
    }

    private void UserDetails(Employe employe) {
        Intent i = new Intent(this, DetailEmploye.class);
        i.putExtra("nom",employe.getNom());
        i.putExtra("prenom",employe.getPrenom());
        i.putExtra("departement",employe.getDepartement());
        i.putExtra("taches",employe.getTaches());
        i.putExtra("fonction",employe.getFonction());
        startActivity(i);
        Log.i("OpenActivity : ", "DetailEmploye");
    }

    private void EmployesToString(String fonction) {
        stringList.clear();
        int cnt = 0;
        for (Employe empl :
                employeArrayList) {
            cnt++;
            String temp = empl.getFonction();
            if (temp != null) {
                if (!temp.equals(fonction)) {
                    if (fonction.equals("all")) {
                        String string = empl.getPrenom() + " " + empl.getNom() + " : " + empl.getDepartement() + " (" + empl.getFonction() + ")";
                        stringList.add(string);
                    }
                } else {
                    String string = empl.getPrenom() + " " + empl.getNom() + " : " + empl.getDepartement() + " (" + empl.getFonction() + ")";
                    stringList.add(string);
                }
            } else {
                String string = empl.getPrenom() + " " + empl.getNom() + " : " + empl.getDepartement() + " (" + empl.getFonction() + ")";
                stringList.add(string);
            }
        }
        try {
            txt_dbSize.setText("DB Size: " + String.valueOf(employeArrayList.size()));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void FetchData(View view) {
        ConnectToDatabase();
        new GetWebData().execute();
    }

    public void initList() {
        for (int i = 0; i < 5; i++) {
            Employe temp = new Employe("Employé#" + i, "test");
            employeArrayList.add(temp);
        }
        EmployesToString("all");
    }

    public void ConnectToDatabase() {
        dbAdapter = new DBAdapter(getApplicationContext());
        dbAdapter.open();

        getEmployes("all");
    }

    private void getEmployes(String fonction) {
        employeArrayList.clear();
        List<Employe> allEmployes = dbAdapter.selectAllEmployes();

        employeArrayList.addAll(allEmployes);

        UpdateListView(fonction);
    }

    public void DropDB(View view) {
        dbAdapter.DropAll();
        employeArrayList.clear();
        UpdateListView("all");
    }

    public void ShowEnseignant(View view) {
        UpdateListView("Enseignant");
    }

    public void ShowAdmin(View view) {
        UpdateListView("Administration");
    }

    private class GetWebData extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            InputStream is = getResources().openRawResource(R.raw.index);
            Writer writer = new StringWriter();
            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            String json_index = writer.toString();

            try {
                ParseJson(json_index);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        private void ParseJson(String json_index) throws JSONException {
            JSONObject jsonObject = new JSONObject(json_index);

            Iterator<?> keys = jsonObject.keys();

            while (keys.hasNext()) {
                String key = (String) keys.next();
                JSONArray nestedChilds = jsonObject.getJSONArray(key);
                for (int i = 0; i < nestedChilds.length(); i++) {
                    Gson gson = new Gson();
                    Employe employe = gson.fromJson(nestedChilds.getJSONObject(i).toString(), Employe.class);
                    employe.setFonction(key);
                    dbAdapter.insertEmploye(employe);
                }
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            getEmployes("all");
        }
    }
}
