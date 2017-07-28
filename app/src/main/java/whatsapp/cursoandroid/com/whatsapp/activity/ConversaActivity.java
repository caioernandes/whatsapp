package whatsapp.cursoandroid.com.whatsapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

import whatsapp.cursoandroid.com.whatsapp.R;
import whatsapp.cursoandroid.com.whatsapp.config.ConfiguracaoFirebase;
import whatsapp.cursoandroid.com.whatsapp.helper.Base64Custom;
import whatsapp.cursoandroid.com.whatsapp.helper.Preferencias;
import whatsapp.cursoandroid.com.whatsapp.model.Mensagem;

public class ConversaActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText editMensagem;
    private ImageButton btMensagem;
    private DatabaseReference firebase;

    //dados de destinatário
    private String nomeUsuarioDestinatario;
    private String idUsuarioDestinatario;

    //dados do remetente
    private String idUsuarioRemetente;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversa);
        init();

        Preferencias preferencias = new Preferencias(this);
        idUsuarioRemetente = preferencias.getIdentificador();

        Bundle extra = getIntent().getExtras();

        if (extra != null) {
            nomeUsuarioDestinatario = extra.getString("nome");
            idUsuarioDestinatario = Base64Custom.codificarBase64(extra.getString("email"));
        }

        //configurar toolbar
        toolbar.setTitle(nomeUsuarioDestinatario);
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left);
        setSupportActionBar(toolbar);

        btMensagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textoMensagem = editMensagem.getText().toString();
                if (textoMensagem.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Digite uma mensagem para enviar.", Toast.LENGTH_LONG).show();
                } else {
                    Mensagem mensagem = new Mensagem();
                    mensagem.setIdUsuario(idUsuarioRemetente);
                    mensagem.setMensagem(textoMensagem);

                    salvarMensagem(idUsuarioRemetente, idUsuarioDestinatario, mensagem);
                    editMensagem.setText("");
                }
            }
        });
    }

    private boolean salvarMensagem(String idRemetente, String idDestinatario, Mensagem mensagem) {
        try {
            firebase = ConfiguracaoFirebase.getFirebase().child("mensagens");
            firebase.child(idRemetente)
                    .child(idDestinatario)
                    .push()
                    .setValue(mensagem);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Erro: " + e.getMessage(), Toast.LENGTH_LONG).show();
            return false;
        }
    }

    public void init() {
        toolbar = (Toolbar) findViewById(R.id.tb_conversa);
        editMensagem = (EditText) findViewById(R.id.edit_conversa);
        btMensagem = (ImageButton) findViewById(R.id.bt_enviar);
    }
}
