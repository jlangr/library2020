package com.langrsoft.persistence;

import com.langrsoft.domain.Holding;
import com.langrsoft.domain.Patron;

import java.util.ArrayList;
import java.util.Collection;

public class PatronStore {
    private static final Collection<Patron> patrons = new ArrayList<>();
    private static int idIndex = 0;

    public static void deleteAll() {
        patrons.clear();
    }

    public void add(Patron patron) {
        if (patron.getId().equals(""))
            patron.setId("p" + PatronStore.nextId());
        patrons.add(copy(patron));
    }

    private static int nextId() {
        return ++idIndex;
    }

    private Patron copy(Patron patron) {
        var newPatron = new Patron(patron.getName());
        newPatron.setId(patron.getId());
        return newPatron;
    }

    public Collection<Patron> getAll() {
        return patrons;
    }

    public void addHoldingToPatron(Patron patron, Holding holding) {
        Patron found = find(patron.getId());
        if (found == null)
            throw new PatronNotFoundException();
        found.add(holding);
    }

    public Patron find(String id) {
        for (Patron each : patrons)
            if (id.equals(each.getId()))
                return each;
        return null;
    }
}
