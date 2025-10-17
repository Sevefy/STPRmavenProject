package ru.malofeev.module1.ZIP;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

enum MethodZIP{
    pack, unpack
}

@Data
@AllArgsConstructor
public class Zip {
    private String name;
    private String catalog;

    public void pack() throws IOException {
        Path path = Paths.get(catalog);
        if (! Files.exists(path)) throw new FileNotFoundException("Нет файла или папки: " + path);
        try (ZipOutputStream zout = new ZipOutputStream(
                new FileOutputStream(name+".zip")
        )){
            if (Files.isDirectory(path)) zipDirectory(zout, path, path);
            else zipFile(zout, path, path.getParent());
            System.out.println("Архив создан");
        }
    }
    public void unpack() throws IOException{
        Path path = Paths.get(name + ".zip");
        if (!Files.exists(path)) throw new FileNotFoundException("Не найден файл: " + name + ".zip");
        Path outpath = Paths.get(catalog);
        Files.createDirectories(outpath);

        try (ZipInputStream zin = new ZipInputStream(
                new FileInputStream(path.toFile())
        )){
            ZipEntry zipEntry;
            while((zipEntry = zin.getNextEntry()) != null) extractEntry(zin, zipEntry, outpath);
            System.out.println("Архив распакован");
        }
    }

    private void zipDirectory(ZipOutputStream zout, Path folder, Path baseFolder) throws IOException {
        Files.walk(folder)
                .filter(path -> !Files.isDirectory(path))
                .forEach(file -> {
                    try {
                        zipFile(zout, file, baseFolder);
                    } catch (IOException e) {
                        throw new UncheckedIOException(e);
                    }
                });
    }

    private void zipFile(ZipOutputStream zout, Path file, Path baseFolder) throws IOException {
        // Создаем относительный путь
        String entryName = baseFolder.relativize(file).toString().replace("\\", "/");

        byte[] fileContent = Files.readAllBytes(file);
        ZipEntry entry = new ZipEntry(entryName);

        zout.putNextEntry(entry);
        zout.write(fileContent);
        zout.closeEntry();
    }

    private void extractEntry(ZipInputStream zin, ZipEntry entry, Path outpath) throws IOException {
        Path filepath = outpath.resolve(entry.getName());

        Files.createDirectories(filepath.getParent());

        if (entry.isDirectory()){
            Files.createDirectories(filepath);
        }else{
            try (OutputStream out = Files.newOutputStream(filepath)){
                byte[] buffer = new byte[8192];
                int len;
                while((len = zin.read(buffer)) > 0){
                    out.write(buffer, 0, len);
                }
            }
        }
        zin.closeEntry();
    }
}
