package com.serdardal.notdefteritest;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

/**
 * Created by serdar on 25.03.2018.
 */

public class ismeGoreSirala  implements Comparator<Block> {

    @Override
    public int compare(Block block, Block t1) {
        Collator collator = Collator.getInstance(new Locale("tr","TR"));
        return collator.compare(block.getStatement(),t1.getStatement());
    }
}

