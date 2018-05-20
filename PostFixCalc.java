

import java.util.Stack;

public class PostFixCalc {

	
	/*
	 * To get PostFix as String from Infix expression
	 * Go through each char in the Infix String
	 * when 0-9 digit concatenate it to postfix string
	 * when '('  push it onto stack
	 * when ')'  concatenate operators and operands to 
	 * postfix string by popping from stack
	 *    until the '(' is encountered on the stack
	 *    then just pop and discard it
	 * when operator '+-/*' AND lowerPrecedence than 
	 * what's on top of stack concatenate the
	 *  operators and operands
	 *   to postfix string by popping from stack
	 *    until stack is empty OR top is '(' or
	 *     top has higher precedence
	 *   then push the operator onto the stack 
	 * After the entire Infix String has been evaluated,
	 *  pop everything that is still on the stack
	 * and concatenate it to the end of the Postfix expression.
	 */
	public static String getPostFixFromInfix(String s){
		String postFix ="";
		Stack<Character> stack = new Stack<Character>();
		for(int i=0; i<s.length(); i++){
			char ch = s.charAt(i);//get current char
			if(isOperand(ch)){
				postFix += ch;//concatenate the digit to string
			}
			else if(ch =='('){
				stack.push(ch);//push '(' onto stack
			}
			else if(ch == ')'){
				while( (!stack.isEmpty()) && (stack.peek() != '(')){
					postFix += stack.pop();//take from stack and concat
				}
				stack.pop();//pop leftparen and discard
			}
			else if(isOperator(ch)){// + / - * 
				while( (!stack.isEmpty()) && (stack.peek() != '(') 
						   && (hasLowerPrecedence(ch, stack.peek()))){
					postFix += stack.pop();//concat operators and operands until...
				}
				stack.push(ch);//push operator onto stack
				
			}
			else{
				System.out.println("Sorry went bonkers"+ch+ "was not expected");
			}
		}
		while(!stack.isEmpty()){
			postFix += stack.pop();//concate to postfix remaining stack content
		}
		return postFix;
	}

	private static boolean isOperand(char c){
		if(Character.isDigit(c)){
			return true;
		}
		return false;//was not 0-9 digit
	}
	private static boolean isOperator(char c){
		switch(c){
			case '-':
			case '*':
			case '/':
			case '+':
				return true;
			default:
				return false;
		}
	}
	/*compare c1 to c2, return true when c1 has lower prec than c2
	 * return false when c1 has equal or greater prec to c2
	 */
	private static boolean hasLowerPrecedence(char c1, char c2){
		switch(c1){
			case '+':
			case '-':
				if((c2 == '*') || (c2=='/')){
					return true;
				}
				break;
			case '*':
			case '/':
				if((c2 == '*')|| (c2=='/') || (c2=='+') || (c2=='-')  ){
					return false;
				}
				break;
		}
		return false;
	}
	
	/*
	 * go through each char in the PostFix String
	 * if 0-9 push it onto the stack
	 * if it is '+*-/' pop twice and perform operation
	 * push result onto stack
	 */
	public static int evalPostFix(String s){
		int result =0;
		Stack<Integer> stack = new Stack<Integer>();
		for(int i=0; i<s.length(); i++){
			char c = s.charAt(i);
			if(isOperand(c)){
				stack.push(Character.getNumericValue(c));
			}
			else if(isOperator(c)){
				int op2 = stack.pop();
				int op1 = stack.pop();
				result = getCalc(op1, c, op2);
				stack.push(result);//stack.push(getCalc(op1, c, op2));
			}
		}
		return result;//stack.pop();
	}
	
	/*perform calculation depending on operator*/
	private static int getCalc(int op1, char c, int op2){
		int result = 0;
		switch(c){
		case '+':
			result = op1 + op2;
			break;
		case '-':
			result = op1 - op2;
			break;
		case '/':
			result = op1 / op2; //if op2 is 0 it will go nuts
			break;
		case '*':
			result = op1 * op2;
			break;
		}
		return result;
	}
}
