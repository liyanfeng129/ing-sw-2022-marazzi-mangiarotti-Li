package it.polimi.ingsw.GUI;

import it.polimi.ingsw.characterCards2.CharacterCard;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;

public class CharacterController extends GameBoardController {



    public void showCharacter(CharacterCard card, ArrayList<Node> Nodes, AnchorPane root, Boolean action){
        int n_card = card.getN_card();
        switch (n_card) {
            case 1:
                showCard1(card, Nodes, root,action);break;
            case 3:
                showCard3(card, Nodes, root,action);break;
            case 5:
                showCard5(card, Nodes, root,action);break;
            case 7:
                showCard7(card, Nodes, root,action);break;
            case 9:
                showCard9(card, Nodes, root,action);break;
            case 10:
                showCard10(card, Nodes, root,action);break;
            case 11:
                showCard11(card, Nodes, root,action);break;
            default:
                showCard12(card, Nodes, root,action);break;
        }

    }


    public void showCard1(CharacterCard card, ArrayList<Node> Nodes, AnchorPane root, Boolean action){

    }

    public void showCard3(CharacterCard card, ArrayList<Node> Nodes, AnchorPane root, Boolean action){

    }
    public void showCard5(CharacterCard card, ArrayList<Node> Nodes, AnchorPane root, Boolean action){

    }
    public void showCard7(CharacterCard card, ArrayList<Node> Nodes, AnchorPane root, Boolean action){

    }
    public void showCard9(CharacterCard card, ArrayList<Node> Nodes, AnchorPane root, Boolean action){

    }
    public void showCard10(CharacterCard card, ArrayList<Node> Nodes, AnchorPane root, Boolean action){

    }
    public void showCard11(CharacterCard card, ArrayList<Node> Nodes, AnchorPane root, Boolean action){

    }
    public void showCard12(CharacterCard card, ArrayList<Node> Nodes, AnchorPane root, Boolean action){

    }

}
