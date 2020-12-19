

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.ArrayList;

//tips
/*
two stacks
operand, thrwo them on
frist operator, push
operator, higher priority, throw
push numbers
operator -> lower priority? now solve it
pop operator and operatnd
anything can go ontop of bracket



*/


/**
 * This class uses two stacks to evaluate an infix arithmetic expression from an
 * InputStream. It should not create a full postfix expression along the way; it
 * should convert and evaluate in a pipelined fashion, in a single pass.
 */
public class InfixExpressionEvaluator {
    // Tokenizer to break up our input into tokens
    StreamTokenizer tokenizer;

    StackInterface<Character> doubleCheck = new ArrayStack<Character>();
    ArrayList<Boolean> paran = new ArrayList<Boolean>(); 
    String numpa = "";
    
    StackInterface<Character> operatorStack;// Stacks for operators (for converting to postfix) and operands (for
    // evaluating)
    StackInterface<Double> operandStack;
    
   

    /**
     * Initializes the evaluator to read an infix expression from an input
     * stream.
     * @param input the input stream from which to read the expression
     */
    public InfixExpressionEvaluator(InputStream input) {
        // Initialize the tokenizer to read from the given InputStream
        tokenizer = new StreamTokenizer(new BufferedReader(
                        new InputStreamReader(input)));

        // StreamTokenizer likes to consider - and / to have special meaning.
        // Tell it that these are regular characters, so that they can be parsed
        // as operators
        tokenizer.ordinaryChar('-');
        tokenizer.ordinaryChar('/');

        // Allow the tokenizer to recognize end-of-line, which marks the end of
        // the expression
        tokenizer.eolIsSignificant(true);

        // Initialize the stacks
        operatorStack = new ArrayStack<Character>();
        operandStack = new ArrayStack<Double>();
    }

