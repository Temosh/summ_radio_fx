package com.summ.radio;

import javafx.fxml.FXML;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class Controller {

    private static final String STREAM_URL = "http://media-ice.musicradio.com:80/CapitalMP3";

    private Player player;

    @FXML
    private void onPlay() {
        InputStream is;

        try {
            URL url = new URL(STREAM_URL);
            printStreamHeaders(url);
            is = url.openStream();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        try {
            player = new Player(new BufferedInputStream(is));
        } catch (JavaLayerException e) {
            e.printStackTrace();
            return;
        }

        Thread mediaThread = new Thread(() -> {
            try {
                player.play();
            } catch (JavaLayerException e) {
                e.printStackTrace();
            }
        });

        mediaThread.setDaemon(true);
        mediaThread.start();
    }

    @FXML
    private void onStop() {
        if (player != null) player.close();
    }

    private void printStreamHeaders(URL url) throws IOException {
        Map<String, List<String>> headers = url.openConnection().getHeaderFields();
        headers.entrySet().stream()
                .sorted(Comparator.comparing(e -> e.getKey() == null ? "" : e.getKey()))
                .forEach(e -> System.out.println(e.getKey() + ": " + e.getValue()));
    }
}
