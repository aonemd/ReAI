package endgame;

import search.SearchProblem;
import search.strategy.BFS;

public class Main {
    public static void main(String[] args) {
        SearchProblem problem = new Endgame();

        String input = "5,5;1,2;3,1;0,2,1,1,2,1,2,2,4,0,4,1;0,3,3,0,3,2,3,4,4,3";
        // String input = "3,3;0,0;1,0;0,2;2,0";
        // String input = "3,3;0,0;1,0;0,2,2,1;2,0";
        // String input = "2,2;0,0;1,0;1,1;0,1";
        // String input = "2,2;0,0;1,0;1,1;0,1";

        long startTime = System.nanoTime();

        String solution = problem.solve(input, new BFS(), true);
        System.out.println("\n+++\n" + solution);

        long endTime = System.nanoTime();
        long timeElapsed = (endTime - startTime) / 1000000;
        System.out.println("Time: " + timeElapsed + "ms");
    }
}
