package com.orange.tavels.dao;


import com.orange.tavels.domain.FileManage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileManageDao extends JpaRepository<FileManage,String> {
}
