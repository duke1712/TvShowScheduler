package com.pritesh.tvshowscheduler.data;

import android.renderscript.Element;

import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.PrimaryKey;


/**
 * Created by prittesh on 26/12/16.
 */

public class ChannelColumn {
    @DataType(DataType.Type.TEXT)
    @PrimaryKey
    public static final String _ID="_id";

    @DataType(DataType.Type.TEXT)
    public static final String NAME="name";

    @DataType(DataType.Type.TEXT)
    public static final String URL="url";

    @DataType(DataType.Type.TEXT)
    public static final String CATEGORY="category";


}
