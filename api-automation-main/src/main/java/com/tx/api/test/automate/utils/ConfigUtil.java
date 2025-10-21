package com.tx.api.test.automate.utils;

import com.tx.api.test.automate.exception.ApiAutomationException;
import com.tx.api.test.automate.security.BasicAuth;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

@Slf4j
public class ConfigUtil {
    public static void setUserContext(String configFile){
        if(log.isInfoEnabled()){
            log.info("loading properties from file: {}",configFile);
        }
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream(configFile));
            UserContext userContext = new UserContext();
            userContext.setTraceLogEnabled(Boolean.parseBoolean(properties.getProperty("automation.enableTraceLog")));
            userContext.setAuthType(properties.getProperty("automation.auth-type"));
            if(ObjectUtils.isNotEmpty(userContext.getAuthType())){
                userContext.setAuthEnabled(true);
            }
            if(log.isDebugEnabled()){
                log.debug("automation.auth-type: {}",properties.getProperty("automation.auth-type"));
            }
            userContext.setBasicAuth(BasicAuth.builder().username(properties.getProperty("automation.username")).password(properties.getProperty("automation.password")).build());
            UserContextManager.setUserContext(userContext);
        } catch (FileNotFoundException e) {
            log.error("Error processing config file ", e);
            throw new ApiAutomationException("Error processing config file ", e);
        } catch (IOException e) {
            log.error("Error processing config file ", e);
            throw new ApiAutomationException("Error processing config file ", e);
        }
    }

    public static void removeUserContext() {
        UserContextManager.unload();
    }
}
