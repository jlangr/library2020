package com.langrsoft.persistence;

import com.langrsoft.service.library.DuplicateHoldingException;
import com.langrsoft.domain.Holding;
import com.langrsoft.util.MultiMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class HoldingStore implements Iterable<Holding> {
    private static MultiMap<String, Holding> holdingsByClassification = new MultiMap<>();

    public static void deleteAll() {
        holdingsByClassification.clear();
    }

    public void save(Holding holding) {
        if (findByBarcode(holding.getBarcode()) != null)
            throw new DuplicateHoldingException();
        holdingsByClassification.put(holding.getMaterial().getClassification(), copy(holding));
    }

    private Holding copy(Holding holding) {
        return new Holding(
                holding.getMaterial(),
                holding.getBranch(),
                holding.getCopyNumber());
    }

    public List<Holding> findByClassification(String classification) {
        var results = holdingsByClassification.get(classification);
        return results == null ? new ArrayList<>() : results;
    }

    public Holding findByBarcode(String barCode) {
        var holdings = holdingsByClassification.get(classificationFrom(barCode));
        if (holdings == null)
            return null;
        var found = holdings.stream()
                .filter(holding -> holding.getBarcode().equals(barCode))
                .findFirst();
        return found.isEmpty() ? null : found.get();
    }

    private String classificationFrom(String barCode) {
        return barCode.substring(0, barCode.indexOf(Holding.BARCODE_SEPARATOR));
    }

    public int size() {
        return holdingsByClassification.size();
    }

    @Override
    public Iterator<Holding> iterator() {
        return allHoldings().iterator();
    }

    private Collection<Holding> allHoldings() {
        return holdingsByClassification.values();
    }

    public List<Holding> findByBranch(String branchScanCode) {
        return allHoldings().stream()
                .filter(holding -> holding.getBranch().getScanCode().equals(branchScanCode))
                .collect(toList());
    }
}
