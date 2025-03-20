package cn.cotenite.leaf.id;

import cn.cotenite.leaf.common.Result;

public interface IDGen {
    Result get(String key);
    boolean init();
}
