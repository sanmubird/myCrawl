package com.etoak.common.commonUtil;

import java.io.File;

public class FileUtil {

    public static final String CLASSPATH = new File(FileUtil.class.getResource("/").getPath()).getPath() + File.separatorChar;

}
