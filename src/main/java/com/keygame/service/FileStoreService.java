package com.keygame.service;

import com.keygame.dto.request.AttachAllFileDto;
import com.keygame.dto.request.MovePositionFileDto;
import com.keygame.dto.request.UpdatePositionFileDto;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStoreService {


  Long uploadFile(MultipartFile multipartFile, Integer position);

  Resource loadResourceFile(Long id);

  Resource loadResourceFileByName(String type, String name);

  Boolean deleteFile(Long id);

  boolean attachProduct(AttachAllFileDto attachAllFileDto);

  boolean movePosition(MovePositionFileDto movePositionFileDto);

  boolean updatePosition(UpdatePositionFileDto updatePositionFileDto);
}
