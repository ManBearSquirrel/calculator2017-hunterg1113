package com.arkansascodingacademy;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;

public class CalculatorPEMDAS
{
    private UserInputPEMDAS userInput = new UserInputPEMDAS();
    private ArrayList<String> equation = new ArrayList<>();
    private ArrayList<Operator> operators = new ArrayList<>();
    private ArrayList<Operator> pOperators = new ArrayList<>();
    private PCalculator pCalculator = new PCalculator();
    private Operator operator;

    public void run()
    {
        userInput.createArrayFromEquation();

        equation = userInput.getEquation();

        createArrayOfOperators(equation);

        while (pOperators.size() > 0)
        {
            calculateP();
        }

        createArrayOfOperators(equation);

        while (operators.size() > 1)
        {
            ArrayList<String> array = scanArrayOfOperators(operators);

            calculate(array);

            createArrayOfOperators(equation);
        }

        ArrayList<String> array = scanArrayOfOperators(operators);

        calculate(array);

        System.out.println("Result: " + equation.get(0));
    }

    public void calculateP()
    {
        int index = pOperators.get(pOperators.size() - 1).getIndex();

        ArrayList<String> pArray = new ArrayList<>();

        boolean pFound = false;

        while (!pFound)
        {
            pArray.add(equation.remove(pOperators.get(pOperators.size() - 1).getIndex()));

            if (pArray.get(pArray.size() - 1).equals(")"))
            {
                pFound = true;
            }
        }

        String result = pCalculator.run(pArray);

        equation.add(index, result);

        pOperators.remove(pOperators.size()-1);
    }

    public void calculate(ArrayList<String> array)
    {
        BigDecimal secondNumber = new BigDecimal(array.get(0));
        String operator = array.get(1);
        BigDecimal firstNumber = new BigDecimal(array.get(2));
        String indexOfEquation = array.get(3);

        BigDecimal result = null;

        switch (operator)
        {
            case "+":
                System.out.println("Adding " + firstNumber + " to " + secondNumber);
                result = firstNumber.add(secondNumber);
                break;

            case "-":
                System.out.println("Subtracting " + secondNumber + " from " + firstNumber);
                result = firstNumber.subtract(secondNumber);
                break;

            case "*":
                System.out.println("Multiplying " + firstNumber + " by " + secondNumber);
                result = firstNumber.multiply(secondNumber);
                break;

            case "/":
                System.out.println("Dividing " + firstNumber + " by " + secondNumber);
                result = firstNumber.divide(secondNumber, 2, BigDecimal.ROUND_HALF_UP);
                break;

            case "^":
                System.out.println(firstNumber + " to the power of " + secondNumber);
                int x = secondNumber.intValue();
                result = firstNumber.pow(x);
                break;

            default:
                System.out.println("You are confusing me");
                result = new BigDecimal("0");
        }

        equation.add((Integer.parseInt(indexOfEquation) - 1), result.toString());
    }

    public ArrayList<String> scanArrayOfOperators(ArrayList<Operator> operators)
    {
        for (int i = 0; i < operators.size(); i++)
        {
            switch (operators.get(i).getOperator())
            {
                case "^":
                    return createArray(i);
            }
        }

        for (int i = 0; i < operators.size(); i++)
        {
            switch (operators.get(i).getOperator())
            {
                case "*":
                    return createArray(i);

                case "/":
                    return createArray(i);
            }
        }

        for (int i = 0; i < operators.size(); i++)
        {
            switch (operators.get(i).getOperator())
            {
                case "+":
                    return createArray(i);

                case "-":
                    return createArray(i);
            }
        }
        return null;
    }

    public void createArrayOfOperators(ArrayList<String> equation)
    {
        operators.clear();

        for (int i = 0; i < equation.size(); i++)
        {
            if (equation.get(i).equals("+") || equation.get(i).equals("-") || equation.get(i).equals("*") ||
                    equation.get(i).equals("/") || equation.get(i).equals("^"))
            {
                operator = new Operator(i, equation.get(i));
                operators.add(operator);
            }
            else if (equation.get(i).equals("("))
            {
                operator = new Operator(i, equation.get(i));
                pOperators.add(operator);
            }
        }
    }

    public ArrayList<String> createArray(int i)
    {
        ArrayList<String> array = new ArrayList<>();

        int x = operators.get(i).getIndex();

        array.add(equation.remove(x + 1));
        array.add(equation.remove(x));
        array.add(equation.remove(x - 1));
        array.add("" + x);

        operators.remove(i);

        return array;
    }
}