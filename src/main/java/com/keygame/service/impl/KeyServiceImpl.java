package com.keygame.service.impl;

import com.keygame.dto.request.AddKeyDto;
import com.keygame.dto.request.SearchKeyDto;
import com.keygame.dto.response.KeyInfoDto;
import com.keygame.dto.response.ProductSimpleDto;
import com.keygame.entity.InputVoucher;
import com.keygame.entity.Inventory;
import com.keygame.entity.Key;
import com.keygame.repository.InventoryRepository;
import com.keygame.repository.KeyRepository;
import com.keygame.service.KeyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class KeyServiceImpl implements KeyService {
    @Autowired
    private KeyRepository keyRepository;

    @Autowired
    InventoryRepository inventoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    @Override
    public boolean save(AddKeyDto addKeyDto) {
        List<Key> keys = new ArrayList<>();
        addKeyDto.getCodes().stream().forEach(
                item -> {
                    Key key = new Key();
                    key.setIsOrdered(false);
                    key.setCreatedAt(new Date());
                    key.setCode(item);
                    key.setBasePrice(addKeyDto.getBasePrice().divide(BigDecimal.valueOf(addKeyDto.getCodes().size()), 4, RoundingMode.CEILING));
                    key.setProductId(addKeyDto.getProductId());
                    key.setSupplier(addKeyDto.getSupplier());
                    keys.add(key);
                }
        );
        keyRepository.saveAll(keys);
        Optional<Inventory> optionalInventory = inventoryRepository.findById(addKeyDto.getProductId());
        Inventory inventory;
        if (optionalInventory.isPresent()) {
            inventory = optionalInventory.get();
            inventory.setInputQuantity(inventory.getInputQuantity() + keys.size());
        } else {
            inventory = new Inventory();
            inventory.setSaleQuantity(0);
            inventory.setInStockQuantity(0);
            inventory.setCreatedAt(new Date());
            inventory.setInputQuantity(keys.size());
            inventory.setId(addKeyDto.getProductId());
        }
        inventoryRepository.save(inventory);

        InputVoucher inputVoucher = new InputVoucher();
        inputVoucher.setInputQuantity(keys.size());
        inputVoucher.setBasePrice(addKeyDto.getBasePrice());
        inputVoucher.setCreatedAt(new Date());

        return true;
    }

    @Transactional
    @Override
    public boolean delete(Long id) {
        return keyRepository.softDelete(id) > 0;
    }

    @Override
    public Page<KeyInfoDto> findAll(SearchKeyDto searchKeyDto) {
        if (searchKeyDto.getPageNumber() < 1) {
            searchKeyDto.setPageNumber(1);
        }
        if (searchKeyDto.getPageSize() < 1 || searchKeyDto.getPageSize() > 100) {
            searchKeyDto.setPageSize(50);
        }
        Pageable pageable = PageRequest.of(searchKeyDto.getPageNumber() - 1, searchKeyDto.getPageSize());
        Page<Key> keys = keyRepository.searchKey(searchKeyDto, pageable);
        return new PageImpl<>(keys.getContent().stream().map(entity -> modelMapper.map(entity, KeyInfoDto.class)).collect(Collectors.toList()),
                pageable, keys.getTotalElements());
    }
}
