package whatsapp.cursoandroid.com.whatsapp.helper;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;


public class Preferencias {

    private Context contexto;
    private SharedPreferences preferences;
    private final String NOME_ARQUIVO = "whatsapp.preferencias";
    private final int MODE = Context.MODE_PRIVATE;
    private SharedPreferences.Editor editor;

    private final String CHAVE_NOME = "nome";
    private final String CHAVE_TELFONE = "telefone";
    private final String CHAVE_TOKEN = "token";

    public Preferencias(Context contextParametro) {
        contexto = contextParametro;
        preferences = contexto.getSharedPreferences(NOME_ARQUIVO, MODE);
        editor = preferences.edit();
    }

    public void salvarUsuarioPreferencias(String nomeUsuario, String telefoneUsuario, String token) {
        editor.putString(CHAVE_NOME, nomeUsuario);
        editor.putString(CHAVE_TELFONE, telefoneUsuario);
        editor.putString(CHAVE_TOKEN, token);
        editor.commit();
    }

    public HashMap<String, String> getDadosUsuario() {
        HashMap<String, String> dadosUsuario = new HashMap<>();
        dadosUsuario.put(CHAVE_NOME, preferences.getString(CHAVE_NOME, null));
        dadosUsuario.put(CHAVE_TELFONE, preferences.getString(CHAVE_TELFONE, null));
        dadosUsuario.put(CHAVE_TOKEN, preferences.getString(CHAVE_TOKEN, null));

        return dadosUsuario;
    }
}
