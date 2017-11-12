package edu.bsu.cs222;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;

public class APICaller {

    public Reader makeAPICall(URL url) throws IOException{
        URLConnection urlConnection = url.openConnection();
        InputStream inputStream = urlConnection.getInputStream();
        return new InputStreamReader(inputStream);
    }
}
