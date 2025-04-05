package com.keygame.service.impl;

import com.keygame.common.ConfigConstant;
import com.keygame.config.exceptionhandler.BusinessException;
import com.keygame.dto.request.AttachAllFileDto;
import com.keygame.dto.request.AttachFileDto;
import com.keygame.dto.request.MovePositionFileDto;
import com.keygame.dto.request.UpdatePositionFileDto;
import com.keygame.entity.FileStore;
import com.keygame.entity.Product;
import com.keygame.repository.FileStoreRepository;
import com.keygame.service.FileStoreService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static com.keygame.common.ConfigConstant.PRODUCT_IMAGE;
import static com.keygame.common.ErrorConstant.*;

@Service
@Transactional
@Slf4j
public class FileStoreServiceImpl implements FileStoreService {

    @Value("${public.folder.image.region}")
    String publicFolderImageRegion;

    @Value("${public.folder.image.product}")
    String publicFolderImageProduct;

    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    FileStoreRepository fileStoreRepository;


    @Override
    public Resource loadResourceFile(Long id) {
        try {
            Optional<FileStore> fileStore = fileStoreRepository.findById(id);
            if (fileStore.isEmpty()) {
                throw new BusinessException(FILE_NOT_FOUND);
            }
            Path file = new File(fileStore.get().getPath()).toPath();
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new BusinessException(FILE_CAN_NOT_READ);
            }
        } catch (MalformedURLException e) {
            throw new BusinessException(FILE_ERROR);
        }
    }

    @Override
    public Resource loadResourceFileByName(String type, String name) {
        try {
            Path file;
            if (PRODUCT_IMAGE.equalsIgnoreCase(type)) {
                file = Paths.get(publicFolderImageProduct, name);
            } else if (ConfigConstant.REGION_IMAGE.equalsIgnoreCase(type)) {
                file = Paths.get(publicFolderImageRegion, name);
            } else {
                throw new BusinessException(FILE_CAN_NOT_READ);
            }
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new BusinessException(FILE_CAN_NOT_READ);
            }
        } catch (MalformedURLException e) {
            throw new BusinessException(FILE_ERROR);
        }
    }

    @Override
    public Long uploadFile(MultipartFile multipartFile, Integer position) {
        File folderRoot = new File(publicFolderImageProduct);
        if (!folderRoot.exists()) {
            folderRoot.mkdirs();
        }

        try {
            File file = new File(folderRoot, UUID.randomUUID() + multipartFile.getOriginalFilename());
            multipartFile.transferTo(file);
            FileStore fileStore = new FileStore();
            fileStore.setName(PRODUCT_IMAGE + "/" + file.getName());
            fileStore.setPath(file.getPath());
            fileStore.setPosition(position);
            fileStore.setCreatedAt(new Date());
            fileStoreRepository.save(fileStore);

            return fileStore.getId();
        } catch (IOException e) {
            log.error(String.valueOf(e));
            throw new BusinessException(UPLOAD_FILE_ERROR);
        }
    }

    @Transactional
    @Override
    public Boolean deleteFile(Long id) {
        Optional<FileStore> fileStore = fileStoreRepository.findById(id);
        if (fileStore.isPresent() && fileStore.get().getProductId() != null && fileStore.get().getTicketId() != null) {
            File file = new File(fileStore.get().getPath());
            file.deleteOnExit();
            fileStoreRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean attachProduct(AttachAllFileDto attachAllFileDto) {
        for (AttachFileDto file : attachAllFileDto.getAttachFileDtos()) {
            FileStore fileStore = fileStoreRepository.findById(file.getId()).orElseThrow(() -> new BusinessException(FILE_NOT_FOUND));
            fileStore.setProductId(attachAllFileDto.getProductId());
            fileStoreRepository.save(fileStore);
        }
        return true;
    }

    @Transactional
    @Override
    public boolean movePosition(MovePositionFileDto movePositionFileDto) {
        Optional<FileStore> optionalFileStore1 = fileStoreRepository.findById(movePositionFileDto.getFileId1());
        if (optionalFileStore1.isEmpty())
            throw new BusinessException(FILE_NOT_FOUND + movePositionFileDto.getFileId1());
        Optional<FileStore> optionalFileStore2 = fileStoreRepository.findById(movePositionFileDto.getFileId2());
        if (optionalFileStore2.isEmpty())
            throw new BusinessException(FILE_NOT_FOUND + movePositionFileDto.getFileId2());
        Integer position1 = optionalFileStore1.get().getPosition();
        FileStore fileStore1 = optionalFileStore1.get();
        FileStore fileStore2 = optionalFileStore2.get();
        fileStore1.setPosition(fileStore2.getPosition());
        fileStore2.setPosition(position1);
        fileStoreRepository.save(fileStore1);
        fileStoreRepository.save(fileStore2);

        return true;
    }

    @Override
    public boolean updatePosition(UpdatePositionFileDto updatePositionFileDto) {
        Optional<FileStore> optionalFileStore = fileStoreRepository.findById(updatePositionFileDto.getFileId());
        if (optionalFileStore.isEmpty())
            throw new BusinessException(FILE_NOT_FOUND);
        FileStore fileStore = optionalFileStore.get();
        fileStore.setPosition(updatePositionFileDto.getPosition());
        fileStoreRepository.save(fileStore);
        return true;
    }
}