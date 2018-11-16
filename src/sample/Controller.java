package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;


public class Controller{

    @FXML
    private TextField searchText;

    @FXML
    private TextField extension;

    @FXML
    private TreeView<String> tree;

    @FXML
    private TextArea textArea;

    @FXML
    private Button searchButton;

    private String path;

    public void fileSelection(ActionEvent actionEvent) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        path = directoryChooser.showDialog(Main.stage).getAbsolutePath();
        searchButton.setDisable(false);
    }


    public void scrollUP(ActionEvent actionEvent) {
        textArea.setScrollTop(textArea.getScrollTop() - 40);
    }


    public void scrollDOWN(ActionEvent actionEvent) {
        textArea.setScrollTop(textArea.getScrollTop() + 40);
    }


    public void selectTextArea(ActionEvent actionEvent) {
        textArea.selectAll();
    }


    public void searchAction(ActionEvent actionEvent) {
        NewThread thread = new NewThread(searchText, extension, tree, path, searchButton);
        thread.start();
    }


    public void treeAction(MouseEvent mouseEvent) {
        textArea.setText(NewThread.readFile(path + NewThread.getTreePath(tree)));
    }
}
