/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javasort;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeMap;

/**
 *
 * @author TANAKA_Hidemune
 */
public class JavaSort {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage : javasort [OPTION] Source.txt");
            System.out.println("Option : -e:encode          default of encode : utf-8");
            System.out.println("Option : -o:destFilename    default Syste.out");
            
            System.exit(1);
        }
        String enc = "utf-8";
        String outputfile = "";
        String[] param;
        for(String argument: args) {
            param = argument.split(":");
            if ("-e".equals(param[0])) {
                enc = param[1];
            } else if ("-o".equals(param[0])) {
                outputfile = param[1];
            } else {
	        readFile(enc, argument, outputfile);
            }
        }
    }
    public static void readFile(String enc, String filename, String outputfile) {
        //String crlf = System.getProperty("line.separator");
        System.err.println("enc:" + enc);
        System.err.println("filename:" + filename);
        ArrayList al = new ArrayList();
        try{
            InputStreamReader f = new InputStreamReader(new FileInputStream(filename), enc);
            BufferedReader b = new BufferedReader(f);

            String s;
            while((s = b.readLine())!=null){
                al.add(s);
            }
            b.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        Object[] oa = al.toArray();            // 配列に変換
        Arrays.sort(oa, new Comparator() {

            @Override
            public int compare(Object t, Object t1) {
                String s1 = (String) t;
                String s2 = (String) t1;
                s1 = s1.toLowerCase();
                s2 = s2.toLowerCase();
                return s1.compareTo(s2);
            }
        });
        if (outputfile.equals("")) {
            for(int i = 0; i < oa.length; i++){
              // ソート前の配列とソート後の配列を並べて表示
              System.out.println(oa[i]);      
            }
        } else {
            writeFile(oa, enc, outputfile);
        }
    }
    
    public static void writeFile(Object[] oa, String enc, String filename) {
        
        //CSVの書き込み
        try {
            File csv = new File(filename); // CSVデータファイル
            //古いファイルのバックアップ
            if (csv.exists()) {
                File fileB = new File(csv.getAbsolutePath() + "~");
                if (fileB.exists()) {
                    fileB.delete();
                }
                csv.renameTo(fileB);
            }
            // 常に新規作成
            PrintWriter bw;
            bw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csv),enc)));
            
            //データ部
            for (int i = 0; i < oa.length; i++) {
                bw.write((String) oa[i]);
                bw.println();
            }
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
}
