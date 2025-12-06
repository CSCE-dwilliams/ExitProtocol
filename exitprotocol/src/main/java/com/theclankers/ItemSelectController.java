package com.theclankers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.model.EscapeManager;
import com.model.Item;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class ItemSelectController implements Initializable {

    @FXML
    Button item1;
    @FXML
    Button item2;
    @FXML
    Button item3;

    private EscapeManager manager;

    private Button[] items;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        manager = EscapeManager.getInstance();

        items = new Button[] { item1, item2, item3 };
        displayAvailableItems();
    }

    private void displayAvailableItems() {
        // Only display earned items (persistent across questions)
        ArrayList<Item> earnedItems = manager.getEarnedItems();

        // Show buttons for available items
        for (int i = 0; i < items.length; i++) {
            if (i < earnedItems.size()) {
                Item item = earnedItems.get(i);
                items[i].setText(item.getName());
                items[i].setVisible(true);
            } else {
                items[i].setVisible(false);
            }
        }
    }

    public void item1Btn(MouseEvent event) throws IOException {

    }

    public void item2Btn(MouseEvent event) throws IOException {

    }

    public void item3Btn(MouseEvent event) throws IOException {
    }

    public void backToGameBtn(MouseEvent event) throws IOException {
        App.setRoot("questiontemplate");
    }
}