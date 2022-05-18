package com.langrsoft.domain;

import java.util.Objects;

public class State {
    public final String name;
    public final String capital;

    public State(String name, String capital) {
        this.name = name;
        this.capital = capital;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return Objects.equals(name, state.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name + " (" + capital + ")";
    }

}
