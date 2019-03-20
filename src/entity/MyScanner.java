package entity;

import java.util.Scanner;

public class MyScanner {
    int inputValue;
    Scanner scanner = new Scanner(System.in);
    String tips = "请输入一个不小于1的正整数";
    int minBounder = 1;

    int maxBounder = 9999999;

    public int readNumber(){
        try {
         inputValue = Integer.valueOf(scanner.next());
         if(inputValue < minBounder||inputValue > maxBounder)
             throw new NumberFormatException(tips);
        }
        catch (NumberFormatException e){
            System.out.println("错误："+e.getMessage());
            this.readNumber();
        }
        return inputValue;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public int getMinBounder() {
        return minBounder;
    }

    public void setMinBounder(int minBounder) {
        this.minBounder = minBounder;
    }

    public int getMaxBounder() {
        return maxBounder;
    }

    public void setMaxBounder(int maxBounder) {
        this.maxBounder = maxBounder;
    }

}