    /**
     * Parses and evaluates the expression read from the provided input stream,
     * then returns the resulting value
     * @return the value of the infix expression that was parsed
     */
    public Double evaluate() throws InvalidExpressionException {
        // Get the first token. If an IO exception occurs, replace it with a
        // runtime exception, causing an immediate crash.
        try {
            tokenizer.nextToken();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Continue processing tokens until we find end-of-line
        while (tokenizer.ttype != StreamTokenizer.TT_EOL) {
            // Consider possible token types
            switch (tokenizer.ttype) {
                case StreamTokenizer.TT_NUMBER:
                    // If the token is a number, process it as a double-valued
                    // operand
                    
                    paranNumCheck('#');
                    checkIfDouble('#');
                    //System.out.println("this works1");
                    handleOperand((double)tokenizer.nval);
                    break;
                case '+':
                case '-':
                case '*':
                case '/':
                case '^':
                    // If the token is any of the above characters, process it
                    // is an operator
                    
                    paranNumCheck('x');
                    checkIfDouble('x');
                    handleOperator((char)tokenizer.ttype);
                    break;
                case '(':
                case '{':
                    // If the token is open bracket, process it as such. Forms
                    // of bracket are interchangeable but must nest properly.
                    
                    paran.add(true);
                    paranNumCheck('(');
                    handleOpenBracket((char)tokenizer.ttype);
                    break;
                case ')':
                case '}':
                    // If the token is close bracket, process it as such. Forms
                    // of bracket are interchangeable but must nest properly.
                    
                    addClosed();
                    paranNumCheck(')');
                    handleCloseBracket((char)tokenizer.ttype);
                    break;
                case StreamTokenizer.TT_WORD:
                    // If the token is a "word", throw an expression error
                    throw new InvalidExpressionException("Unrecognized symbol: " +
                                    tokenizer.sval);
                default:
                    // If the token is any other type or value, throw an
                    // expression error
                    throw new InvalidExpressionException("Unrecognized symbol: " +
                                    String.valueOf((char)tokenizer.ttype));
            }

            // Read the next token, again converting any potential IO exception
            try {
                tokenizer.nextToken();
            } catch(IOException e) {
                throw new RuntimeException(e);
            }
        }

        // Almost done now, but we may have to process remaining operators in
        // the operators stack
        handleRemainingOperators();

        // Return the result of the evaluation
        // TODO: Fix this return statement
        return operandStack.peek();
    }

    /**
     * This method is called when the evaluator encounters an operand. It
     * manipulates operatorStack and/or operandStack to process the operand
     * according to the Infix-to-Postfix and Postfix-evaluation algorithms.
     * @param operand the operand token that was encountered
     */
    void handleOperand(double operand) {

        
        operandStack.push(operand);
        // TODO: Complete this method
    }

    /**
     * This method is called when the evaluator encounters an operator. It
     * manipulates operatorStack and/or operandStack to process the operator
     * according to the Infix-to-Postfix and Postfix-evaluation algorithms.
     * @param operator the operator token that was encountered
     */
    void handleOperator(char operator) {
        
    
        
        // TODO: Complete this method

        boolean moveOn;

        if(operatorStack.isEmpty())  // if its empty
            operatorStack.push(operator);
        else if(operatorStack.peek() == '(')
        {
            
            operatorStack.push(operator);
        }
        else
        {
            char temp = precedenceCheck(operator, operatorStack.peek());
            if( temp == '#')//good to add
                operatorStack.push(operator);

            else{//SOLVIN
                do{
                    
                   
                    solveExpression();
                    if(operatorStack.isEmpty())
                    {
                        moveOn = true;
                    }
                    else if(precedenceCheck(operator,operatorStack.peek())!= '#')
                    {
                        moveOn = false;
                    }
                    else
                    {
                        moveOn = true;
                    }
                }while(!moveOn);

                operatorStack.push(operator);
            }
        }
    }

    /**4
     * This method is called when the evaluator encounters an open bracket. It
     * manipulates operatorStack and/or operandStack to process the open bracket
     * according to the Infix-to-Postfix and Postfix-evaluation algorithms.
     * @param openBracket the open bracket token that was encountered
     */
    void handleOpenBracket(char openBracket) {
        // TODO: Complete this 
        operatorStack.push(openBracket);
    }

    /**
     * This method is called when the evaluator encounters a close bracket. It
     * manipulates operatorStack and/or operandStack to process the close
     * bracket according to the Infix-to-Postfix and Postfix-evaluation
     * algorithms.
     * @param closeBracket the close bracket token that was encountered
     */
    void handleCloseBracket(char closeBracket) {
        
        
        do{
            solveExpression();
            
        }while(operatorStack.peek() != '(');
        
        
        operatorStack.pop();

        // TODO: Complete this method
    }

    /**
     * This method is called when the evaluator encounters the end of an
     * expression. It manipulates operatorStack and/or operandStack to process
     * the operators that remain on the stack, according to the Infix-to-Postfix
     * and Postfix-evaluation algorithms.
     */
    void handleRemainingOperators() {
        
        for(int i = 0; i< paran.size(); i++)
        {
            System.out.println("Checking" + paran.get(i));
            if(paran.get(i))
                throw new InvalidExpressionException("Parans are not right");
        }
        while(!operatorStack.isEmpty())
        {
            solveExpression();
        }
        // TODO: Complete this method
    }


    /**
     * Creates an InfixExpressionEvaluator object to read from System.in, then
     * evaluates its input and prints the result.
     * @param args not used
     */
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //if o1 lower, return o2
    //if higher, return #
    
    //o1 you want to add
    //o2 you are peaking
    private char precedenceCheck(char o1, char o2){
        if(o1== o2)
            return o2; //equal
        else if(o2 == '^')//exp
            return o2;
        else if(o1 == '^')
            return '#';
        
        else if(o1 == '*' || o1 == '/')
        {
            if(o2 == '/' || o2 == '*' )
                return o2;
            else 
                return '#';
        }
        else if(o1 == '+' || o1 == '-')
        {
            return o2;
        }
        else if(o2 == '(')
            return '#';
        else
            return '@';//something wrong happened....

    }

    private void solveExpression()
    {
        
        
     if(operatorStack.peek() == '^')
        {
           //solve that baby
            operatorStack.pop();
            double num1, num2;
            num1 = operandStack.pop();
            num2 = operandStack.pop(); //WE NEED TO CHECK THIS
            operandStack.push(Math.pow(num2, num1));  
        }
        else if(operatorStack.peek() == '*')
        {
            operatorStack.pop();
            double num1, num2;
            num1 = operandStack.pop();
            num2 = operandStack.pop(); //WE NEED TO CHECK THIS
            operandStack.push(num2 * num1);  
        }
        else if(operatorStack.peek() == '/')
        {
             operatorStack.pop();
            double num1, num2;
            num1 = operandStack.pop();
            num2 = operandStack.pop(); //WE NEED TO CHECK THIS
            operandStack.push(num2 / num1);  
        }
        else if(operatorStack.peek() == '+')
        {
             operatorStack.pop();
            double num1, num2;
            num1 = operandStack.pop();
            num2 = operandStack.pop(); //WE NEED TO CHECK THIS
            operandStack.push(num2 + num1);  
        }
        else if(operatorStack.peek() == '-')
        {
             operatorStack.pop();
            double num1, num2;
            num1 = operandStack.pop();
            num2 = operandStack.pop(); //WE NEED TO CHECK THIS
            operandStack.push(num2 - num1);  
        }
       
    }

    private void checkIfDouble(char sym)
    {
        // x = operator
        // # = number
        if(!doubleCheck.isEmpty())
        {
            if(sym != doubleCheck.peek())
                doubleCheck.push(sym);
            else
                throw new InvalidExpressionException("You either have two operators, or two operands in a row. It ain't happening...");
        }
        else
            doubleCheck.push(sym );
    }
    
    private void addClosed()
    {
        boolean changed = false;
        for(int i = 0; i < paran.size(); i++ )
        {
            if(paran.get(i))
            {
                paran.set(i, false);
                changed = true;
                break;
            }
        }
        
        if(changed== false)
            throw new InvalidExpressionException("parans messed up");
    }
       
    //x = operator
    //# = number
    //( = open paran
    //) = close paran
    private void paranNumCheck(char numparan)
    {
        if(numpa.length()==0)
            numpa += numparan;
        else
        {
            if(numparan == 'x')
            {
                if(numpa.charAt(numpa.length() - 1) == '(')
                {
                    throw new InvalidExpressionException("operator can be after open bracket");
                }
                else
                    numpa += numparan;
                    
            }
            else if(numparan == '#')
            {
                if(numpa.charAt(numpa.length() - 1) == ')')
                    throw new InvalidExpressionException("number cannot be after closed bracket");
                else
                    numpa += numparan;

            }
            else if(numparan == '(')
            {
                if(numpa.charAt(numpa.length() - 1) == '#')
                    throw new InvalidExpressionException("open bracket cannot be after number");
                else
                    numpa += numparan;

            }
            else if(numparan == ')')
            {
                if(numpa.charAt(numpa.length() - 1) == 'x')
                    throw new InvalidExpressionException("closed bracket cannot be after operator");
                else
                    numpa += numparan;

            }
        }
    }
    
    public static void main(String[] args) {
        System.out.println("Infix expression:");
        InfixExpressionEvaluator evaluator =
                        new InfixExpressionEvaluator(System.in);
        Double value = null;
        try {
            value = evaluator.evaluate();
        } catch (InvalidExpressionException e) {
            System.out.println("Invalid expression: " + e.getMessage());
        }
        if (value != null) {
            System.out.println(value);
        }
    }
    
    

}

