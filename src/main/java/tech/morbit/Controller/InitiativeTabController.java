package tech.morbit.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import tech.morbit.Character.Character;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class InitiativeTabController {

    @FXML
    private ListView<String> listInvolved;

    @FXML
    private TextField monsterName;

    @FXML
    private TextField monsterCount;

    @FXML
    public void initialize(){

    }

    public void listRotate(){
        List<String> involved = listInvolved.getItems();
        String f = involved.get(0);
        listInvolved.getItems().remove(f);
        listInvolved.getItems().add( f);
    }

    public void listAddOpenCharacters(){
        if(!listInvolved.getItems().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("List has content.");
            alert.setHeaderText(null);
            alert.setContentText("Want to clear the list before adding characters?");


            // Add custom buttons (OK and Cancel are included by default)
            ButtonType buttonYes = new ButtonType("Yes");
            ButtonType buttonNo = new ButtonType("No");
            alert.getButtonTypes().setAll(buttonYes, buttonNo);

            // Show the alert and wait for a response
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == buttonYes) {
                System.out.println("Cleaning out the closet...");
                listInvolved.getItems().clear();
            }
        }

        ArrayList<Character> characters = MainController.getCharacters();
        for(Character character : characters){
            listInvolved.getItems().add(character.getName());
        };
    }

    public void listAddMonsters(){
        try{
            String c = monsterCount.getText();
            int count;


            if(!c.isEmpty()){
                count = Integer.parseInt(c);
            } else{
                count = 0;
            }

            String name = monsterName.getText();

            if(name != null && !name.isEmpty()){
                if(count == 0){
                    listInvolved.getItems().add(name);
                }else {
                    for (int i = 1; i < count+1; i++) {
                        String output = String.format("%s #%d", name, i);
                        listInvolved.getItems().add(output);
                    }
                }

            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("No valid String entered.");
                alert.setContentText("Please enter a valid string");
                alert.show();
            }


        } catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Not Integer count entered.");
            alert.setContentText("Please enter a valid integer");
            alert.show();
        }
    }

    public void listMoveEntryUp(){listMoveEntry(false);}
    public void listMoveEntryDown(){listMoveEntry(true);}

    public void listMoveEntry(boolean down){

        int index = listInvolved.getSelectionModel().getSelectedIndex();
        String selectedString = listInvolved.getItems().get(index);


        try {
            int i = down ? index + 2 : index - 1;
            listInvolved.getItems().add(i, selectedString);
            listInvolved.getItems().remove(down ? index : index + 1);


        } catch (IndexOutOfBoundsException e) {

            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Reached Edge");
            a.setHeaderText(null);
            a.setContentText("You read an edge, you can not go further in this direction.");
            a.show();
        }


    }

    public void listRemoveEntry(){
        String selected = listInvolved.getSelectionModel().getSelectedItem();

        if(selected != null) {

            listInvolved.getItems().remove(selected);
        }
        else{
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Nothing Selected");
            a.setHeaderText(null);
            a.setContentText("Nothing Selected");
            a.show();
        }
    }
}
