package com.imooc.ad.constant;

import com.github.shyiko.mysql.binlog.event.EventType;

public enum OpType {

    ADD,
    UPDATE,
    DELETE,
    OTHER;

    // MySql5.7版本和mysql8.0版本关于eventType进行了修改
    // 我们需要根据自己mysql版本，选择合适的eventType
//     public static OpType to(EventType eventType) {
//
//         switch (eventType) {
//             case WRITE_ROWS:
//                 return ADD;
//             case UPDATE_ROWS:
//                 return UPDATE;
//             case DELETE_ROWS:
//                 return DELETE;
//
//             default:
//                 return OTHER;
//         }
//     }

    public static OpType to(EventType eventType) {

        switch (eventType) {
            case EXT_WRITE_ROWS:
                return ADD;
            case EXT_UPDATE_ROWS:
                return UPDATE;
            case EXT_DELETE_ROWS:
                return DELETE;
            default:
                return OTHER;
        }
    }
}
