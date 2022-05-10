package it.polimi.ingsw.view;


import it.polimi.ingsw.characterCards.CharacterCard;
import it.polimi.ingsw.model.*;

public class Cli {
    private boolean on_bool ;

    public Cli(){
        this.on_bool = true;
    }

    public void show_game(Game game){
        System.out.println("GAME INFO");
        System.out.println("Number of players: "+ game.getN_Player());
        if(game.isExpertMode())
            System.out.println("game mode: expert");
        else
            System.out.println("game mode: normal");

        System.out.println("------------------------------------------------");

        show_table(game.getTable());

        System.out.println("------------------------------------------------");

        for(int i=0; i<game.getN_Player();i++){
            show_player(game.getPlayers().get(i));
        }
    }

    public void show_hand(Hand hand){

        for (int i =0; i< hand.getN_cards();i++){
            System.out.println("Card_"+i);
            show_assistant(hand.getList_cards().get(i));
        }

    }

    public void show_table(Table table){
        System.out.println("TABLE INFO");
        System.out.println(" ");
        System.out.println("ISLANDS");
        for(int i=0; i<table.getIslands().size();i++) {
            System.out.println("ISLAND_" + i);
            show_island(table.getIslands(i));
            System.out.println(" ");
        }
        System.out.println(" ");
        System.out.println("CLOUDS");
        for(int i=0; i<table.getClouds().size();i++) {
            System.out.println("CLOUD_" + i);
            show_cloud(table.getClouds().get(i));
            System.out.println(" ");
        }
        if(table.getCharacters()!=null)
        {
            System.out.println(" ");
            System.out.println("CHARACTERS");
            for(int i=0; i<table.getCharacters().size();i++) {
                show_character(table.getCharacters().get(i));
                System.out.println(" ");
            }
        }
    }
    public void show_island(Island island){
        if (island.getMotherNature())
            System.out.println("Mother Nature");

        System.out.println("Size:" + island.getSize());
        System.out.println("RED: "+ island.getStudents()[0] + " " + "YELLOW: "+ island.getStudents()[1] + " " +
                           "PINK: "+ island.getStudents()[2] + " " + "BLUE: "+ island.getStudents()[3] + " " +
                           "GREEN: "+ island.getStudents()[4]);
        if (island.getTower() == null)
            System.out.println("NO TOWER " + island.getSize());
        else{
            System.out.println(island.getTower()+ "TOWER");
        }

    }

    public void show_cloud(Cloud cloud){
        System.out.println("RED: "+ cloud.getStudents()[0] + " " + "YELLOW: "+ cloud.getStudents()[1] + " " +
                            "PINK: "+ cloud.getStudents()[2] + " " + "BLUE: "+ cloud.getStudents()[3] + " " +
                            "GREEN: "+ cloud.getStudents()[4]);

    }
    public void show_character(CharacterCard character){
        System.out.println("qui ci saranno i characters");
    }

    public void show_player(Player player){
        System.out.println("Player Name: "+ player.getName());
        System.out.println("Player Mage: "+ player.getMage());
        System.out.println("Tower Color : "+ player.getTowerColor());
        if(player.getWallet()!=null)
            System.out.println("Wallet: "+ player.getWallet().getWallet());
        show_playerboard(player.getPlayerBoard());


    }



    public void show_playerboard(PlayerBoard playerBoard){
        System.out.println("WAITING ROOM");
        System.out.println("RED: "+ playerBoard.getWaitingRoom()[0] + " " + "YELLOW: "+ playerBoard.getWaitingRoom()[1] + " " +
                "PINK: "+ playerBoard.getWaitingRoom()[2] + " " + "BLUE: "+ playerBoard.getWaitingRoom()[3] + " " +
                "GREEN: "+ playerBoard.getWaitingRoom()[4]);
        System.out.println("DINING ROOM");
        System.out.println("RED: "+ playerBoard.getDiningRoom()[0] + " " + "YELLOW: "+ playerBoard.getDiningRoom()[1] + " " +
                "PINK: "+ playerBoard.getDiningRoom()[2] + " " + "BLUE: "+ playerBoard.getDiningRoom()[3] + " " +
                "GREEN: "+ playerBoard.getDiningRoom()[4]);
        System.out.println("TOWER: "+ playerBoard.getN_tower());

    }


    public void show_assistant(Assistant card){
        System.out.println("Steps: " + card.getAssistantType().getSteps() + "" + "Value: " + card.getAssistantType().getNum());
    }



}
