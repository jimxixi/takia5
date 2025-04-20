package takia5.utils;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

import takia5.playerClient.Main;

/**
 * 目前暂时采用全局静态方法的思路，每个方法都自己创建和销毁所有资源。 因为我也不知道这些资源将在什么时候用到，又会用到多少。
 */
public class GameSaveUtil {
    /**
     * 初始化存档，更新元信息
     * 
     * @param saveName
     * @return success
     */
    public static boolean initGameSave(String saveName) {
        String url = "jdbc:sqlite:" + Main.SavePath + saveName + ".sqlite";
        String 更新元数据 = "update '存档信息saveInfo' set value = ? where key = ?;";
        try (Connection conn = DriverManager.getConnection(url);
                PreparedStatement stmt = conn.prepareStatement(更新元数据);) {
            conn.setAutoCommit(false);
            stmt.setString(1, Long.toString(System.currentTimeMillis()));
            stmt.setString(2, "创建时间createtime");
            stmt.addBatch();
            stmt.setString(1, Long.toString(System.currentTimeMillis()));
            stmt.setString(2, "更新时间updatetime");
            stmt.addBatch();
            stmt.setString(1, "0");
            stmt.setString(2, "游玩时间playhours");
            stmt.addBatch();
            stmt.setString(1, "this story is a machine");
            stmt.setString(2, "存档备注note");
            stmt.addBatch();
            stmt.setString(1, "days past / total days");
            stmt.setString(2, "游戏进度gameprogress");
            stmt.addBatch();
            int[] rs = stmt.executeBatch();
            System.out.println(Arrays.toString(rs));
            conn.commit();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * 获取一个游戏存档的元数据
     * 
     * @param file
     * @return
     */
    public static HashMap<String, String> scanGameSave(File file) {
        HashMap<String, String> map = new HashMap<String, String>();
        // 找到最后一个点的位置
        int dotIndex = file.getName().lastIndexOf('.');
        map.put("name", file.getName().substring(0, dotIndex));
        String url = "jdbc:sqlite:" + file.getAbsolutePath();
        String sql = "select * from '存档信息saveInfo';";

        try (Connection connection = DriverManager.getConnection(url);
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String key = rs.getString("key");
                String value = rs.getString("value");
                map.put(key, value);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return map;
    }

    /**
     * 扫描所有存档的元数据
     * 
     * @return
     */
    public static LinkedList<HashMap<String, String>> scanGameSaves() {
        LinkedList<HashMap<String, String>> mapList = new LinkedList<HashMap<String, String>>();
        File dir = new File(Main.SavePath);
        if (dir.exists() && dir.isDirectory()) {
            // 创建一个文件过滤器，过滤出所有以.sqlite结尾的文件
            FilenameFilter filter = new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.endsWith(".sqlite");
                }
            };
            // 获取目录下所有符合条件的文件
            File[] sqliteFiles = dir.listFiles(filter);
            for (File file : sqliteFiles) {
                mapList.add(scanGameSave(file));
            }
        }
        return mapList;
    }

    /**
     * 删除存档
     * 
     * @param fileName
     * @return
     */
    public static boolean deletaGameSave(String fileName) {
        boolean result = false;
        // 删除文件
        File file = new File(Main.SavePath + fileName + ".sqlite");
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                System.out.println("文件 " + fileName + " 删除成功。");
            } else {
                System.out.println("文件 " + fileName + " 删除失败。");
            }
        } else {
            System.out.println("文件 " + fileName + " 不存在或不是文件。");
        }

        // 删除同名文件夹
        File folder = new File(Main.SavePath + fileName);
        if (folder.exists() && folder.isDirectory()) {
            try {
                deleteDirectoryRecursively(folder.toPath());
                System.out.println("文件夹 " + fileName + " 删除成功。");
            } catch (IOException e) {
                System.out.println("文件夹 " + fileName + " 删除失败: " + e.getMessage());
            }
        } else {
            System.out.println("文件夹 " + fileName + " 不存在或不是文件夹。");
        }
        return result;
    }

    public static void deleteDirectoryRecursively(Path path) throws IOException {
        if (Files.isDirectory(path)) {
            Files.list(path).forEach(p -> {
                try {
                    deleteDirectoryRecursively(p);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        Files.delete(path);
    }

}
