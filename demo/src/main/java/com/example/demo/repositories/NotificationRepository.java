package com.example.demo.repositories;

import com.example.demo.models.Notification;
import com.example.demo.models.UserEntity;
import com.example.demo.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    // Find all notifications for a specific user
    List<Notification> findByRecipient(UserEntity recipient);

    // Find all notifications for a specific user ordered by creation date (newest first)
    List<Notification> findByRecipientOrderByCreatedAtDesc(UserEntity recipient);

    // Find unread notifications for a specific user
    List<Notification> findByRecipientAndIsReadFalse(UserEntity recipient);

    // Find unread notifications for a specific user ordered by date
    List<Notification> findByRecipientAndIsReadFalseOrderByCreatedAtDesc(UserEntity recipient);

    // Find read notifications for a specific user
    List<Notification> findByRecipientAndIsReadTrue(UserEntity recipient);

    // Find notifications by type
    List<Notification> findByType(String type);

    // Find notifications by type for a specific user
    List<Notification> findByRecipientAndType(UserEntity recipient, String type);

    // Find notifications by priority
    List<Notification> findByPriority(String priority);

    // Find notifications by priority for a specific user
    List<Notification> findByRecipientAndPriority(UserEntity recipient, String priority);

    // Find high priority unread notifications
    List<Notification> findByRecipientAndPriorityAndIsReadFalse(UserEntity recipient, String priority);

    // Find notifications within a date range
    List<Notification> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    // Find notifications for a user within a date range
    List<Notification> findByRecipientAndCreatedAtBetween(UserEntity recipient, LocalDateTime startDate, LocalDateTime endDate);

    // Find notifications by sender
    List<Notification> findBySender(UserEntity sender);

    // Find notifications sent by a user to a specific recipient
    List<Notification> findBySenderAndRecipient(UserEntity sender, UserEntity recipient);

    // Count unread notifications for a user
    long countByRecipientAndIsReadFalse(UserEntity recipient);

    // Count notifications by type for a user
    long countByRecipientAndType(UserEntity recipient, String type);

    // Count high priority unread notifications
    long countByRecipientAndPriorityAndIsReadFalse(UserEntity recipient, String priority);

    // Find notifications that have not been sent yet (for scheduled notifications)
    List<Notification> findBySentAtIsNull();

    // Find scheduled notifications that are ready to be sent
    @Query("SELECT n FROM Notification n WHERE n.sentAt IS NULL AND n.scheduledFor <= :currentTime")
    List<Notification> findScheduledNotificationsReadyToSend(@Param("currentTime") LocalDateTime currentTime);

    // Find notifications related to a specific reference (e.g., attendance record, grade, etc.)
    List<Notification> findByRelatedEntityIdAndRelatedEntityType(Long referenceId, String referenceType);

    // Find recent notifications (last N days) for a user
    @Query("SELECT n FROM Notification n WHERE n.recipient = :recipient AND n.createdAt >= :fromDate ORDER BY n.createdAt DESC")
    List<Notification> findRecentNotificationsByRecipient(@Param("recipient") UserEntity recipient, @Param("fromDate") LocalDateTime fromDate);

    // Mark notification as read
    @Modifying
    @Transactional
    @Query("UPDATE Notification n SET n.isRead = true, n.readAt = :readTime WHERE n.id = :id")
    int markAsRead(@Param("id") Long id, @Param("readTime") LocalDateTime readTime);

    // Mark all notifications as read for a user
    @Modifying
    @Transactional
    @Query("UPDATE Notification n SET n.isRead = true, n.readAt = :readTime WHERE n.recipient = :recipient AND n.isRead = false")
    int markAllAsReadForUser(@Param("recipient") UserEntity recipient, @Param("readTime") LocalDateTime readTime);

    // Mark notifications as sent
    @Modifying
    @Transactional
    @Query("UPDATE Notification n SET n.sentAt = :sentTime WHERE n.id = :id")
    int markAsSent(@Param("id") Long id, @Param("sentTime") LocalDateTime sentTime);

    // Find notifications for parents of a specific student
    @Query("SELECT n FROM Notification n WHERE n.recipient IN (SELECT p.user FROM Parent p JOIN p.children s WHERE s = :student)")
    List<Notification> findNotificationsForParentsOfStudent(@Param("student") Student student);

    // Find notifications sent by teachers to parents about a specific student
    @Query("SELECT n FROM Notification n WHERE n.sender IN (SELECT t.user FROM Teacher t) AND n.recipient IN (SELECT p.user FROM Parent p JOIN p.children s WHERE s = :student)")
    List<Notification> findTeacherNotificationsToParentsAboutStudent(@Param("student") Student student);

    // Find system notifications (no sender)
    List<Notification> findBySenderIsNull();

    // Find notifications by multiple types
    List<Notification> findByTypeIn(List<String> types);

    // Find notifications for multiple recipients
    List<Notification> findByRecipientIn(List<UserEntity> recipients);

    // Find urgent notifications that haven't been read
    @Query("SELECT n FROM Notification n WHERE n.priority = 'HIGH' AND n.isRead = false AND n.createdAt >= :fromDate")
    List<Notification> findUrgentUnreadNotifications(@Param("fromDate") LocalDateTime fromDate);

    // Get notification statistics for a user
    @Query("SELECT n.type, COUNT(n), SUM(CASE WHEN n.isRead = false THEN 1 ELSE 0 END) FROM Notification n WHERE n.recipient = :recipient GROUP BY n.type")
    List<Object[]> getNotificationStatsByUser(@Param("recipient") UserEntity recipient);

    // Find duplicate notifications (same content, recipient, type within timeframe)
    @Query("SELECT n FROM Notification n WHERE n.recipient = :recipient AND n.type = :type AND n.title = :title AND n.createdAt >= :fromTime")
    List<Notification> findPotentialDuplicates(@Param("recipient") UserEntity recipient,
                                               @Param("type") String type,
                                               @Param("title") String title,
                                               @Param("fromTime") LocalDateTime fromTime);

    // Check if user has any unread notifications
    boolean existsByRecipientAndIsReadFalse(UserEntity recipient);
}
