package com.pritesh.tvshowscheduler.Data;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.PrimaryKey;

import static java.sql.Types.INTEGER;

/**
 * Created by prittesh on 14/12/16.
 */

public class Columns {
    @DataType(DataType.Type.INTEGER)
    @PrimaryKey
    @AutoIncrement
    public static final String _ID = "_id";

    @DataType(DataType.Type.TEXT)
    public static final String TITLE="title";

    @DataType(DataType.Type.TEXT)
    public static final String URL="url";

    @DataType(DataType.Type.INTEGER)
    public static final String TIME="time";


}