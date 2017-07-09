package whatsapp.cursoandroid.com.whatsapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import whatsapp.cursoandroid.com.whatsapp.R;
import whatsapp.cursoandroid.com.whatsapp.config.ConfiguracaoFirebase;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    private DatabaseReference referenciaFirebase;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }

    private void init() {
        referenciaFirebase = ConfiguracaoFirebase.getFirebase();

        mToolbar = (Toolbar) findViewById(R.id.toolbarWidget);
        mToolbar.setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(mToolbar);
    }

    private void deslogarUsuario() {
        ConfiguracaoFirebase.efetuarLogout(autenticacao);
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_sair:
                deslogarUsuario();
                return true;
            case R.id.item_adicionar:
                Toast.makeText(this, "Adicionar", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item_pesquisa:
                Toast.makeText(this, "Pesquisar", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
