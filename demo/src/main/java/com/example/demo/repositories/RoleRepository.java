package com.example.demo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    
    // Find role by role name (e.g., "ADMIN", "TEACHER", "STUDENT", "PARENT")
    Optional<Role> findByRoleName(String roleName);
    
    // Find role by role name ignoring case
    Optional<Role> findByRoleNameIgnoreCase(String roleName);
    
    // Check if role exists by name
    boolean existsByRoleName(String roleName);
    
    // Check if role exists by name ignoring case
    boolean existsByRoleNameIgnoreCase(String roleName);
    
    // Find all roles containing a specific string (useful for search)
    List<Role> findByRoleNameContainingIgnoreCase(String roleName);
    
    // Get all roles ordered by role name
    List<Role> findAllByOrderByRoleNameAsc();
    
    // Custom query to count users with a specific role
    @Query("SELECT COUNT(u) FROM UserEntity u WHERE u.role.id = :roleId")
    long countUsersByRoleId(@Param("roleId") Long roleId);
    
    // Custom query to count users with a specific role name
    @Query("SELECT COUNT(u) FROM UserEntity u WHERE u.role.roleName = :roleName")
    long countUsersByRoleName(@Param("roleName") String roleName);
    
    // Find roles that have active users - FIXED: Changed u.active to u.isActive
    @Query("SELECT DISTINCT u.role FROM UserEntity u WHERE u.isActive = true")
    List<Role> findRolesWithActiveUsers();
    
    // Custom query to get role statistics
    @Query("SELECT r.roleName, COUNT(u) FROM Role r LEFT JOIN UserEntity u ON r.id = u.role.id GROUP BY r.id, r.roleName")
    List<Object[]> getRoleStatistics();
    
    // Find default roles (assuming you might have a default field)
    // List<Role> findByIsDefaultTrue();
    
    // Predefined methods for common roles (uncomment if you want direct access)
    
    @Query("SELECT r FROM Role r WHERE r.roleName = 'ADMIN'")
    Optional<Role> findAdminRole();
    
    @Query("SELECT r FROM Role r WHERE r.roleName = 'TEACHER'")
    Optional<Role> findTeacherRole();
    
    @Query("SELECT r FROM Role r WHERE r.roleName = 'STUDENT'")
    Optional<Role> findStudentRole();
    
    @Query("SELECT r FROM Role r WHERE r.roleName = 'PARENT'")
    Optional<Role> findParentRole();
}