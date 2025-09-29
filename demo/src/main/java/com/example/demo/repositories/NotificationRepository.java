package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.models.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {

    @Modifying
    @Transactional
    @Query("UPDATE Notification n SET n.isRead = true WHERE n.parent.id = :parentId AND n.isRead = false")
    void markNotificationsAsReadByParentId(@Param("ParentID") int parentId);

    @Query("SELECT n FROM Notification n WHERE n.parent.id = :parentId AND n.isRead = false")
    List<Notification> findByParentIdAndReadFalse(@Param("ParentID") int parentId);
}
