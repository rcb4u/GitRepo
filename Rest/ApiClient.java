package com.example.rspl_rahul.gitrepo.Rest;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by rspl-rahul on 30/11/17.
 */

public class ApiClient {

    public void auth(Context authcontext) {
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509", "BC");
            InputStream is = authcontext.getResources().getAssets().open("latest.crt");
            InputStream caInput = new BufferedInputStream(is);
            Certificate ca;
            try {
                ca = cf.generateCertificate(caInput);
                System.out.println("ca=" + ((X509Certificate) ca).getSubjectDN());
            } finally {
                caInput.close();
            }
            // Create a KeyStore containing our trusted CAs
            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);
            // Create a TrustManager that trusts the CAs in our KeyStore
            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);
            // Create an SSLContext that uses our TrustManager
            SSLContext context = SSLContext.getInstance("TLSv1.2");
            context.init(null, tmf.getTrustManagers(), null);
            // Tell the URLConnection to use a SocketFactory from our SSLContext
            SocketFactory sf = SSLSocketFactory.getDefault();
            SSLSocket socket = (SSLSocket) sf.createSocket("api-dev.fyi.lk", 9443);
            HostnameVerifier hv = HttpsURLConnection.getDefaultHostnameVerifier();
            SSLSession s = socket.getSession();
            socket.close();
            HostnameVerifier allHostsValid = new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
            // At this point SSLSocket performed certificate verificaiton and
            // we have performed hostname verification, so it is safe to proceed.
            URL url = new URL("https://api-dev.fyi.lk:8243/scope/1.0/get_movies?token=UDMKyQPaH1fzfn0ABGoM4rlrwVMXi17O");
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Authorization", "Bearer 232d244b-d35f-3918-b82a-c1a9b9e2ec29");
            urlConnection.setSSLSocketFactory(context.getSocketFactory());

            if (urlConnection.getResponseCode() == HttpsURLConnection.HTTP_OK) {

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String line;
                while ((line = reader.readLine()) != null) {
                    StringBuilder result = new StringBuilder();
                    result.append(line);
                    JSONObject readers = new JSONObject(result.toString());
                    Log.e("GetMovies", String.valueOf(readers));
                    Boolean state = (readers.getBoolean("state"));
                    Log.e("state", String.valueOf(state));
                    JSONArray data = readers.getJSONArray("data");
                    Log.e("data", String.valueOf(data.get(0)));

                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
