package com.imooc.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitKeywordRequest {

    // 允许批量创建
    private List<UnitKeyword> unitKeywords;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UnitKeyword {
        // 需要限制的推广单元
        private Long unitId;
        // keyword推广单元需要限制的关键字
        private String keyword;
    }
}
