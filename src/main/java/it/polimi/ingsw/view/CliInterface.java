package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;

import java.util.ArrayList;

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
            /**
            switch (msg) {
                case msg -> System.out.println("Parameter is A");
                case b -> System.out.println("Parameter is b");
                default -> System.out.println("Parameter is unknown");
            };
            **/









            // ci sono diversi messaggi in entrata
            if (msg=="UPDATE") {
                // aggiorno il model e faccio show
            }
            else if(msg=="REQ_ASSISTANT"){
                // richiedo assistente
            }
            else if(msg=="ASSISTANT_ERROR"){
                // richiedo nuovo assistente per errore
            }
            else if(msg=="START_TURN"){
                // avviso inizio turno
            }
            else if(msg=="STUDENT_DESTINATION"){
                // richiedo dove mettere lo studente selezionato
            }

            else if(msg=="NOT_ENAUGH_MONEY"){
                // avviso che non hai abbastanza soldi per usare il character
            }
            else if(msg=="REQ_PARAMS_CHARACTER"){
                // avviso che non hai abbastanza soldi per usare il character
            }
            else if(msg=="MOVE_MN"){
                // richiedo posizionare madre natura
            }
            else if(msg=="MN_POSITION_ERROR"){
                // la posizione non va bene
            }
            else if(msg=="CHARACTER_STUDENT_MOVES"){
                // avviso che non hai abbastanza soldi per usare il character
            }
            else if(msg=="CHARACTER_MN_MOVES"){
                // richiedo spiego mosse (character, MN)
            }
            else if(msg=="CHARACTER_CLOUD_MOVES"){
                // richiedo spiego mosse (character, CLOUD)
            }
            else if(msg=="REAQ_CLOUD"){
                // richiedo spiego mosse (character, cloud)
            }
            else if(msg=="ERROR_CLOUD"){
                // richiedo nuova nuvola
            }
            else if(msg=="END_TURN"){
                // avviso fine turno
            }

        }
    }

