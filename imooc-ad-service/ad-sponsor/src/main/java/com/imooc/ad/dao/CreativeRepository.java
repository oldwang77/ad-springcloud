package com.imooc.ad.dao;

import com.imooc.ad.entity.Creative;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 如果没有定义，一些基本的方法都可以使用了save、update等等
 */
public interface CreativeRepository extends JpaRepository<Creative, Long> {
}
