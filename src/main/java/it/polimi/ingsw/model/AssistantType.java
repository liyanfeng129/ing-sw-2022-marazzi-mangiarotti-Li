package it.polimi.ingsw.model;


import java.io.Serializable;

public enum AssistantType implements Serializable {

    Card_1(1, 1), Card_2(2, 1), Card_3(3, 2),
    Card_4(4, 2), Card_5(5, 3), Card_6(6, 3),
    Card_7(7, 4), Card_8(8, 4), Card_9(9, 5),
    Card_10(10, 5);

    private int Num;
    private int Steps;

    private AssistantType(int Num, int Steps) {
        this.Num = Num ;
        this.Steps = Steps ;
    }
    public static AssistantType index(int i){
        return AssistantType.values()[i];
    }

    public int getNum(){
        return Num;
    }
    public int getSteps(){
        return Steps;
    }

    @Override
    public String toString() {
        return "AssistantType{" +
                "Num=" + Num +
                ", Steps=" + Steps +
                '}';
    }
}
