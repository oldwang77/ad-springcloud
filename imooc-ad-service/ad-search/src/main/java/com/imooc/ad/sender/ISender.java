package com.imooc.ad.sender;

import com.imooc.ad.dto.MySqlRowData;

// 增量数据投递
public interface ISender {
    void sender(MySqlRowData rowData);
}
