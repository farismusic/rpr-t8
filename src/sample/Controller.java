package sample;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public Button traziBtn;
    public Button prekiniBtn;
    public TextField searchBox;
    public ListView list;
    private ObservableList fileList = FXCollections.observableArrayList();
    private Pretraga pretraga;

    public void onTrazi(ActionEvent actionEvent) {
        pretraga = new Pretraga(this);
        fileList.clear();

        Thread pretragaThread = new Thread(pretraga);
        pretragaThread.start();
        prekidacZaPretrazivanjeBtn(true);
    }

    public void onPrekini(ActionEvent actionEvent) {
        System.out.println("Prekid!");
        pretraga.stop();
        prekidacZaPretrazivanjeBtn(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        list.setItems(fileList);
        list.getItems().addListener((ListChangeListener)(promjena) -> list.scrollTo(list.getItems().size()-1));
    }

    public ObservableList<File> getFileList() {
        return fileList;
    }

    public void prekidacZaPretrazivanjeBtn(boolean vrijednost) {
        traziBtn.setDisable(vrijednost);
        prekiniBtn.setDisable(!vrijednost);
    }
}
