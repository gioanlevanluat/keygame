package com.keygame.service.impl;

import com.keygame.repository.FileStoreRepository;
import com.keygame.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService {


    @Autowired
    FileStoreRepository fileStoreRepository;

}
