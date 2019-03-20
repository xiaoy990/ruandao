package tools.arithmeticfactory.impl;

import entity.Fraction;
import tools.arithmeticfactory.ArithmeticFactory;

import java.util.ArrayList;
import java.util.Stack;

public class ArithmeticFactoryImpl implements ArithmeticFactory {
    public static boolean nag = false;
    @Override
    /*
    * 调度场算法生成后缀表达式并计算结果
    * 参数ArrayList nifix：被转换的的中缀表达式
    */
    public String shuntingYard(ArrayList<String> nifix) {
        this.nag = false;
        Stack<Integer> numstack = new Stack<Integer>();
        Stack opstack = new Stack<String>();
        /*
        *循环中缀表达式 将数字入numstack 将符号处理后入opstack
        */
        int i = 0;
        while (!nifix.isEmpty()) {
            if (isInt(nifix.get(i))) {
                numstack.push(Integer.valueOf(nifix.get(i)));
            }
            else {
                //操作符入栈：当前操作符栈为空，当前操作符为（，当前栈顶元素为（，当前操作符大于栈顶操作符
                if (opstack.empty() || percedence(nifix.get(i)) == 3 || percedence((String) opstack.peek()) == 3 || percedence(nifix.get(i)) > percedence((String) opstack.peek()))
                    //操作符栈栈顶元素为（且当前操作符为）时消除括号
                    if (nifix.get(i).equals(")") && opstack.peek().equals("(")) opstack.pop();
                    else
                        opstack.push(nifix.get(i));
                 else{
                        //当前操作符为）时，弹出符号栈与数字栈不断运算，直到操作符栈遇到左括号为止
                        if(nifix.get(i).equals(")")){
                            String curop = (String)opstack.pop();
                            while(!curop.equals("(")){
                                int a = Integer.valueOf(numstack.pop());
                                int b = Integer.valueOf(numstack.pop());
                                numstack.push(doCacu(b,a,curop));
                                curop = (String)opstack.pop();
                            }
                        }
                        else {
                            //当前操作符优先级不大于栈顶操作符优先级时弹出栈顶操作符与数字栈栈顶元素并将计算结果入栈将当前操作符入栈
                            String curop = (String)opstack.pop();
                            int a = Integer.valueOf(numstack.pop());
                            int b = Integer.valueOf(numstack.pop());
                            numstack.push(doCacu(b,a,curop));
                            opstack.push(nifix.get(i));
                        }
                 }
            }
            //将当前操作符出队
            nifix.remove(i);
        }
        //将栈中剩余运算运算完毕
        while(!opstack.empty()){
            String curop = (String)opstack.pop();
            int a = Integer.valueOf(numstack.pop());
            int b = Integer.valueOf(numstack.pop());
            numstack.push(doCacu(b,a,curop));
        }
        //返回运算结果
        return String.valueOf(numstack.peek());
    }

    @Override
    /*
    *判断运算符优先级
    * 参数String operator：运算符
    */
    public int percedence(String operator) {
        switch (operator){
            case "+":
            case "-":
                return 1;
            case "×":
            case "÷":
                return 2;
            case "(":
                return 3;
            default:
                return -1;
        }
    }

    /*
    * 运算分数
    * 参数：仅含有真分数的表达式
    */
    public ArrayList<String> doCacuFc(ArrayList<Object> list){
        ArrayList<Integer> myList = new ArrayList<Integer>();
        ArrayList<Fraction> myFractionList = new ArrayList<>();
        ArrayList<String> myStringList = new ArrayList<>();
        for (Object str:list){
            if (str.getClass()== Fraction.class){
                myList.add(((Fraction) str).getDenominator());
                myFractionList.add((Fraction)str);
            }
            else
                myStringList.add((String)str);
        }
        int mutDnmt = 1;
        for (int i:myList){
            mutDnmt = i*mutDnmt;
        }
        //最大公因数
        int gcf = myList.get(0);
        for(int i:myList){
            if(i>=gcf)
                gcf = gcd(i,gcf);
            else
                gcf = gcd(gcf,i);
        }
//        System.out.println("最大公因数是 "+gcf);
        //最小公倍数
        int lcm = mutDnmt/gcf;
//        System.out.println("最小公倍数是 "+lcm);
        //通分 并记录通分后的分子
        int temp;
        int[] a = new int[myFractionList.size()];
        int i = 0;
        for (Fraction f:myFractionList){
            temp = lcm/f.getDenominator();
//            System.out.print("分母为 "+f.getDenominator()+" 倍数为 "+temp);
            f.setNumerator(f.getNumerator()*temp);
//            System.out.println("第"+i+"个分子的值为 "+f.getNumerator());
            a[i] = f.getNumerator();
            i++;
        }
        int sum = 0;
        for(int j=0;j<a.length;j++){
            if(sum==0||myStringList.get(j-1).equals("+")){
                sum = sum+a[j];
//                System.out.println("第"+j+"次计算为+法计算 "+sum);
            }
            else if(myStringList.get(j-1).equals("-")){
                sum = sum-a[j];
//                System.out.println("第"+j+"次计算为-法计算 "+sum);
            }
        }
        ArrayList<String> result = new ArrayList();
        result.add(String.valueOf(sum));
        result.add("/");
        result.add(String.valueOf(lcm));
        return result;
    }
    //最大公因数函数
    private int gcd(int a,int b){
        if(a%b==0){
            return b;
        }
        else
            return gcd(b,a%b);
    }
    /*
    * 判断当前操作数是否为数字
    * 参数String str：当前操作数
    */
    private boolean isInt(String str){
        boolean flag = true;
        try{
            Integer.valueOf(str);
        }
        catch (Exception e){
            flag = !flag;
        }
        return flag;
    }


    /*
    *运算逻辑
    * 参数int a,int b,String op : 运算数1， 运算数2， 操作符
    */
    private int doCacu(int a ,int b,String op){
        switch (op){
            case "+":
                return a + b;
            case "-":
                if(a-b<0){
                    this.nag = true;
                }
                return a - b;
            case "×":
                return a * b;
            case "÷":
                if(b==0)
                    return -1;
                if(a%b!=0){
                    this.nag = true;
                }
                return a / b;
            default :
                this.nag = true;
                return -1;

        }
    }
}
