package com.keygame.service.impl;

import com.keygame.dto.MenuDto;
import com.keygame.dto.TagDto;
import com.keygame.entity.Menu;
import com.keygame.entity.Tag;
import com.keygame.repository.MenuRepository;
import com.keygame.repository.TagRepository;
import com.keygame.service.MenuService;
import com.keygame.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private ModelMapper modelMapper;

    private static List<MenuDto> menuDtos;

//    @Override
//    public boolean save(TagDto tagDto) {
//        tagRepository.save(modelMapper.map(tagDto, Tag.class));
//        return true;
//    }
//
//    @Override
//    public boolean delete(Long id) {
//        return tagRepository.softDelete(id) > 0;
//    }

    @Override
    public List<MenuDto> findAll() {
        if (CollectionUtils.isEmpty(menuDtos)) {
            initMenu();
        }
        return menuDtos;
    }

    @Scheduled(cron = "0 0 * * * ?")
    public void scheduleMenu() {
        initMenu();
    }

    private void initMenu() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        simpleDateFormat.applyPattern("dd/MM/yyyy hh:mm:ss");
        log.error(simpleDateFormat.format(new Date()));
        List<Menu> menuAll = menuRepository.findAll();
        menuDtos = menuAll.stream().filter(menu -> menu.getMenuParent() == null)
                .map(menu -> modelMapper.map(menu, MenuDto.class))
                .collect(Collectors.toList());
        for (MenuDto menu : menuDtos) {
            addSubMenu(menu, menuAll);
        }
    }

    private void addSubMenu(MenuDto menuDto, List<Menu> menus) {
        List<MenuDto> subMenu = findSubMenu(menuDto.getId(), menus);
        subMenu.sort(Comparator.comparing(MenuDto::getIndex));
        menuDto.setChildren(subMenu);
        for (MenuDto menuDto1 : subMenu) {
            addSubMenu(menuDto1, menus);
        }
    }

    private List<MenuDto> findSubMenu(Long id, List<Menu> menus) {
        List<MenuDto> subMenuDto = new ArrayList<>();
        for (Menu menu: menus) {
            if (menu.getMenuParent() != null && menu.getMenuParent().equals(id)) {
                subMenuDto.add(modelMapper.map(menu, MenuDto.class));
            }
        }
        return subMenuDto;
    }
}
