package com.c2.lc.lib.filters;

import com.c2.lc.lib.filters.interfaces.ApiFilterService;
import com.c2.lc.lib.services.BaseServicesImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ApiFilterServiceImpl extends BaseServicesImpl implements ApiFilterService {

    @Value("${aes.key:-}") private String aesKey;
    @Value("${aes.iv:-}") private String aesIV;

    @Override
    public String [] getDecryptedKeyValues(String key) throws Exception {
        return this.aesCbcEncryption.getDecryptedData(key, aesKey, aesIV).split("\\|");
    }

}
