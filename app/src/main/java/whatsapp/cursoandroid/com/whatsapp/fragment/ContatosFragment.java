package whatsapp.cursoandroid.com.whatsapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import whatsapp.cursoandroid.com.whatsapp.R;


public class ContatosFragment extends Fragment {

    private ListView listView;
    private ArrayAdapter arrayAdapter;
    private ArrayList<String> contatos;

    public ContatosFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_contatos, container, false);

        listView = (ListView) view.findViewById(R.id.lv_contatos);
        contatos = new ArrayList<>();
        contatos.add("Caio Ernandes");
        contatos.add("Jeyson Melo");
        contatos.add("Charles Lucena");

        arrayAdapter = new ArrayAdapter(
                getActivity(),
                R.layout.lista_contato,
                contatos
        );

        listView.setAdapter(arrayAdapter);

        return view;
    }

}
