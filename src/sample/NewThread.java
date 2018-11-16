package sample;


import javafx.scene.control.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class NewThread extends Thread {

    public TextField searchText;
    public TextField extension;
    public TreeView<String> tree;
    public String path;
    public Button searchButton;

    NewThread(TextField searchText, TextField extension, TreeView<String> tree, String path, Button searchButton)
    {
        this.searchText = searchText;
        this.extension = extension;
        this.tree = tree;
        this.path = path;
        this.searchButton = searchButton;
    }

    @Override
    public void run()
    {
        searchButton.setDisable(true);
        tree.setRoot(null);
        TreeItem<String> root = new TreeItem(new File(path).getName());
        tree.setRoot(root);

        getDirs(path, searchText.getText(), extension.getText(), root);
        searchButton.setDisable(false);
    }

    public static String getTreePath(TreeView<String> tree)
    {
        StringBuilder pathBuilder = new StringBuilder();
        for (TreeItem<String> item = tree.getSelectionModel().getSelectedItem();
             item.getParent() != null; item = item.getParent()) {

            pathBuilder.insert(0, item.getValue());
            pathBuilder.insert(0, "/");
        }
        return pathBuilder.toString();
    }



    public static void getDirs(String sDir, String text, String ext, TreeItem<String> v){
        File[] faFiles = new File(sDir).listFiles();
        for(File file: faFiles){
            if(file.isDirectory()){
                v.getChildren().add(new TreeItem<String>(file.getName()));
                int last = v.getChildren().size() - 1;
                getDirs(file.getAbsolutePath(), text, ext, v.getChildren().get(last));
                if(v.getChildren().get(last).getChildren().size() == 0)
                    v.getChildren().remove(last);
            }
            else {
                String name = file.getName();
                if (name.endsWith(ext)) {
                    if(readFile(file.getAbsolutePath()).indexOf(text) != -1)
                        v.getChildren().add(new TreeItem<String>(name));
                }
            }
        }
    }



    public static String readFile(String sDir)
    {
        try (FileInputStream fin = new FileInputStream(sDir)) {
            ByteArrayOutputStream result = new ByteArrayOutputStream();
            byte[] buffer = new byte[fin.available()];
            int length;
            while ((length = fin.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            return result.toString("UTF-8");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        return "";
    }
}
