package com.example.demo.repositories;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Role;
import com.example.demo.models.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    
    // Find user by username (for login)
    Optional<UserEntity> findByUsername(String username);
    
    // Find user by email
    Optional<UserEntity> findByEmail(String email);
    
    // Find user by username or email (useful for login flexibility)
    Optional<UserEntity> findByUsernameOrEmail(String username, String email);
    
    // Find all active users
    List<UserEntity> findByActiveTrue();
    
    // Find all inactive users
    List<UserEntity> findByActiveFalse();
    
    // Find users by role
    List<UserEntity> findByRole(Role role);
    
    // Find active users by role
    List<UserEntity> findByRoleAndActiveTrue(Role role, boolean active);
    
    // Find users by first name and last name
    List<UserEntity> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
        String firstName, String lastName);
    
    // Check if username exists
    boolean existsByUsername(String username);
    
    // Check if email exists
    boolean existsByEmail(String email);
    
    // Custom query to find users by role name
    @Query("SELECT u FROM UserEntity u WHERE u.role.roleName = :roleName")
    List<UserEntity> findByRoleName(@Param("roleName") String roleName);
    
    // Custom query to find users by role name and active status
    @Query("SELECT u FROM UserEntity u WHERE u.role.roleName = :roleName AND u.active = :active")
    List<UserEntity> findByRoleNameAndActiveStatus(@Param("roleName") String roleName, @Param("active") boolean active);
    
    // Count users by role
    @Query("SELECT COUNT(u) FROM UserEntity u WHERE u.role = :role")
    long countByRole(@Param("role") Role role);
    
    // Get all teachers (assuming TEACHER role exists)
    @Query("SELECT u FROM UserEntity u WHERE u.role.roleName = 'TEACHER' AND u.active = true")
    List<UserEntity> findAllActiveTeachers();
    
    // Get all students (assuming STUDENT role exists)
    @Query("SELECT u FROM UserEntity u WHERE u.role.roleName = 'STUDENT' AND u.active = true")
    List<UserEntity> findAllActiveStudents();
    
    // Get all parents (assuming PARENT role exists)
    @Query("SELECT u FROM UserEntity u WHERE u.role.roleName = 'PARENT' AND u.active = true")
    List<UserEntity> findAllActiveParents();
}