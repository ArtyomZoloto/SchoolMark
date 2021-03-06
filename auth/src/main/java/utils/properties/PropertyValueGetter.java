package utils.properties;

import org.apache.log4j.Logger;
import services.exceptions.ServicesAuthGetPropertyNotFoundException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyValueGetter {

    private static final Logger logger = Logger.getLogger(PropertyValueGetter.class);

    public String getPropertyByKey(String key)
            throws ServicesAuthGetPropertyNotFoundException {
        InputStream is = getClass().getResourceAsStream("/auth-module-resources.properties");
        Properties props = new Properties();
        try {
            props.load(is);
            is.close();
            String property = props.getProperty(key);
            if(property == null) {
                String errMessage = "parameter " + key + " not found into property file";
                logger.error(errMessage);
                throw new ServicesAuthGetPropertyNotFoundException(errMessage);
            }
            return property;
        } catch (IOException e) {
            logger.error(e);
            throw new ServicesAuthGetPropertyNotFoundException(e.getMessage());
        }
    }

}
