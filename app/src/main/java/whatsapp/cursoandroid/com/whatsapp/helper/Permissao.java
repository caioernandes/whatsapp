package whatsapp.cursoandroid.com.whatsapp.helper;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;


public class Permissao {

    public static boolean validarPermissoes(int requestCode, Activity activity, String[] permissoes) {

        if (Build.VERSION.SDK_INT >= 23) {
            /*
            percorre se as permissões passadas, verificando uma a uma
            se já tem permissão liberada
            * */

            List<String> listaPermissoes = new ArrayList<>();

            for (String permissao : permissoes) {
                boolean temPermissao = ContextCompat.checkSelfPermission(activity, permissao) == PackageManager.PERMISSION_GRANTED;
                if (!temPermissao) {
                    listaPermissoes.add(permissao);
                }
            }

            //caso a lista esteja vazia, não é necessário solicitar permissão
            if (listaPermissoes.isEmpty()) {
                return true;
            }

            String[] novasPermissoes = new String[listaPermissoes.size()];
            listaPermissoes.toArray(novasPermissoes);

            //solicitar permissão
            ActivityCompat.requestPermissions(activity, novasPermissoes, requestCode);
        }

        return true;
    }
}
