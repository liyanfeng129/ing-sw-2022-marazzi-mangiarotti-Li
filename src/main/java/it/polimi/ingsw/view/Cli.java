package it.polimi.ingsw.view;


import it.polimi.ingsw.characterCards2.*;
import it.polimi.ingsw.model.*;

import java.util.ArrayList;

public class Cli {
    private boolean on_bool ;

    public Cli(){
        this.on_bool = true;
    }

    /**
     * this method print on CLI principal information about game and call other methods to print on cli
     * @param game
     * @throws EriantysExceptions
     */
    public void show_game(Game game) throws EriantysExceptions {
        System.out.println("GAME INFO");
        System.out.println("Number of players: "+ game.getN_Player());
        if(game.isExpertMode())
            System.out.println("game mode: expert");
        else
            System.out.println("game mode: normal");

        System.out.println("------------------------------------------------");

        show_table(game.getTable());

        System.out.println("------------------------------------------------");

        System.out.println("------------------------------------------------");

        for(int i=0; i<game.getN_Player();i++){
            show_player(game.getPlayers().get(i),game);
            System.out.println("------------------------------------------------");
        }
    }

    /**
     * print hand of player
     * @param hand the hand of the player
     */
    public void show_hand(Hand hand){

        for (int i =0; i< hand.getN_cards();i++){
            System.out.println("Card_"+i);
            show_assistant(hand.getList_cards().get(i));
        }

    }

    /**
     * print all assistant with their values and steps
     * @param assistants
     */
    public void show_Assistants(ArrayList<Assistant> assistants){
        int i = 1;
        for (Assistant a : assistants){
            System.out.println("Card_" + i++);
            show_assistant(a);
        }

    }

    /**
     * print all info about table
     * @param table table of this game
     */
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

    /**
     * print students on island
     * @param island island to show
     * @return a msg
     */
    public String show_students_island(Island island){
        String msg = Color.RED + String.valueOf(island.getStudents()[0]) + " " + Color.YELLOW + String.valueOf(island.getStudents()[1]) + " " +
                Color.PURPLE + String.valueOf(island.getStudents()[2]) + " " + Color.BLUE+ String.valueOf(island.getStudents()[3]) + " " +
                Color.GREEN + String.valueOf(island.getStudents()[4]) + Color.RESET;
        return msg;
    }

    /**
     * print students on cloud
     * @param cloud cloud to show
     * @return a msg
     */
    public String show_students_cloud(Cloud cloud){
        String msg = Color.RED + String.valueOf(cloud.getStudents()[0]) + " " + Color.YELLOW + String.valueOf(cloud.getStudents()[1]) + " " +
                Color.PURPLE + String.valueOf(cloud.getStudents()[2]) + " " + Color.BLUE+ String.valueOf(cloud.getStudents()[3]) + " " +
                Color.GREEN + String.valueOf(cloud.getStudents()[4]) + Color.RESET;
        return msg;
    }

    /**
     * print students on waiting room
     * @param pb playerboard to show
     * @return a msg
     */
    public String show_students_waiting_room(PlayerBoard pb){

        String msg = Color.RED + String.valueOf(pb.getWaitingRoom()[0]) + " " + Color.YELLOW + String.valueOf(pb.getWaitingRoom()[1]) + " " +
                Color.PURPLE + String.valueOf(pb.getWaitingRoom()[2]) + " " + Color.BLUE+ String.valueOf(pb.getWaitingRoom()[3]) + " " +
                Color.GREEN + String.valueOf(pb.getWaitingRoom()[4]) + Color.RESET;
        return msg;
    }

    /**
     * print students on dining room
     * @param pb playerboard to show
     * @return a msg
     */
    public String show_students_dining_room(PlayerBoard pb){
        String msg = Color.RED + String.valueOf(pb.getDiningRoom()[0]) + " " + Color.YELLOW + String.valueOf(pb.getDiningRoom()[1]) + " " +
                Color.PURPLE + String.valueOf(pb.getDiningRoom()[2]) + " " + Color.BLUE+ String.valueOf(pb.getDiningRoom()[3]) + " " +
                Color.GREEN + String.valueOf(pb.getDiningRoom()[4]) + Color.RESET;
        return msg;
    }

    /**
     * print student on assistant card
     * @param array_student
     * @return a msg
     */
    public String show_students(int[] array_student){
        String msg = Color.RED + String.valueOf(array_student[0]) + " " + Color.YELLOW + String.valueOf(array_student[1]) + " " +
                Color.PURPLE + String.valueOf(array_student[2]) + " " + Color.BLUE+ String.valueOf(array_student[3]) + " " +
                Color.GREEN + String.valueOf(array_student[4]) + Color.RESET;
        return msg;
    }

    /**
     * print all islands
     * @param table table with island to print
     */
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
                System.out.print("Tower:"+table.getIslands().get(i).getTowerColor() +"|");
            else
                System.out.print("Tower:"+table.getIslands().get(i).getTowerColor() +"  |");

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
                System.out.print("Tower:"+table.getIslands().get(i).getTowerColor() +"|");
            else
                System.out.print("Tower:"+table.getIslands().get(i).getTowerColor() +"  |");
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

    /**
     * print all cloud
     * @param table table with cloude to print
     */
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

    /**
     * show a character
     * @param character
     */
    public void show_character(CharacterCard character){
        System.out.println(character.toString());
    }

    /**
     * print a player
     * @param player player to print
     * @param game game with this player
     */
    public void show_player(Player player,Game game){
        System.out.println("Player Name: "+ player.getName());
        System.out.println("Player Mage: "+ player.getMage());
        System.out.println("Tower Color : "+ player.getTowerColor());
        if(player.getWallet()!=null)
            System.out.println("Wallet: "+ player.getWallet().getSaving());
        show_playerboard(player.getPb(),game,player);


    }

    /**
     * print the complete playerboard
     * @param playerBoard pb to print
     * @param game game with this player
     * @param player player whit this playerboard
     */
    public void show_playerboard(PlayerBoard playerBoard,Game game,Player player){
        System.out.println("WAITING ROOM");
        System.out.println(show_students_waiting_room(playerBoard));
        System.out.println("DINING ROOM");
        System.out.println(show_students_dining_room(playerBoard));
        System.out.println("TOWER: "+ playerBoard.getN_tower());
        show_professor(game,player);


    }

    /**
     * print one assistant card
     * @param card
     */
    public void show_assistant(Assistant card){
        System.out.println("Steps: " + card.getType().getSteps() + " " + "Value: " + card.getType().getNum());
    }

    /**
     * print which player control every professor
     * @param game
     * @param player
     */
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

    /**
     * print all character with their effect and cost
     * @param num num of character
     * @param info string with effect of the card
     */
    public void show_character(int num, ArrayList<Object> info){
        System.out.println("EFFECT: ");
        System.out.println((String)info.get(0));
        System.out.println("COST: ");
        if ((Boolean) info.get(2))
            System.out.println((int)info.get(1)+1);
        else
            System.out.println((int)info.get(1));
        if(num ==1 || num ==7 || num ==11) {
            System.out.println("STUDENTS: ");
            System.out.println(show_students((int[]) info.get(3)));
        }
        if(num == 5){
            System.out.println("NO ENTRY TILES LEFT: ");
            System.out.println((int) info.get(3));
        }
    }




}
