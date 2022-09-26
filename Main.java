package CoreJavaLesson3Task2;

import java.io.*;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void saveGame(String uri, GameProgress gameProgress) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(uri);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(gameProgress);
        objectOutputStream.close();
    }

    public static void zipFiles(String zipUri, String[] uri) throws IOException {

        ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipUri));

        for (int i = 0; i < uri.length; i++) {
            FileInputStream file = new FileInputStream(Paths.get(uri[i]).toString());
            ZipEntry entry = new ZipEntry(Paths.get(uri[i]).getFileName().toString());
            zipOutputStream.putNextEntry(entry);
            byte[] buffer = new byte[file.available()];
            file.read(buffer);
            zipOutputStream.write(buffer);
            zipOutputStream.closeEntry();
            file.close();
            File deleteFile = new File(Paths.get(uri[i]).toString());
            deleteFile.delete();
        }

        zipOutputStream.close();
    }

    public static void main(String[] args) {
        String[] uri = {
                "D://GamesJava/savegames//save1.dat",
                "D://GamesJava/savegames//save2.dat",
                "D://GamesJava/savegames//save3.dat"
        };
        String zipUri = "D://GamesJava/savegames//save.zip";

        GameProgress[] gameProgress = new GameProgress[3];
        gameProgress[0] = new GameProgress(10, 20, 30, 40.0);
        gameProgress[1] = new GameProgress(13, 23, 33, 43.0);
        gameProgress[2] = new GameProgress(19, 29, 39, 49.0);

        try {
            for (int i = 0; i < uri.length; i++) {
                saveGame(uri[i], gameProgress[i]);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        try {
            zipFiles(zipUri, uri);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
