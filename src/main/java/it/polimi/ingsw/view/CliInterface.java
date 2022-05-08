package it.polimi.ingsw.view;

import it.polimi.ingsw.model.*;


import java.util.ArrayList;

import static it.polimi.ingsw.model.Config.*;

public class CliInterface {
    private Cli cli;
    private Game game;
    private Player player;


    public void setCli(Cli cli) {
        this.cli = cli;
    }

    public Cli getCli() {
        return cli;
    }

    public void read_msg (ArrayList<Object> msg_list) {
        // ci sono diversi messaggi in entrata
        Player curr_player = (Player) msg_list.get(1);
        String msg = (String) msg_list.get(0);
        if (curr_player == this.player) {
            selector(msg,msg_list);
        } else {
            System.out.println("Turn of player " +
                                curr_player);
        }
    }


        public void selector (String msg,ArrayList<Object> msg_list){

            switch (msg) {
                case UPDATE -> System.out.println("Parameter is A");
                case REQ_ASSISTANT -> System.out.println("Parameter is b");
                case ASSISTANT_ERROR -> System.out.println("Parameter is b");
                case START_TURN -> System.out.println("Parameter is b");
                case STUDENT_DESTINATION -> System.out.println("Parameter is b");
                case NOT_ENAUGH_MONEY -> System.out.println("Parameter is A");
                case REQ_PARAMS_CHARACTER -> System.out.println("Parameter is b");
                case MOVE_MN -> System.out.println("Parameter is b");
                case MN_POSITION_ERROR -> System.out.println("Parameter is b");
                case CHARACTER_STUDENT_MOVES -> System.out.println("Parameter is b");
                case CHARACTER_MN_MOVES -> System.out.println("Parameter is A");
                case CHARACTER_CLOUD_MOVES -> System.out.println("Parameter is b");
                case REAQ_CLOUD -> System.out.println("Parameter is b");
                case ERROR_CLOUD -> System.out.println("Parameter is b");
                case END_TURN -> System.out.println("Parameter is b");
                case END_GAME -> System.out.println("Parameter is b");



                default -> System.out.println("Parameter is unknown");
            }





        }
    }

