package com.maths.huim.models;

import java.util.ArrayList;
import java.util.List;

public class PathUtil {

    private List<String> path;
    private long util;

    public PathUtil(List<String> path, long util) {
        this.path = path;
        this.util = util;
    }

    public PathUtil() {
        this.path = new ArrayList<String>();
        this.util = 0;
    }

    public List<String> getPath() {
        return path;
    }

    public void setPath(List<String> path) {
        this.path = path;
    }

    public long getUtil() {
        return util;
    }

    public void setUtil(long util) {
        this.util = util;
    }

    @Override
    public String toString() {
        return "PathUtil{" +
                "path=" + path +
                ", util=" + util +
                '}';
    }
}
