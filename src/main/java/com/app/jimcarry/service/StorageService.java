package com.app.jimcarry.service;

import com.app.jimcarry.aspect.annotation.LogStatus;
import com.app.jimcarry.domain.dao.ReviewDAO;
import com.app.jimcarry.domain.dao.StorageDAO;
import com.app.jimcarry.domain.dao.StorageFileDAO;
import com.app.jimcarry.domain.dto.PageDTO;
import com.app.jimcarry.domain.dto.SearchDTO;
import com.app.jimcarry.domain.dto.StorageDTO;
import com.app.jimcarry.domain.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StorageService {
    private final StorageDAO storageDAO;
    private final StorageFileDAO storageFileDAO;
    private final ReviewDAO reviewDAO;

    //    추가
    /*@LogStatus
    @Transactional(rollbackFor = Exception.class)
    public void register(StorageDTO storageDTO) {
        storageDAO.save(storageDTO);
    }*/

    //    추가
    /*파일 저장*/
    @Transactional(rollbackFor = Exception.class)
    @LogStatus
    public void registerStorage(StorageDTO storageDTO) {
        StorageVO newStorage = storageDTO.createVO();
//        storageFileDAO.deleteById(reviewId);
        storageDAO.save(newStorage);
        storageDTO.getFiles().stream().map(file -> new StorageFileVO().create(file, newStorage.getStorageId()))
                .forEach(file -> {file.setFilePath(getPath()); storageFileDAO.save(file);});
    }


    //    현재 날짜 경로 구하기
    private String getPath() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
    }

    //    조회
    @LogStatus
    public StorageVO getStorage(Long storageId) {
        return storageDAO.findById(storageId);
    }
    public StorageDTO getStorageById(Long storageId) {

        return storageDAO.findStorageById(storageId);
    }

    /**
     * 전체조회
     *
     * @param pageDTO 화면에서 받아온 페이징처리 정보, Criteria 포함
     * */
    @LogStatus
    public List<StorageVO> getList(PageDTO pageDTO) {
        return storageDAO.findAll(pageDTO);
    }


    /**
     * 검색조건 (Criteria 포함)
     *
     * @param pageDTO 화면에서 받아온 페이징처리 정보, Criteria, SearchDTO 포함
     * */
    @LogStatus
    public List<StorageVO> getListBy(PageDTO pageDTO) {
        log.info("★★★★★★★★★★" + pageDTO);
        log.info("★★★★★★★★★★" + storageDAO.findBy(pageDTO));
        return storageDAO.findBy(pageDTO);
    }

    //    전체개수 조회
    @LogStatus
    public int getTotal() {
        return storageDAO.findTotal();
    }

    /**
     * 조건에 맞는 전체개수 조회
     *
     * @param searchDTO Controller 에서 설정한 검색조건
     * */
    @LogStatus
    public int getTotalBy(SearchDTO searchDTO) {
        return storageDAO.findTotalBy(searchDTO);
    }

    //    수정
    public void setStorage(StorageVO storageVO){ storageDAO.updateBy(storageVO);}
    public void modify(StorageVO storageVO){ storageDAO.setStorage(storageVO);}

    //    삭제
    public void removeStorage(Long storageId) {
        storageDAO.deleteById(storageId);
    }


    //    DTO 창고 전체 조회
    /* Storage DTO 전체 조회*/
    @LogStatus
    public List<StorageDTO> getStorageList(PageDTO pageDTO) {
        return storageDAO.findStorageAll(pageDTO);
    }

    //    검색에 맞는 전체개수 조회
    public int getTotalDTOBy(SearchDTO searchDTO){
        return storageDAO.findTotalBy(searchDTO);
    }

    //    DTO 창고 조회
    public List<StorageDTO> getStorageBy(Long storageId){
        return storageDAO.findStorageDTOBy(storageId);
    }

    /* DTO 창고 조건 조회*/
    public List<StorageDTO> getStorageDTOBy(PageDTO pageDTO){
        List<StorageDTO> storageList = storageDAO.findStorageDTOListBy(pageDTO);
        storageList.forEach(storage ->
            storage.setReviewCount(reviewDAO.findByStorageId(storage.getStorageId()).size())
        );
        return storageList;
    }

    /* DTO 메인 신규창고 조회*/
    public List<StorageDTO> getStorageDTO(){
        List<StorageDTO> storageLists = storageDAO.findStorageDTOList();
        storageLists.forEach(storage -> storage.setReviewCount(reviewDAO.findByStorageId(storage.getStorageId()).size()));
        return storageLists;
    }

    /* DTO 메인 신규창고 조회*/
    public List<StorageDTO> getStorage(){
        List<StorageDTO> reviews = storageDAO.findStorageDTOLists();
        reviews.forEach(storage -> storage.setReviewCount(reviewDAO.findByStorageId(storage.getStorageId()).size()));
        return reviews;
    }
}
