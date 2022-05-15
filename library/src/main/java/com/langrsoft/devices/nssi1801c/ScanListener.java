package com.langrsoft.devices.nssi1801c;

public interface ScanListener {
    void scan(String barcode);

    void pressComplete();
}
