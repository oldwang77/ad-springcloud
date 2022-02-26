package com.imooc.ad.sender;

import com.imooc.ad.dto.MySqlRowData;

public interface ISender {

    void sender(MySqlRowData rowData);
}
