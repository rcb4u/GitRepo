package com.example.rspl_rahul.gitrepo.Rest;

import android.content.Context;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
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

/**
 * Created by rspl-rahul on 20/1/18.
 */

public class authentication {
    private static authentication ourInstance;
    private Context context;
    public static authentication getInstance() {
        return ourInstance;
    }

    public static void init(Context context) {
        if (ourInstance == null) {
            ourInstance = new authentication(context);
        }
    }

    private authentication(Context context) {
        this.context = context;
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509", "BC");
            InputStream is = context.getResources().getAssets().open("latest.crt");
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
            SSLContext sslcontext = SSLContext.getInstance("TLSv1.2");
            sslcontext.init(null, tmf.getTrustManagers(), null);
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
        }catch (IOException e)
        {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }

    public Context getContext() {
        return context;
    }
}
