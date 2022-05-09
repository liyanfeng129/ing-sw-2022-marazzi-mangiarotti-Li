package it.polimi.ingsw.view;

import it.polimi.ingsw.model.*;


import java.util.ArrayList;
import java.util.Scanner;

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

    public void read_msg(ArrayList<Object> msg_list) {
        // ci sono diversi messaggi in entrata
        // non va bene si deve aggio
        Player curr_player = (Player) msg_list.get(1);   // qui bisogna cambiare questo
        String msg = (String) msg_list.get(0);
        if (curr_player == this.player) {
            selector(msg, msg_list);
        } else {
            System.out.println("Turn of player " +
                    curr_player);
        }
    }


    public ArrayList<Object> selector(String msg, ArrayList<Object> respons_list) {
        ArrayList<Object> msg_list = new ArrayList<Object>();
        switch (msg) {
            case UPDATE:
                msg_list = this.update((Game) respons_list.get(1));
            case REQ_ASSISTANT:
                msg_list = this.req_assistant();
            case ASSISTANT_ERROR:
                msg_list = this.assistant_error((Game) respons_list.get(1));
            case START_TURN:
                System.out.println("SERVER: IT'S YOUR TURN !");
                msg_list = this.start_turn(game);
            case STUDENT_DESTINATION:
                msg_list = this.student_destination(game);
            case NOT_ENAUGH_MONEY:
                msg_list = this.not_enaugh_money(game);
            case REQ_PARAMS_CHARACTER:
                System.out.println("chiede parametri character");
            case MOVE_MN:
                msg_list = this.move_mn(game);
            case CHARACTER_STUDENT_MOVES:
                msg_list = this.start_turn(game);

            case CHARACTER_MN_MOVES:
                msg_list = this.character_mn_moves(game);
            case CHARACTER_CLOUD_MOVES:
                msg_list = this.character_cloud_moves(game);
            case REAQ_CLOUD:
                msg_list = this.req_cloud(game);
            case ERROR_CLOUD:
                msg_list = this.error_cloud(game);
            case END_TURN:
                msg_list = this.end_turn(game);
            case END_GAME:
                msg_list = this.end_game(game,(Player) respons_list.get(1));
            default:
                System.out.println("ERROR: Parameter is unknown");
        }
        return msg_list;
    }

    public ArrayList<Object> update(Game game) {
        ArrayList<Object> msg_list = new ArrayList<Object>();
        System.out.println("SERVER:  UPDATE_GAME");
        cli.show_game(game);
        cli.show_hand(player.getHand());
        return msg_list;
    }

    public ArrayList<Object> req_assistant() {
        ArrayList<Object> msg_list = new ArrayList<Object>();
        Scanner scanner = new Scanner(System.in);
        System.out.println("SERVER:  PLEASE ENTER THE NUMBER OF THE CHARACTER YOU WANT TO PLAY");
        cli.show_hand(player.getHand());
        int card = Integer.parseInt(scanner.nextLine());
        return msg_list;
    }

    public ArrayList<Object> assistant_error(Game game) {
        ArrayList<Object> msg_list = new ArrayList<Object>();
        this.update(game);
        Scanner scanner = new Scanner(System.in);
        System.out.println("SERVER:  THE CARD PREVIUSLY SELECT HAS ALREADY BEEN USED BY ANOTHER PLAYER");
        System.out.println("SERVER:  PLEASE ENTER THE NUMBER OF THE CHARACTER YOU WANT TO PLAY");
        cli.show_hand(player.getHand());
        int card = Integer.parseInt(scanner.nextLine());
        return msg_list;
    }

    // manca il caso in cui non ci sono abbastanza studenti

    public ArrayList<Object> start_turn(Game game) {
        ArrayList<Object> msg_list = new ArrayList<Object>();
        Scanner scanner = new Scanner(System.in);
        this.update(game);
        System.out.println("SERVER:  1 FOR MOVING STUDENT, 2 FOR PLAYING CHARACTER");
        int Move = Integer.parseInt(scanner.nextLine());

        int c = 0;
        int c_1 = 0;
        int c_2 = 0;

        while (c == 0) {
            if (Move == 1) {
                c = 1;
                System.out.println("SERVER:  SELECT COLOR");
                System.out.println("SERVER:  0 RED, 1 YELLOW, 2 PINK, 3 BLUE, 4 GREEN");
                int Colore = Integer.parseInt(scanner.nextLine());

                while (c_1 == 0) {

                    if (Colore == 0 || Colore == 1 || Colore == 2 || Colore == 3 || Colore == 4) {
                        c_1 = 1;
                        // va aggiunto lo studente

                    } else {
                        System.out.println("SERVER:  ILLIGAL INPUT");
                        System.out.println("SERVER:  0 RED, 1 YELLOW, 2 PINK, 3 BLUE, 4 GREEN");
                        Move = Integer.parseInt(scanner.nextLine());
                    }

                }

            } else if (Move == 2) {
                c = 1;
                int character;
                System.out.println("SERVER: SELECT CHARACTER WITH 0,1,2");
                character = Integer.parseInt(scanner.nextLine());
                while (c_2 == 0) {
                    if (character >= 0 || character <= 2) {
                        c_2 = 1;

                        //aggiungere messaggio qui
                    } else {
                        System.out.println("SERVER:  ILLIGAL INPUT");
                        System.out.println("SERVER: SELECT CHARACTER WITH 0,1,2");
                        character = Integer.parseInt(scanner.nextLine());
                    }

                }

            } else {
                System.out.println("SERVER:  ILLIGAL INPUT");
                System.out.println("SERVER:  1 FOR MOVING STUDENT, 2 FOR PLAYING CHARACTER");
                Move = Integer.parseInt(scanner.nextLine());
            }


        }
        return msg_list;
    }

    public ArrayList<Object> student_destination(Game game) {
        int c = 0;
        int c_1 = 0;
        ArrayList<Object> msg_list = new ArrayList<Object>();
        Scanner scanner = new Scanner(System.in);

        System.out.println("SERVER: SELECT DESTINATION, 0 ISLANDS, 1 HALL ");
        int destination = Integer.parseInt(scanner.nextLine());

        while (c == 0) {
            if (destination == 0) {
                c = 1;
                System.out.println("SERVER: SELECT ISLANDS WITH INT POSITION FROM 0 TO "
                        + game.getTable().getIslands().size());
                int island = Integer.parseInt(scanner.nextLine());
                while (c_1 == 0) {
                    if (island >= 0 && island < game.getTable().getIslands().size()) {
                        c_1 = 1;
                        // BISOGNA AGGIUNERE COME VIENE CARICATO IL MESSAGGIO
                    } else {
                        System.out.println("SERVER:  ILLIGAL INPUT");
                        System.out.println("SERVER: SELECT ISLANDS WITH INT POSITION FROM 0 TO "
                                + game.getTable().getIslands().size());
                        island = Integer.parseInt(scanner.nextLine());

                    }
                }
            } else if (destination == 1) {
                c = 1;
                // BISOGNA AGGIUNERE COME VIENE CARICATO IL MESSAGGIO
            } else {
                System.out.println("SERVER:  ILLIGAL INPUT");
                System.out.println("SERVER: SELECT DESTINATION, 0 ISLANDS, 1 HALL");
                destination = Integer.parseInt(scanner.nextLine());
            }
        }
        return msg_list;

    }


    public ArrayList<Object> not_enaugh_money(Game game) {
        ArrayList<Object> msg_list = new ArrayList<Object>();
        System.out.println("SERVER:  YOU DON'T HAVE ENOUGH MONEY TO USE THE CHARACTER CARD");
        // COSA MANDIAMO NELLA CARTA ?
        return msg_list;
    }

    public ArrayList<Object> move_mn(Game game) {
        ArrayList<Object> msg_list = new ArrayList<Object>();
        Scanner scanner = new Scanner(System.in);
        int c = 1;
        System.out.println("SERVER: SELECT ISLANDS WITH INT POSITION FROM 0 TO "
                + game.getTable().getIslands().size());
        int island = Integer.parseInt(scanner.nextLine());
        while (c == 0) {
            if (island >= 0 && island < game.getTable().getIslands().size()) {
                c = 1;
                // BISOGNA AGGIUNERE COME VIENE CARICATO IL MESSAGGIO
            } else {
                System.out.println("SERVER:  ILLIGAL INPUT");
                System.out.println("SERVER: SELECT ISLANDS WITH INT POSITION FROM 0 TO "
                        + game.getTable().getIslands().size());
                island = Integer.parseInt(scanner.nextLine());

            }
        }

        return msg_list;
    }


    public ArrayList<Object> req_cloud(Game game) {
        ArrayList<Object> msg_list = new ArrayList<Object>();
        Scanner scanner = new Scanner(System.in);
        int c = 1;
        System.out.println("SERVER: SELECT CLOUD WITH INT POSITION FROM 0 TO "
                + game.getTable().getIslands().size());
        int island = Integer.parseInt(scanner.nextLine());
        while (c == 0) {
            if (island >= 0 && island < game.getTable().getClouds().size()) {
                c = 1;
                // BISOGNA AGGIUNERE COME VIENE CARICATO IL MESSAGGIO
            } else {
                System.out.println("SERVER:  ILLIGAL INPUT");
                System.out.println("SERVER: SELECT CLOUD WITH INT POSITION FROM 0 TO "
                        + game.getTable().getClouds().size());
                island = Integer.parseInt(scanner.nextLine());

            }
        }

        return msg_list;

    }


    public ArrayList<Object> error_cloud(Game game) {
        ArrayList<Object> msg_list = new ArrayList<Object>();
        System.out.println("SERVER:  ILLIGAL INPUT FOR ISLAND");
        // BISOGNA AGGIUNERE COME VIENE CARICATO IL MESSAGGIO
        return msg_list;
    }

    public ArrayList<Object> end_turn(Game game) {
        ArrayList<Object> msg_list = new ArrayList<Object>();
        System.out.println("SERVER:  YOUR TURN HAS ENDED");
        // BISOGNA AGGIUNERE COME VIENE CARICATO IL MESSAGGIO
        return msg_list;
    }

    public ArrayList<Object> end_game(Game game, Player player) {
        ArrayList<Object> msg_list = new ArrayList<Object>();
        System.out.println("SERVER:  GAME HAS ENDED");
        System.out.println("SERVER: " + player.getName() + " HAS WON THE GAME");
        // BISOGNA AGGIUNERE COME VIENE CARICATO IL MESSAGGIO
        return msg_list;


    }

    public ArrayList<Object> character_mn_moves(Game game) {
        ArrayList<Object> msg_list = new ArrayList<Object>();
        Scanner scanner = new Scanner(System.in);
        this.update(game);
        System.out.println("SERVER:  1 MOVE MOTHER NATURE, 2 FOR PLAYING CHARACTER");
        int Move = Integer.parseInt(scanner.nextLine());

        int c = 0;
        int c_1 = 0;
        int c_2 = 0;

        while (c == 0) {
            if (Move == 1) {
                c = 1;
                msg_list = this.move_mn(game);

            } else if (Move == 2) {
                c = 1;
                int character;
                System.out.println("SERVER: SELECT CHARACTER WITH 0,1,2");
                character = Integer.parseInt(scanner.nextLine());
                while (c_2 == 0) {
                    if (character >= 0 || character <= 2) {
                        c_2 = 1;

                        //aggiungere messaggio qui
                    } else {
                        System.out.println("SERVER:  ILLIGAL INPUT");
                        System.out.println("SERVER: SELECT CHARACTER WITH 0,1,2");
                        character = Integer.parseInt(scanner.nextLine());
                    }

                }

            } else {
                System.out.println("SERVER:  ILLIGAL INPUT");
                System.out.println("SERVER:  1 FOR MOVING STUDENT, 2 FOR PLAYING CHARACTER");
                Move = Integer.parseInt(scanner.nextLine());
            }


        }
        return msg_list;


    }

    public ArrayList<Object> character_cloud_moves(Game game) {
        ArrayList<Object> msg_list = new ArrayList<Object>();
        Scanner scanner = new Scanner(System.in);
        this.update(game);
        System.out.println("SERVER:  1 MOVE MOTHER NATURE, 2 FOR PLAYING CHARACTER");
        int Move = Integer.parseInt(scanner.nextLine());

        int c = 0;
        int c_1 = 0;
        int c_2 = 0;

        while (c == 0) {
            if (Move == 1) {
                c = 1;
                msg_list = this.req_cloud(game);

            } else if (Move == 2) {
                c = 1;
                int character;
                System.out.println("SERVER: SELECT CHARACTER WITH 0,1,2");
                character = Integer.parseInt(scanner.nextLine());
                while (c_2 == 0) {
                    if (character >= 0 || character <= 2) {
                        c_2 = 1;

                        //aggiungere messaggio qui
                    } else {
                        System.out.println("SERVER:  ILLIGAL INPUT");
                        System.out.println("SERVER: SELECT CHARACTER WITH 0,1,2");
                        character = Integer.parseInt(scanner.nextLine());
                    }

                }

            } else {
                System.out.println("SERVER:  ILLIGAL INPUT");
                System.out.println("SERVER:  1 FOR MOVING STUDENT, 2 FOR PLAYING CHARACTER");
                Move = Integer.parseInt(scanner.nextLine());
            }


        }
        return msg_list;


    }
}
