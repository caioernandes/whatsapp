package br.com.whatsapp.caioenascimento.whatsapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import br.com.whatsapp.caioenascimento.whatsapp.R;

public class LoginActivity extends AppCompatActivity {

    private EditText telefone;
    private EditText editCodPais;
    private EditText editDdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        telefone = (EditText) findViewById(R.id.edit_telefone);
        editCodPais = (EditText) findViewById(R.id.edit_codigo_pais);
        editDdd = (EditText) findViewById(R.id.edit_ddd_numero);

        telefone.addTextChangedListener(maskTextWatcher("(NN) NNNNN-NNNN", telefone));
        editCodPais.addTextChangedListener(maskTextWatcher("+55", editCodPais));
        editDdd.addTextChangedListener(maskTextWatcher("(81)", editDdd));
    }

    public MaskTextWatcher maskTextWatcher(String formato, EditText editText) {
        SimpleMaskFormatter simpleMaskTelefone = new SimpleMaskFormatter(formato);
        MaskTextWatcher maskTelefone = new MaskTextWatcher(editText, simpleMaskTelefone);
        return maskTelefone;
    }
}
