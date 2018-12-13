package sample;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import java.io.File;

public class Pretraga implements Runnable {
    private File dir;
    private boolean pretraga = false;
    private Controller controller;
    private String zaPretrazivanje;
    private ObservableList<File> lista;

    public Pretraga(Controller controller){
        dir = new File (System.getProperty("user.home"));
        this.controller = controller;
        lista = controller.getFileList();
        zaPretrazivanje = "" + controller.searchBox.getText().trim();
    }

    public void stop(){
        pretraga = false;
    }

    @Override
    public void run() {
        if(controller.searchBox.getText().trim().isEmpty()){
            System.out.print("Nista nije pronadeno!");
            Platform.runLater(() -> controller.prekidacZaPretrazivanjeBtn(false));
            return;
        }
        pretraga = true;
        try{
            poredi(dir);
        }catch (Exception greska){
            System.out.println(greska);
        }
        pretraga = false;
        Platform.runLater(()->controller.prekidacZaPretrazivanjeBtn(false));

        System.out.println("Pretraga zavrsena!");
    }

    private void poredi(File root) throws Exception {
        if (!pretraga){
            throw new Exception("Prekinuta pretraga!");
        }

        if (root.isFile()){
            Platform.runLater(()->{
                if(root.getName().contains(zaPretrazivanje.trim())){
                    if (lista != null)
                        lista.add(root);
                }
            });
        }
        else {
            for (File f : root.listFiles()){
                poredi(f);
            }
        }
    }
}