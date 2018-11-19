package br.com.belapp.belapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import br.com.belapp.belapp.R;
import br.com.belapp.belapp.model.Estabelecimento;
import br.com.belapp.belapp.model.Teste;
import br.com.belapp.belapp.presenter.ClickRecyclerView_Interface;

public class SaloesActivity extends AppCompatActivity implements ClickRecyclerView_Interface {

    TextView tvTeste, tvLatitude, tvLongitude;

    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Teste> lista;
    ArrayList<Teste> lista2;
    FirebaseDatabase firebaseDatabase;
    Estabelecimento estabelecimento;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saloes);

        tvTeste = findViewById(R.id.tvTeste);
        tvLatitude = findViewById(R.id.tvLatitude);
        tvLongitude = findViewById(R.id.tvLongitude);

        String categoria = getIntent().getStringExtra("categoria");
        double latitude = getIntent().getDoubleExtra("latitude", -8);
        double longitude = getIntent().getDoubleExtra("longitude", -36);
        tvTeste.setText(categoria);
        tvLatitude.setText(String.valueOf(latitude));
        tvLongitude.setText(String.valueOf(longitude));

        recyclerView = findViewById(R.id.rvSaloes);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
//        lista = new ArrayList<Teste>();
//        lista2 = new ArrayList<Teste>();
//        lista.add(new Teste("00","Salao Beauty", "Barba"));
//        lista.add(new Teste("01","Salao Great", "Cabelo"));
//        lista.add(new Teste("02","Salao Beauty", "Barba"));
//        lista.add(new Teste("03","Salao Great", "Olho"));
//        lista.add(new Teste("04","Salao Beauty", "Unha"));
//        lista.add(new Teste("05","Salao Great", "Olho"));
//
//        for (int i = 0; i < ((Integer) lista.size()); i++){
//            if (lista.get(i).getCateg().equals(categoria)){
//                lista2.add(lista.get(i));
//            }
//        }
//
//        myAdapter = new SalaoAdapter(this, lista2);
//        recyclerView.setAdapter(myAdapter);
    }




    @Override
    public void onCustomClick(Object object)
    {
        Estabelecimento novo = (Estabelecimento) object;
        String nome = novo.getmNome();
        Toast.makeText(this, "Vc clickou em :"+nome, Toast.LENGTH_LONG).show();
    }
}
