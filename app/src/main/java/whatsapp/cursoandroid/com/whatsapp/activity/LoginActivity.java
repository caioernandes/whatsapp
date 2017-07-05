package whatsapp.cursoandroid.com.whatsapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.firebase.database.DatabaseReference;

import whatsapp.cursoandroid.com.whatsapp.R;
import whatsapp.cursoandroid.com.whatsapp.config.ConfiguracaoFirebase;

public class LoginActivity extends AppCompatActivity {

    private DatabaseReference referenciaDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();

        referenciaDatabase = ConfiguracaoFirebase.getFirebase();
        referenciaDatabase.child("pontos").setValue("800");
    }

    private void init() {

    }

    public void abrirCadastroUsuario(View view) {
        startActivity(new Intent(this, CadastroUsuarioActivity.class));
    }
}
