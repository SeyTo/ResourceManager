package jigglypuff;

import rm.u;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by rj on 7/26/2016.
 */
public class Torchick {

    public static void main(String[] args) {
        System.out.println("Starting...");
        File file = new File("D:/rj/Java/");
        List<File> list = new ArrayList<>();
        u.search(file, list);

        for (File file1 : list) {
            if (file1.getAbsolutePath().toLowerCase().contains("jmeter")) {
                System.out.println("FOUND : " + file1.getPath());
            }
        }
    }
}
