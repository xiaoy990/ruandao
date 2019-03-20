import entity.Fraction;
import entity.MyScanner;
import tools.equationfactory.EquationFactory;
import tools.equationfactory.impl.EquationFactoryImpl;
import tools.arithmeticfactory.ArithmeticFactory;
import tools.arithmeticfactory.impl.ArithmeticFactoryImpl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    private static  FileWriter fileWriter = null;
    public static void main(String[] args) {
        File file = new File("result.txt");
        try {
            FileWriter iniFileWriter = new FileWriter(file);
            iniFileWriter.write("");
            iniFileWriter.flush();
        } catch (IOException e) {
            System.out.println("文件初始化错误，请联系管理员");
            e.getMessage();
        }
        try {
            fileWriter = new FileWriter(file,true);
            fileWriter.write("2017012545\n");
            fileWriter.flush();
        } catch (IOException e) {
            System.out.println("文件生成或写入出错，请联系管理员");
            e.getMessage();
        }

        System.out.println("请输入要打印的算式类型：0：普通算式 1：分数算式");
        MyScanner myScanner = new MyScanner();
        myScanner.setTips("只能输入0或1");
        myScanner.setMinBounder(0);
        myScanner.setMaxBounder(2);
        int type = myScanner.readNumber();
        if(type == 0){
            generate();
        }
        else if(type == 1){
            System.out.println("输入算式数量");
            myScanner.setMinBounder(1);
            myScanner.setMaxBounder(99999);
            myScanner.setTips("请输入一个大于0且小于99999的数");
            int number = myScanner.readNumber();
            withFraction(number);
        }

    }
        private static void generate(){
            ArithmeticFactory test = new ArithmeticFactoryImpl();
            MyScanner scanner = new MyScanner();
            ArrayList list1;
            System.out.println("输入算式数量");
            int num = scanner.readNumber();
            System.out.println("输入每个算式数字数量");
            scanner.setMinBounder(2);
            scanner.setTips("输入数字且数字数值不小于2");
            int itemNum = scanner.readNumber();
            EquationFactory ef = new EquationFactoryImpl(itemNum);
            while (num-- != 0) {
                list1 = ef.creatEquation();
                String expression = String.join("", list1);
                String result = test.shuntingYard(list1);
                if (!ArithmeticFactoryImpl.nag) {
                    String curResult = expression + "=" + result +"\n";
                    System.out.print(curResult);
                    try {
                        fileWriter.write(curResult);
                        fileWriter.flush();
                    } catch (IOException e) {
                        System.out.println("文件写入出错");
                        e.getMessage();
                    }

                } else
                    num++;
            }
        }

        private static void withFraction(int num){

            int n = num;
            while(n!=0){
                EquationFactory ef = new EquationFactoryImpl();
                ArrayList list = ef.creatEquationWithFraction();
                ArrayList result = new ArrayList();
                for (Object str:list){
                    result.add(str.toString());
                }
                String str = String.join("",result);
                ArithmeticFactory af = new ArithmeticFactoryImpl();
                ArrayList<String> list1 = af.doCacuFc(list);
                int a = Integer.valueOf(list1.get(0));
                int b = Integer.valueOf(list1.get(2));
                if(a<=0||b<=0||a>1000||b>1000&&a/b<100||a/b>1){
                    n++;
                }
                else {
                    Fraction fraction = new Fraction(Integer.valueOf(list1.get(0)), Integer.valueOf(list1.get(2)));
                    String curResult = str + "=" +fraction.getValue() + "\n";
                    System.out.print(curResult);
                    try {
                        fileWriter.write(curResult);
                        fileWriter.flush();
                    } catch (IOException e) {
                        System.out.println("文件写入出错");
                        e.getMessage();
                    }
                }
                n--;
            }
        }
}
