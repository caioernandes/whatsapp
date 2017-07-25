package whatsapp.cursoandroid.com.whatsapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import whatsapp.cursoandroid.com.whatsapp.R;
import whatsapp.cursoandroid.com.whatsapp.config.ConfiguracaoFirebase;
import whatsapp.cursoandroid.com.whatsapp.helper.Preferencias;
import whatsapp.cursoandroid.com.whatsapp.model.Contato;


public class ContatosFragment extends Fragment {

    private ListView listView;
    private ArrayAdapter arrayAdapter;
    private ArrayList<String> contatos;
    private DatabaseReference firebase;

    public ContatosFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_contatos, container, false);

        listView = (ListView) view.findViewById(R.id.lv_contatos);
        contatos = new ArrayList<>();

        Preferencias preferencias = new Preferencias(getActivity());
        String idUsuarioLogado = preferencias.getIdentificador();
        firebase = ConfiguracaoFirebase.getFirebase().child("contatos").child(idUsuarioLogado);

        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    //limpar lista
                    contatos.clear();

                    //listar contatos
                    for (DataSnapshot dados : dataSnapshot.getChildren()) {
                        Contato contato = dados.getValue(Contato.class);
                        contatos.add(contato.getNome());
                    }

                    arrayAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        arrayAdapter = new ArrayAdapter(
                getActivity(),
                R.layout.lista_contato,
                contatos
        );

        listView.setAdapter(arrayAdapter);

        return view;
    }

}
