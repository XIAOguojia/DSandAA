package chapter4;

import java.io.File;

/**
 * Created by intellij IDEA
 * Author:Raven
 * Date:2017/12/16
 * Time:19:30
 */
public class FileList {
    public static void main(String[] args) {
        File dir = new File("d:\\PythonProgram");
        listAll(dir,0);
    }

    private static void listAll(File dir, int level) {
        System.out.println(getSpace(level)+dir.getName());
        level++;
        File[] files = dir.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()){
                listAll(files[i],level);
            }else {
                System.out.println(getSpace(level)+files[i].getName());
            }
        }
    }

    private static String getSpace(int level) {
        StringBuilder sb = new StringBuilder();
        sb.append("|--");
        for (int i = 0; i < level; i++) {
            sb.insert(0,"|  ");
        }
        return sb.toString();
    }
}
