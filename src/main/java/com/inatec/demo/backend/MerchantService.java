package com.inatec.demo.backend;


import org.apache.commons.beanutils.BeanUtils;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Anatoly Chernysh
 */
/**
 * Separate Java service class. Backend implementation for the address book
 * application, with "detached entities" simulating real world DAO. Typically
 * these something that the Java EE or Spring backend services provide.
 */
public class MerchantService {

    private HashMap<Long, Merchant> merchants = new HashMap<Long, Merchant>();

    private long nextId = 0;

    private static MerchantService instance;

    public static MerchantService createDemoService() {
        if (instance == null) {
            final MerchantService merchantService = new MerchantService();
            instance = merchantService;
        }

        return instance;
    }

    public synchronized List<Merchant> findAll(String stringFilter) {
        ArrayList arrayList = new ArrayList();
        for (Merchant merchant : merchants.values()) {
            try {
                boolean passesFilter = (stringFilter == null || stringFilter
                        .isEmpty())
                        || merchant.toString().toLowerCase()
                        .contains(stringFilter.toLowerCase());
                if (passesFilter) {
                    arrayList.add(merchant.clone());
                }
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(MerchantService.class.getName()).log(
                        Level.SEVERE, null, ex);
            }
        }

        Collections.sort(arrayList, new Comparator<Merchant>() {

            @Override
            public int compare(Merchant o1, Merchant o2) {
                return (int) (o2.getId() - o1.getId());
            }
        });

        return arrayList;
    }

    public synchronized long count() {
        return merchants.size();
    }

    public synchronized void delete(Merchant value) {
        merchants.remove(value.getId());
    }

    public synchronized void save(Merchant entry) {
        if (entry.getId() == null) {
            entry.setId(nextId++);
        }
        try {
            entry = (Merchant) BeanUtils.cloneBean(entry);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        merchants.put(entry.getId(), entry);
    }
}