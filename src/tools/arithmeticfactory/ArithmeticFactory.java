package tools.arithmeticfactory;

import java.util.ArrayList;

public interface ArithmeticFactory {

    //调度场算法
    String shuntingYard(ArrayList<String> nifix);
    //运算符优先级判断
    int percedence(String operator);
    //分数计算
    public ArrayList<String> doCacuFc(ArrayList<Object> list);
}
