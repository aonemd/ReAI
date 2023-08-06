package search;

import java.util.function.Function;

import util.Tuple;

public record Operator(String name, int cost, Function<State, Tuple<State, Integer>> applyFn) {
    public Tuple<State, Integer> apply(State state) {
        return applyFn().apply(state);
    }
}
