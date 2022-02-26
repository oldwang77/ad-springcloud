package com.imooc.ad.dto;

import com.imooc.ad.constant.OpType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 针对的具体的一张table
// template.json
    // table1
    // table2
    // table3
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableTemplate {

    private String tableName;
    private String level;

    private Map<OpType, List<String>> opTypeFieldSetMap = new HashMap<>();

    /**
     * 字段索引 -> 字段名
     * bin_log在监听数据的时候，不会列出具体的列的名字，
     * 只会给出列的索引值0123....
     * */
    private Map<Integer, String> posMap = new HashMap<>();
}
