package tools.equationfactory;

import java.util.ArrayList;

public interface EquationFactory {
    //创建无分数的中缀表达式
    ArrayList creatEquation();
    //创建有分数的中缀表达式
    ArrayList creatEquationWithFraction();
}
