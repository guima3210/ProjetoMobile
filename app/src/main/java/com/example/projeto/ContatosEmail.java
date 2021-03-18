package com.example.projeto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class ContatosEmail extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private int qualSolicitacao = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contatos_telefone);
        setTitle("Contatos - Lista de E-mails");
        lerJSON();
    }

    private void lerJSON() {
        int qualSolicitacao = 1;
        if (checkInternetConection()){
            progressDialog = ProgressDialog.show(this, "", "Baixando dados");
            new DownloadJson().execute("http://mfpledon.com.br/listadecontatosbck.json");
        } else{
            Toast.makeText(getApplicationContext(),"Sem conexão. Verifique.", Toast.LENGTH_LONG).show();
        }
    }



    public boolean checkInternetConection() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }
    public void mostrarJSONFuncionarios(String strjson) {
        //recebe uma String com os dados do JSON
        String data = "";
        try {
            JSONObject objRaiz = new JSONObject(strjson);
            JSONArray jsonArray = objRaiz.optJSONArray("listacontatos");
            JSONObject jsonObject = null;
            //percorre o vetor de funcionarios e pega o nome para imprimir
            int i  = 0;
            final String[] dados = new String[jsonArray.length()];
            final String[] numeros = new String[jsonArray.length()];
            while(i<jsonArray.length()){
                jsonObject = jsonArray.getJSONObject(i);

                String nome = jsonObject.optString("nomecontato");
                String telefone = jsonObject.optString("email");
                data = " \n\n nome: "+nome + " / email: " + telefone ;
                numeros[i] = telefone;
                dados[i] = data;
                i++;
                jsonObject = null;

            }
            final ArrayAdapter<String> myadapter = new ArrayAdapter<String>(getApplicationContext(),
                    R.layout.item_list2,
                    R.id.item_list,
                    dados);
            ListView lista = (ListView) findViewById(R.id.lvMain);
            lista.setAdapter(myadapter);
            lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Contato aluno2 = new Contato();
                    String n = numeros[position];

                    email(n);
                }
            });
            progressDialog.dismiss();
        } catch (JSONException e) {
        }
    }
    public void email(String numero) {
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.setType("text/plain");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                new String[]{ numero } );
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Assunto da mensagem");
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Texto da mensagem");
        startActivity(Intent.createChooser(emailIntent, "Enviando e-mail..."));

    }
    private class DownloadJson extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            // params é um vetor onde params[0] é a URL
            try {
                return downloadJSON(params[0]);
            } catch (IOException e) {
                return "URL inválido";
            }
        }

        // onPostExecute exibe o resultado do AsyncTask
        @Override
        protected void onPostExecute(String result) {
            if (qualSolicitacao == 1) mostrarJSONFuncionarios(result);
        }

        private String downloadJSON(String myurl) throws IOException {
            InputStream is = null;
            String respostaHttp = "";
            HttpURLConnection conn = null;
            InputStream in = null;
            ByteArrayOutputStream bos = null;
            try {
                URL u = new URL(myurl);
                conn = (HttpURLConnection) u.openConnection();
                conn.setConnectTimeout(7000); // 7 segundos de timeout
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();
                in = conn.getInputStream();
                bos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len;
                while ((len = in.read(buffer)) > 0) {
                    bos.write(buffer, 0, len);
                }
                respostaHttp = bos.toString("UTF-8");
                return respostaHttp;
                //return "URL inválido ou estouro de memória ou...: \n" + ex.getMessage() + "\nmyurl: " + myurl;
            } catch (Exception ex) {
            } finally {
                if (in != null) in.close();
            }
            return respostaHttp;
        }
    }
}