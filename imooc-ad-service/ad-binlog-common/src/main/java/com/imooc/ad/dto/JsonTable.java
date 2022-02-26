package com.imooc.ad.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonTable {

    private String tableName;
    private Integer level;

    /**
         "insert": [
         {"column": "id"},
         {"column": "user_id"},
         {"column": "plan_status"},
         {"column": "start_date"},
         {"column": "end_date"}
         ],
     */

    private List<Column> insert;
    private List<Column> update;
    private List<Column> delete;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Column {

        private String column;
    }
}
