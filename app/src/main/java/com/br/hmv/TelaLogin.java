package com.br.hmv;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.os.AsyncTask;
import android.content.Context;

public class TelaLogin extends AppCompatActivity {

    EditText editUsuario, editSenha;
    Button btnLogar;

    String url = "";
    String parametros = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_login);

        editUsuario = (EditText)findViewById(R.id.editUsuario);
        editSenha = (EditText)findViewById(R.id.editSenha);
        btnLogar = (Button)findViewById(R.id.btnLogar);

        btnLogar.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {

                ConnectivityManager connMgr = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                if (networkInfo != null && networkInfo.isConnected()){

                    String usuario = editUsuario.getText().toString();
                    String senha = editSenha.getText().toString();

                    if(usuario.isEmpty() || senha.isEmpty()){
                        Toast.makeText(getApplicationContext(), "Nenhum campo pode estar vazio!", Toast.LENGTH_LONG).show();
                    }else{
                        url = "http://192.168.17.160:80/solicita/logar.php";
                        parametros = "nome=" + usuario + "&senha=" + senha;
                        new SolicitaDados().execute(url);
                    }


                }else {
                    Toast.makeText(getApplicationContext(), "Nenhuma conexão foi detectada", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private class SolicitaDados extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... urls) {

            //try{
                return Conexao.postDados(urls[0], parametros);
            //}
           // return null;
        }

        @Override
        protected void onPostExecute(String resultado){
            editUsuario.setText(resultado);
        }

    }
}
