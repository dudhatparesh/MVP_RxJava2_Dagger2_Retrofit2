package com.crossover.conferencemanagement.data;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by PareshDudhat on 10-03-2017.
 */

public class DatabaseConfig extends OrmLiteConfigUtil {
    public static void main(String arg[]) throws IOException, SQLException {
        writeConfigFile("write");
    }
}
