package util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.User;
import model.LaporanBarang;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JsonHelper {

    private static final Gson gson = new Gson();

    private static final String USER_FILE = "data/users.json";
    private static final String LAPORAN_FILE = "data/laporan.json";

    // ================= USER =================
    public static List<User> readUsers() {
        try (FileReader reader = new FileReader(USER_FILE)) {
            Type listType = new TypeToken<List<User>>() {
            }.getType();
            List<User> users = gson.fromJson(reader, listType);
            return users != null ? users : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    // ================= LAPORAN =================
    public static List<LaporanBarang> readLaporan() {
        try (FileReader reader = new FileReader(LAPORAN_FILE)) {
            Type listType = new TypeToken<List<LaporanBarang>>() {
            }.getType();
            List<LaporanBarang> laporan = gson.fromJson(reader, listType);
            return laporan != null ? laporan : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public static void writeLaporan(List<LaporanBarang> data) {
        try (FileWriter writer = new FileWriter(LAPORAN_FILE)) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
