package utils.conf;

import java.util.Properties;

/**
 * @author trafalgar
 *
 * offer function to get info of config
 */
public final class ConfigHelper {

    private static final Properties CONFIG_PROPS = PropsUtil.loadProps(ConfigConstant.CONFIG_FILE);

    public static String getCalendarPath() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.CALENDAR_PATH);
    }

    public static String getXMLPath() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.XML_PATH);
    }


}
