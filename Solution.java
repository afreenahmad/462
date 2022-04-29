import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Stack;
import java.util.Iterator;

public class Solution {

    public static void main(String[] args) throws NumberFormatException, IOException {
        // Read input for the problem
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        HashSet<Integer> forbiddenCodes = new HashSet<Integer>();
        Queue<Node> nodesQueue = new ArrayDeque<Node>();
        Stack<Node> nodesPath = new Stack<Node>();

        StringBuilder sb = new StringBuilder();

        int digitCount = Integer.valueOf(reader.readLine());
        int startCode = Integer.valueOf(reader.readLine()); // The starting lock code
        int targetCode = Integer.valueOf(reader.readLine()); // The target lock code

        int count = Integer.valueOf(reader.readLine());
        for (int i = 0; i < count; i++) {
            //int taboo = Integer.valueOf(reader.readLine());
            forbiddenCodes.add(Integer.valueOf(reader.readLine()));
        }

        if (forbiddenCodes.contains(startCode) || forbiddenCodes.contains(targetCode))
        {
            sb.append(-1);
        } else {
            Node target = null;
            boolean targetFound = false;
            Node Start = new Node(startCode);
            nodesQueue.add(Start);

            if (nodesQueue.peek() == null) {
                createChildren(nodesQueue.poll(), digitCount, nodesQueue, forbiddenCodes);
                {
                    if (forbiddenCodes.contains(targetCode)) {
                        targetFound = true;
                    }

                }
            }
            Iterator<Node> iterator = nodesQueue.iterator();
            //int length = nodesQueue.size();
            //Node target = null;
            while (iterator.hasNext())
            {
                Node current = iterator.next();
                if (current.currNum == targetCode)
                {
                    target = current;
                }
            }
            while (target != null)
            {
                nodesPath.push(target);
                target = target.prevNode;
            }

            sb.append(nodesPath.size() - 1).append("\n");
            nodesPath.pop();

            while (nodesPath.isEmpty() == false)
            {
                Node printOutput = nodesPath.pop();
                sb.append(printOutput.direction);
                sb.append(printOutput.digitMoved);
                sb.append(" ");
                String formatString = String.format("%0" + digitCount + "d", printOutput.currNum);
                sb.append(formatString);
                sb.append(System.getProperty("line.separator"));
            }
        }
        System.out.println(sb);
    }

//            for(int i = 1; i<length;i++)
//            {
//                //if()
//
//            }
//            //make targetNode
//            //iterate through the node queue (level order traversal) check if the node you are at is equal to the targetcode
//            //then set it equal to the targetNode
//
//            //after you found targetcode
//            //while targetNode not equal to null
//            //put it in the nodePath, push in that target node
//            //then push that node's previous node
//            while(targetNode!=null)
//            {
//                nodesPath.push(targetNode);
//                targetNode = targetNode.prevNode;
//            }
//            sb.append(nodesPath.size()-1);
//            while(!nodesPath.isEmpty())
//            {
//                Node print=nodesPath.pop();
//                String s = String.format();
//                sb.append(s);
//            }
//
//        }
//        System.out.print(sb);

    static void createChildren(Node parent, int digitCount, Queue<Node>nodesQueue,HashSet<Integer>forbiddenCodes )
    {
        for(int i = 1; i< digitCount; i++)
        {
            Node down = parent.changeCode('D',i+1,digitCount);
            if(forbiddenCodes.contains(down.currNum)==false)
            {
                nodesQueue.add(down);
                forbiddenCodes.add(down.currNum);
            }
            Node up = parent.changeCode('U',i+1, digitCount);
            if(forbiddenCodes.contains(up.currNum)==false)
            {
                nodesQueue.add(up);
                forbiddenCodes.add(up.currNum);

            }
        }
    }

}


class Node {
    int currNum;        // Current Lock Code
    char direction;     // U or D - for the move that got us to this combination
    int digitMoved;     // Which digit was moved to get us here
    Node prevNode;      // Link to the previous state

    public Node(int currNum) {
        this.currNum = currNum;
    }

    public Node(int currNum, Node prevNode, char direction, int digitMoved) {
        this.currNum = currNum;
        this.prevNode = prevNode;
        this.direction = direction;
        this.digitMoved = digitMoved;
    }

    /**
     * Return number that results from taking the current state and moving a digit either
     * up or down.
     *
     * @param direction either 'U' or 'D' for the direction of movement (up or down)
     * @param digit the digit that was moved
     *
     * @return the new lock code
     */
    public Node changeCode(char direction, int digit, int digitCount) {
        int base = (int) Math.pow(10, digitCount - digit);
        int newDigit;
        int currentDigit = (currNum /base) % 10;

        if (direction == 'U') {
            newDigit = (currentDigit + 1) % 10;
        }
        else {
            newDigit = (currentDigit + 9) % 10;
        }

        return new Node(currNum + (newDigit - currentDigit) * base, this, direction, digit);
    }
}