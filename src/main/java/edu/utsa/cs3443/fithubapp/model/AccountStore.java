package edu.utsa.cs3443.fithubapp.model;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.security.*;
import java.util.HexFormat;
import java.util.Properties;

public final class AccountStore {
    private static final Path STORE = Path.of(System.getProperty("user.home"), ".fithub", "account.properties");
    private AccountStore() {}

    public static boolean accountExists() {
        return Files.exists(STORE);
    }

    public static void createAccount(String username, String password) throws IOException {
        Properties p = new Properties();
        p.setProperty("username", username.trim());
        p.setProperty("passwordHash", hash(password));
        save(p);
    }

    public static boolean authenticate(String username, String password) throws IOException {
        Properties p = load();
        return username.trim().equals(p.getProperty("username", ""))
                && hash(password).equals(p.getProperty("passwordHash", ""));
    }

    public static boolean changePassword(String username, String password) throws IOException {
        Properties p = load();
        if (!username.trim().equals(p.getProperty("username", ""))) return false;
        p.setProperty("passwordHash", hash(password));
        save(p);
        return true;
    }

    private static Properties load() throws IOException {
        Properties p = new Properties();
        if (Files.exists(STORE)) {
            try (InputStream in = Files.newInputStream(STORE)) {
                p.load(in);
            }
        }
        return p;
    }

    private static void save(Properties p) throws IOException {
        Files.createDirectories(STORE.getParent());
        try (OutputStream out = Files.newOutputStream(STORE)) {
            p.store(out, "FitHub local prototype account");
        }
    }

    private static String hash(String value) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            return HexFormat.of().formatHex(md.digest(value.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }
}
