package entity;

import java.util.Random;

public class Operator {
    int priority;
    String value;
    public Operator(int priority){
        this.priority = priority;
        this.init(priority);
    }

    public int getPriority() {
        return priority;
    }

    public String getValue() {
        return value;
    }

    private void init(int priority){
        Random r = new Random();
        int num = 3;
        if(priority==1){
            num = r.nextInt(2)+1;
        }
        else if (priority==2){
            num = r.nextInt(2)+3;
        }
        else if (priority==3){
            num = r.nextInt(2)+5;
        }
        switch (num){
            case 1:
                this.value = "+";
                break;
            case 2:
                this.value = "-";
                break;
            case 3:
                this.value = "÷";
                break;
            case 4:
                this.value = "×";
                break;
            case 5:
                this.value = "(";
                break;
            case 6:
                this.value = ")";
                break;
        default:
        break;
    }
    }
}
