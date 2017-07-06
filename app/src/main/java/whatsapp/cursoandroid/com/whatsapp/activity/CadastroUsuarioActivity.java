package whatsapp.cursoandroid.com.whatsapp.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import whatsapp.cursoandroid.com.whatsapp.R;
import whatsapp.cursoandroid.com.whatsapp.config.ConfiguracaoFirebase;
import whatsapp.cursoandroid.com.whatsapp.model.Usuario;

public class CadastroUsuarioActivity extends AppCompatActivity {

    private EditText mNome;
    private EditText mEmail;
    private EditText mSenha;
    private Button mCadastrar;

    private Usuario usuario;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);
        init();

        mCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario = new Usuario();
                usuario.setNome(mNome.getText().toString());
                usuario.setEmail(mEmail.getText().toString());
                usuario.setSenha(mSenha.getText().toString());

                if (usuario.getSenha().length() < 6) {
                    Toast.makeText(getApplicationContext(), "A senha deve conter pelo menos 6 caracteres!", Toast.LENGTH_LONG).show();
                } else {
                    cadastrarUsuario();
                }
            }
        });
    }

    private void init() {
        mNome = (EditText) findViewById(R.id.edit_cadastro_nome);
        mEmail = (EditText) findViewById(R.id.edit_cadastro_email);
        mSenha = (EditText) findViewById(R.id.edit_cadastro_senha);
        mCadastrar = (Button) findViewById(R.id.bt_cadastrar);
    }

    private void cadastrarUsuario() {
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(CadastroUsuarioActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Usuário cadastrado com sucesso!", Toast.LENGTH_LONG).show();

                    FirebaseUser usuarioFirebase = task.getResult().getUser();
                    usuario.setId(usuarioFirebase.getUid());
                    usuario.salvar();
                } else {
                    Toast.makeText(getApplicationContext(), "Erro ao cadastrar usuário!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
