package org.anelda.wizston.countdowntimer.model.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.anelda.wizston.countdowntimer.database.DatabaseConnection;
import org.anelda.wizston.countdowntimer.model.Preset;

import java.sql.*;
import java.util.Optional;

public class PresetDao {

    private static final ObservableList<Preset> presets;

    private static final Connection connection = new DatabaseConnection().getDatabaseConnection();

    public PresetDao() {
    }

    public static void updatePresetList() throws SQLException {
        String presetQuery = "SELECT id, title, hour, minute, sec, autoStart from presets";

        Statement statement = connection.createStatement();

        try {
            ResultSet presets = statement.executeQuery(presetQuery);
            PresetDao.presets.clear();

            while (presets.next()) {
                int id = presets.getInt("id");
                String title = presets.getString("title");
                int hour = presets.getInt("hour");
                int minute = presets.getInt("minute");
                int sec = presets.getInt("sec");
                boolean autoStart = presets.getBoolean("autoStart");
                PresetDao.presets.add(new Preset(id, title, hour, minute, sec, autoStart));
            }
        } catch (SQLException sqlException) {

            System.out.println(sqlException.getMessage());
            if (sqlException.getMessage().equals("Missing database. Creating...")) {
                String newTableQuery = "create table presets (id INTEGER PRIMARY KEY AUTOINCREMENT, title text, hour int, minute int, sec int default 00, autoStart  TEXT, created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP);";
                statement.execute(newTableQuery);
                updatePresetList();
            } else
                throw new SQLException(sqlException);
        }
    }

    public static void createPreset(String title, String hour, String minute, String sec) throws SQLException {

        connection.setAutoCommit(false);
        String createPresetQuery = "INSERT INTO presets (title, hour, minute, sec) " +
                "VALUES ('" + title + "', '" + hour + "', '" + minute + "', '" + sec + "' );";

        Statement statement = connection.createStatement();
        statement.executeUpdate(createPresetQuery);

        statement.close();
        connection.commit();

    }

    public static void updatePreset(int id, String title, String hour, String minute, String sec) throws SQLException {

        connection.setAutoCommit(false);
        String createPresetQuery = "UPDATE presets SET title = '" + title + "', hour = '" + hour + "', minute = '" + minute + "', sec = '" + sec + "' where id = '" + id + "';";

        Statement statement = connection.createStatement();
        statement.executeUpdate(createPresetQuery);

        statement.close();
        connection.commit();

    }

    public static void deletePreset(int id) throws SQLException {

        connection.setAutoCommit(false);
        String deletePresetQuery = "DELETE FROM presets WHERE id = '" + id + "';";

        Statement statement = connection.createStatement();
        statement.executeUpdate(deletePresetQuery);
        statement.close();
        connection.commit();
    }

    public static Optional<Preset> getPresetById(int id) throws SQLException {
        for (Preset preset : presets) {
            if (preset.id == id) return Optional.of(preset);
        }
        return Optional.empty();
    }

    static {
        presets = FXCollections.observableArrayList();
        try {
            updatePresetList();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ObservableList<Preset> getPresets() {
        return presets;
    }

}
