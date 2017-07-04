package whatsapp.cursoandroid.com.whatsapp.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.HashMap;
import java.util.Random;

import whatsapp.cursoandroid.com.whatsapp.R;
import whatsapp.cursoandroid.com.whatsapp.helper.Permissao;
import whatsapp.cursoandroid.com.whatsapp.helper.Preferencias;

public class LoginActivity extends AppCompatActivity {

    private EditText mNome;
    private EditText mTelefone;
    private EditText mCodPais;
    private EditText mCodArea;
    private Button mCadastrar;
    private String[] permissoesNecessarias = new String[] {
            Manifest.permission.SEND_SMS,
            Manifest.permission.INTERNET
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();

        Permissao.validarPermissoes(1, this, permissoesNecessarias);

        /*Definir mascaras*/
        SimpleMaskFormatter simpleMaskCodPais = new SimpleMaskFormatter("+NN");
        SimpleMaskFormatter simpleMaskCodArea = new SimpleMaskFormatter("NN");
        SimpleMaskFormatter simpleMaskTelefone = new SimpleMaskFormatter("NNNNN-NNNN");

        MaskTextWatcher maskCodPais = new MaskTextWatcher(mCodPais, simpleMaskCodPais);
        MaskTextWatcher maskCodArea = new MaskTextWatcher(mCodArea, simpleMaskCodArea);
        MaskTextWatcher maskTelefone = new MaskTextWatcher(mTelefone, simpleMaskTelefone);

        mCodPais.addTextChangedListener(maskCodPais);
        mCodArea.addTextChangedListener(maskCodArea);
        mTelefone.addTextChangedListener(maskTelefone);

        mCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nomeUsuario = mNome.getText().toString();
                String telefoneCompleto = mCodPais.getText().toString() +
                                            mCodArea.getText().toString() +
                                            mTelefone.getText().toString();

                String telefoneSemFormatacao = telefoneCompleto.replace("+","");
                telefoneSemFormatacao = telefoneSemFormatacao.replace("-","");
                Log.i("TELEFONE", "T:" + telefoneSemFormatacao);

                //Gerar token
                Random random = new Random();
                int numeroRandomico = random.nextInt(9999 - 1000) + 1000;
                String token = String.valueOf(numeroRandomico);
                String mensagemEnvio = "WhatsApp código de confirmação: " + token;

                Log.i("TOKEN", "T:" + token);

                //Salvar os dados para validação
                Preferencias preferencias = new Preferencias(getApplicationContext());
                preferencias.salvarUsuarioPreferencias(nomeUsuario, telefoneSemFormatacao, token);

                HashMap<String, String> usuario = preferencias.getDadosUsuario();
                Log.i("TOKEN", "T:" + usuario.get("token"));

                //Envio SMS
                boolean enviadoSms = enviarSms("+" + telefoneSemFormatacao, mensagemEnvio);

                if (enviadoSms) {
                    Intent intent = new Intent(getApplicationContext(), ValidadorActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Problema ao enviar SMS, tente novamente!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void init() {
        mNome = (EditText) findViewById(R.id.edit_nome);
        mTelefone = (EditText) findViewById(R.id.edit_telefone);
        mCodPais = (EditText) findViewById(R.id.edit_cod_pais);
        mCodArea = (EditText) findViewById(R.id.edit_cod_area);
        mCadastrar = (Button) findViewById(R.id.bt_cadastrar);
    }

    /* Envio de SMS */
    private boolean enviarSms(String telefone, String mensagem) {
        try {

            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(telefone, null, mensagem, null, null);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for (int resultado : grantResults) {
            if (resultado == PackageManager.PERMISSION_DENIED) {
                alertaValidacaoPermissao();
            }
        }
    }

    private void alertaValidacaoPermissao() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissões negadas");
        builder.setMessage("Para utilizar esse app, é necessário aceitar as permissões.");
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
