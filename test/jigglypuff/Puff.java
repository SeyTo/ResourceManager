package jigglypuff;

import java.util.Scanner;

/**
 * Created by rj on 7/25/2016.
 */
public class Puff {

    public static void main(String[] args) {
        String[] test = new String[] {
                "c:/base1/base2/",  "d:/base1/base.txt",    "e:/base1/base",            "f:/",

                "key-key?",         "g:/key-key?/",         "h:/key-key?",              "i:/key-key?/key-key2?",

                "c:/base/**",       "c:/base/base/*",            "c:/**",

                "/base1/",          "c:/base1//",           "base1.txt",                "c:/base1/",

                "c:/key-key?/*",    "c:/key-key?/key-key?/**", "c:/base/base/**",       "c:/dir/key?",

                "c:/dir/key?/dir/key?", "c:/key?/",

                "http://blabla",    "file:///D:/blacla"
        };

        Scanner inp = new Scanner(System.in);
        String pattern = "";
        do {
            pattern = inp.nextLine();
            try {
                System.out.println(test[0].matches(pattern) + " " + test[1].matches(pattern) + " " + test[2].matches(pattern) + " " + test[3].matches(pattern));
                System.out.println(test[4].matches(pattern) + " " + test[5].matches(pattern) + " " + test[6].matches(pattern) + " " + test[7].matches(pattern));
                System.out.println(test[8].matches(pattern) + " " + test[9].matches(pattern) + " " + test[10].matches(pattern));
                System.out.println(test[11].matches(pattern)+ " " + test[12].matches(pattern)+ " " + test[13].matches(pattern) + " " + test[14].matches(pattern));
                System.out.println(test[15].matches(pattern)+ " " + test[16].matches(pattern) + " " +test[17].matches(pattern) + " " + test[18].matches(pattern));
                System.out.println(test[19].matches(pattern)+ " " + test[20].matches(pattern));
                System.out.println(test[21].matches(pattern)+ " " + test[22].matches(pattern));
                System.out.println();
            } catch (Exception e) {
                System.out.println("Error");
            }
        }while (true);
    }
}
