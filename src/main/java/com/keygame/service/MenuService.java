package com.keygame.service;

import com.keygame.dto.MenuDto;
import com.keygame.dto.TagDto;

import java.util.List;

public interface MenuService {

//    boolean save(MenuDto menuDto);
//
//    boolean delete(Long id);

    List<MenuDto> findAll();
}
