package it.polimi.ingsw.view;


import it.polimi.ingsw.characterCards.CharacterCard;
import it.polimi.ingsw.model.*;

import java.util.ArrayList;

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
            show_player(game.getPlayers().get(i),game);
            System.out.println("------------------------------------------------");
        }
    }

    public void show_hand(Hand hand){

        for (int i =0; i< hand.getN_cards();i++){
            System.out.println("Card_"+i);
            show_assistant(hand.getList_cards().get(i));
        }

    }

    public void show_Assistants(ArrayList<Assistant> assistants){
        int i = 1;
        for (Assistant a : assistants){
            System.out.println("Card_" + i++);
            show_assistant(a);
        }

    }

    public void show_table(Table table){
        System.out.println("TABLE INFO");
        System.out.println(" ");
        System.out.println("ISLANDS");
        show_islands(table);

        System.out.println("CLOUDS");

        show_clouds(table);


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


    public String show_students_island(Island island){
        String msg = Color.RED + String.valueOf(island.getStudents()[0]) + " " + Color.YELLOW + String.valueOf(island.getStudents()[1]) + " " +
                Color.PURPLE + String.valueOf(island.getStudents()[2]) + " " + Color.BLUE+ String.valueOf(island.getStudents()[3]) + " " +
                Color.GREEN + String.valueOf(island.getStudents()[4]) + Color.RESET;
        return msg;
    }
    public String show_students_cloud(Cloud cloud){
        String msg = Color.RED + String.valueOf(cloud.getStudents()[0]) + " " + Color.YELLOW + String.valueOf(cloud.getStudents()[1]) + " " +
                Color.PURPLE + String.valueOf(cloud.getStudents()[2]) + " " + Color.BLUE+ String.valueOf(cloud.getStudents()[3]) + " " +
                Color.GREEN + String.valueOf(cloud.getStudents()[4]) + Color.RESET;
        return msg;
    }
    public String show_students_waiting_room(PlayerBoard pb){

        String msg = Color.RED + String.valueOf(pb.getWaitingRoom()[0]) + " " + Color.YELLOW + String.valueOf(pb.getWaitingRoom()[1]) + " " +
                Color.PURPLE + String.valueOf(pb.getWaitingRoom()[2]) + " " + Color.BLUE+ String.valueOf(pb.getWaitingRoom()[3]) + " " +
                Color.GREEN + String.valueOf(pb.getWaitingRoom()[4]) + Color.RESET;
        return msg;
    }
    public String show_students_dining_room(PlayerBoard pb){
        String msg = Color.RED + String.valueOf(pb.getDiningRoom()[0]) + " " + Color.YELLOW + String.valueOf(pb.getDiningRoom()[1]) + " " +
                Color.PURPLE + String.valueOf(pb.getDiningRoom()[2]) + " " + Color.BLUE+ String.valueOf(pb.getDiningRoom()[3]) + " " +
                Color.GREEN + String.valueOf(pb.getDiningRoom()[4]) + Color.RESET;
        return msg;
    }


    public void show_islands(Table table){
        for(int i=0; i<table.getIslands().size()/2;i++){
            System.out.print("ISLAND_" + (i+1) + "  |  ");
        }
        System.out.println(" ");
        for(int i=0; i<table.getIslands().size()/2;i++){
            System.out.print("SIZE: " + table.getIslands().get(i).getSize() + "   |  ");
        }
        System.out.println(" ");
        for(int i=0; i<table.getIslands().size()/2;i++){
            if(i==0)
                System.out.print(show_students_island(table.getIslands().get(i)) + " | ");
            else
                System.out.print(show_students_island(table.getIslands().get(i)) + "  | ");

        }
        System.out.println(" ");
        for(int i=0; i<table.getIslands().size()/2;i++) {
            if (i==0)
                System.out.print("Tower:"+table.getIslands().get(i).getTower() +"|");
            else
                System.out.print("Tower:"+table.getIslands().get(i).getTower() +"  |");

        }
        System.out.println(" ");


        for(int i=0; i<table.getIslands().size()/2;i++) {
            if(table.getIslands().get(i).getMotherNature()==true){
                System.out.print("    🗿    | ");
            }
            else{
                System.out.print("            |");
            }
        }
        System.out.println(" ");
        System.out.println("--------------------------------------------------------------------------");

        for(int i=table.getIslands().size()/2; i<table.getIslands().size();i++){
            System.out.print("ISLAND_" + (i+1) + "  |  ");
        }
        System.out.println(" ");
        for(int i=table.getIslands().size()/2; i<table.getIslands().size();i++){
            if (i<9)
                System.out.print("SIZE: " + table.getIslands().get(i).getSize() + "   |  ");
            else
                System.out.print("SIZE: " + table.getIslands().get(i).getSize() + "    |  ");
        }
        System.out.println(" ");
        for(int i=table.getIslands().size()/2; i<table.getIslands().size();i++) {
            if (i==table.getIslands().size()/2)
                System.out.print("Tower:"+table.getIslands().get(i).getTower() +"|");
            else
                System.out.print("Tower:"+table.getIslands().get(i).getTower() +"  |");
        }
        System.out.println(" ");
        for(int i=table.getIslands().size()/2; i<table.getIslands().size();i++){
            if(i==0)
                System.out.print(show_students_island(table.getIslands().get(i)) +  " | ");
            else
                System.out.print(show_students_island(table.getIslands().get(i)) + "  | ");

        }
        System.out.println("");
        for(int i=table.getIslands().size()/2; i<table.getIslands().size();i++) {
            if(table.getIslands().get(i).getMotherNature()==true){
                System.out.print("    🗿    | ");
            }
            else{
                if (i<10)
                    System.out.print("            |");
                else
                    System.out.print("             |");
            }
        }
        System.out.println(" ");

    }

    public void show_clouds(Table table){
        for(int i=0; i<table.getClouds().size();i++){
            System.out.print("CLOUD_" + i + "   |  ");
        }
        System.out.println(" ");
        for(int i=0; i<table.getClouds().size();i++){
            if(i==0)
                System.out.print(show_students_cloud(table.getClouds().get(i)) + " | ");
            else
                System.out.print(show_students_cloud(table.getClouds().get(i)) + "  | ");

        }
        System.out.println(" ");

    }
    public void show_character(CharacterCard character){
        System.out.println("qui ci saranno i characters");
    }

    public void show_player(Player player,Game game){
        System.out.println("Player Name: "+ player.getName());
        System.out.println("Player Mage: "+ player.getMage());
        System.out.println("Tower Color : "+ player.getTowerColor());
        if(player.getWallet()!=null)
            System.out.println("Wallet: "+ player.getWallet().getWallet());
        show_playerboard(player.getPlayerBoard(),game,player);


    }


    public void show_playerboard(PlayerBoard playerBoard,Game game,Player player){
        System.out.println("WAITING ROOM");
        System.out.println(show_students_waiting_room(playerBoard));
        System.out.println("DINING ROOM");
        System.out.println(show_students_dining_room(playerBoard));
        System.out.println("TOWER: "+ playerBoard.getN_tower());
        show_professor(game,player);


    }


    public void show_assistant(Assistant card){
        System.out.println("Steps: " + card.getType().getSteps() + " " + "Value: " + card.getType().getNum());
    }

    public void show_professor(Game game,Player player){
        System.out.println("Professor");
        Mage[] progessor = game.getProfessors().getList_professors();
        for(int i=0;i<5;i++){
            if (player.getMage()==progessor[i]){
                if(i==0)
                    System.out.print(Color.RED + " ▊ "+ Color.RESET+" ");
                if(i==1)
                    System.out.print(Color.YELLOW + " ▊ " + Color.RESET +" ");
                if(i==2)
                    System.out.print(Color.PURPLE + " ▊ " + Color.RESET+" ");
                if(i==3)
                    System.out.print(Color.BLUE + " ▊ " + Color.RESET+" ");
                if(i==4)
                    System.out.print(Color.GREEN + " ▊ "+ Color.RESET+" ");
            }
            else
                System.out.print("");
        }
        System.out.println(" ");
    }

}
