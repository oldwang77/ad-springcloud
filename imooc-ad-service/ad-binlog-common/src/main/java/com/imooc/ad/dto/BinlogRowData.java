package com.imooc.ad.dto;

import com.github.shyiko.mysql.binlog.event.EventType;
import lombok.Data;

import java.util.List;
import java.util.Map;

// 定义binlog的格式数据
@Data
public class BinlogRowData {

    private TableTemplate table;

    private EventType eventType;

    // 修改后的map<colName,colValue>
    private List<Map<String, String>> after;

    private List<Map<String, String>> before;
}
