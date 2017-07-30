package whatsapp.cursoandroid.com.whatsapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import whatsapp.cursoandroid.com.whatsapp.R;
import whatsapp.cursoandroid.com.whatsapp.model.Conversa;


public class ConversaAdapter extends ArrayAdapter<Conversa> {

    private ArrayList<Conversa> conversas;
    private Context context;

    public ConversaAdapter(@NonNull Context context, @NonNull ArrayList<Conversa> objects) {
        super(context, 0, objects);
        this.conversas = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = null;

        //verifica se a lista está vazia
        if(conversas != null) {
            //inicializar obj para montagem da view
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            //monta view a partir do xml
            view = inflater.inflate(R.layout.lista_conversa, parent, false);

            //recupera elemento para exibição
            TextView nomeContato = (TextView) view.findViewById(R.id.tv_nome);
            TextView ultimaConversa = (TextView) view.findViewById(R.id.tv_conversa);

            Conversa conversa = conversas.get(position);
            nomeContato.setText(conversa.getNome());
            ultimaConversa.setText(conversa.getMensagem());
        }

        return view;
    }
}
