package com.imooc.ad.dto;

import com.imooc.ad.constant.OpType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MySqlRowData {
    // 我们这里只有一个数据库，因此不需要定义

    private String tableName;

    private String level;

    // opType是我们从eventType转换得到
    private OpType opType;

    private List<Map<String, String>> fieldValueMap = new ArrayList<>();

    //

    /**
     * 我们传递的数据格式：
     * WriteRowsEventData{
     * tableId=85, includedColumns={0, 1, 2},
     * rows=[10, 10, 宝马]}
     *
     * tableName: ad_unit_keyword
     * level: 3
     * optype : add
     * fileValueMap: [(id,10),(unit_id,10),(keyword,20)]
     */
}
