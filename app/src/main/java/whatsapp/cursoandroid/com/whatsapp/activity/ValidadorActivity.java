package whatsapp.cursoandroid.com.whatsapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.HashMap;

import whatsapp.cursoandroid.com.whatsapp.R;
import whatsapp.cursoandroid.com.whatsapp.helper.Preferencias;

public class ValidadorActivity extends AppCompatActivity {

    private EditText mCodigoValidacao;
    private Button mValidar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validador);
        init();

        SimpleMaskFormatter simpleMaskCodigoValidacao = new SimpleMaskFormatter("NNNN");
        MaskTextWatcher maskCodigoValidacao = new MaskTextWatcher(mCodigoValidacao, simpleMaskCodigoValidacao);
        mCodigoValidacao.addTextChangedListener(maskCodigoValidacao);

        mValidar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Preferencias preferencias = new Preferencias(getApplicationContext());
                HashMap<String, String> usuario = preferencias.getDadosUsuario();

                String tokenGerado = usuario.get("toke");
                String tokenDigitado = mCodigoValidacao.getText().toString();

                Log.i("Token gerado", tokenGerado);
                Log.i("Token digitado", tokenDigitado);

                if (tokenDigitado.equals(tokenGerado)) {
                    Toast.makeText(getApplicationContext(), "Token válido.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Token inválido.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void init() {
        mCodigoValidacao = (EditText) findViewById(R.id.edit_cod_validacao);
        mValidar = (Button) findViewById(R.id.bt_validar);
    }
}
