package com.example.gggab.certificatifandroid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by gggab(Zombietux) on 2018-04-23.
 */
public class DetailEmploye extends AppCompatActivity {
    private Employe employe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_employe);

        employe = new Employe(
                getIntent().getStringExtra("nom"),
                getIntent().getStringExtra("prenom"),
                getIntent().getStringExtra("departement"),
                getIntent().getStringExtra("taches"),
                getIntent().getStringExtra("fonction")
        );

        setTitle("Fiche Employe");

        TextView txt_nom = findViewById(R.id.txt_nom);
        TextView txt_prenom = findViewById(R.id.txt_prenom);
        TextView txt_departement = findViewById(R.id.txt_departement);
        TextView txt_taches = findViewById(R.id.txt_taches);
        TextView txt_fonction = findViewById(R.id.txt_fonction);

        txt_nom.setText(employe.getNom());
        txt_prenom.setText(employe.getPrenom());
        txt_departement.setText(employe.getDepartement());
        txt_taches.setText(employe.getTaches());
        txt_fonction.setText(employe.getFonction());
    }

    public void Retour(View view) {
        this.finish();
    }
}
