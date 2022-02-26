package com.imooc.ad.dto;

import com.imooc.ad.constant.OpType;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

// 解析存储的模版数据
@Data
public class ParseTemplate {
    // 可以完整的表达json
    private String database;
    // // (table1, template.json中table1里面的数据)
    private Map<String, TableTemplate> tableTemplateMap = new HashMap<>();

    /**
     * public class Template {
     *     private String database;
     *     private List<JsonTable> tableList;
     * }
     */
    public static ParseTemplate parse(Template _template) {
        ParseTemplate template = new ParseTemplate();
        template.setDatabase(_template.getDatabase());

        /**
         * public class JsonTable {
         *
         *     private String tableName;
         *     private Integer level;
         *
         *     private List<Column> insert;
         *     private List<Column> update;
         *     private List<Column> delete;
         */
        for (JsonTable table : _template.getTableList()) {

            String name = table.getTableName();
            Integer level = table.getLevel();

            // 针对其中一个table
            /**
             * public class TableTemplate {
             *
             *     private String tableName;
             *     private String level;
             *
             *     private Map<OpType, List<String>> opTypeFieldSetMap = new HashMap<>();
             *
             *      * 字段索引 -> 字段名
             *      * bin_log在监听数据的时候，不会列出具体的列的名字，
             *      * 只会给出列的索引值0123....
             *      private Map<Integer, String> posMap = new HashMap<>();
             *}
             */
            TableTemplate tableTemplate = new TableTemplate();
            tableTemplate.setTableName(name);
            tableTemplate.setLevel(level.toString());
            /**
             * {
             *       "tableName": "ad_plan",
             *       "level": 2,
             *       "insert": [
             *         {"column": "id"},
             *         {"column": "user_id"},
             *         {"column": "plan_status"},
             *         {"column": "start_date"},
             *         {"column": "end_date"}
             *       ],
             *       "update": [
             *         {"column": "id"},
             *         {"column": "user_id"},
             *         {"column": "plan_status"},
             *         {"column": "start_date"},
             *         {"column": "end_date"}
             *       ],
             *       "delete": [
             *         {"column": "id"}
             *       ]
             *     },
             *     {
             *       "tableName": "ad_unit",
             *       "level": 3,
             *       "insert": [....
             */

            // (table1, template.json中table1里面的数据)
            // ParseTemplate template = new ParseTemplate();
            template.tableTemplateMap.put(name, tableTemplate);


            // 遍历操作类型对应的列
            Map<OpType, List<String>> opTypeFieldSetMap = tableTemplate.getOpTypeFieldSetMap();

            // 如果是insert操作，那么在opTypeFieldSetMap执行ADD
            // 我们去遍历insert插入的所有列
            // 如果Optype = ADD不存在
            // 如果不存在那么就new 一个 arrayList，如果存在就返回原来的arraylist
            // 并对list添加一个列
            for (JsonTable.Column column : table.getInsert()) {
                getAndCreateIfNeed(
                        OpType.ADD,
                        opTypeFieldSetMap,
                        ArrayList::new
                ).add(column.getColumn());
            }
            //  如果是update操作，那么在opTypeFieldSetMap执行update
            for (JsonTable.Column column : table.getUpdate()) {
                getAndCreateIfNeed(
                        OpType.UPDATE,
                        opTypeFieldSetMap,
                        ArrayList::new
                ).add(column.getColumn());
            }
            // 如果是delete操作，那么在opTypeFieldSetMap执行delete
            for (JsonTable.Column column : table.getDelete()) {
                getAndCreateIfNeed(
                        OpType.DELETE,
                        opTypeFieldSetMap,
                        ArrayList::new
                ).add(column.getColumn());
            }
        }
        return template;
    }

    // 返回R类型
    private static <T, R> R getAndCreateIfNeed(T key, Map<T, R> map,
                                               Supplier<R> factory) {
        return map.computeIfAbsent(key, k -> factory.get());
    }
}
