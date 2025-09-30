package com.example.demo.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.models.Attendance;
import com.example.demo.models.Notification;
import com.example.demo.models.Parent;
import com.example.demo.models.Student;
import com.example.demo.repositories.AttendanceRepository;
import com.example.demo.repositories.NotificationRepository;
import com.example.demo.repositories.ParentRepository;

@Controller
public class ParentController {

    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;
    
    @Autowired
    private NotificationRepository notificationRepository;

    //parent dashboard
    @GetMapping("/parent/dashboard")
    public String showParentDashboard(Model model, @RequestParam int parentId) {
        Parent parent = parentRepository.findById(parentId)
                .orElseThrow(() -> new RuntimeException("Parent not found"));

        //fetch unread notifications for the parent
        List<Notification> notifications = notificationRepository.findByParentIdAndReadFalse(parentId);
        if (notifications == null) {
            notifications = List.of();//ensure list is not null
        }

        //add parent & notif to model
        model.addAttribute("parent", parent);
        model.addAttribute("notifications", notifications);
        return "parent_dashboard";//return parent dashboard
    }

    @GetMapping("/parent/notifications")
    public String showNotifications(@RequestParam("parentId") int parentId, Model model) {
        //find parent by id
        Parent parent = parentRepository.findById(parentId)
                .orElseThrow(() -> new RuntimeException("Parent not found"));

        //add notifs to model
        model.addAttribute("parent", parent);
        model.addAttribute("notifications", parent.getNotifications());
        return "parent-notifications";
    }
    
    @PostMapping("/parent/mark-notifications-read")
    public String markNotificationsAsRead(@RequestParam int parentId) {
        notificationRepository.markNotificationsAsReadByParentId(parentId);
        return "redirect:/parent/dashboard?parentId=" + parentId; // Redirect back to dashboard
    }

    //view children attendance
    @GetMapping("/parent/children/attendance")
    public String showChildrenAttendance(@RequestParam("parentId") int parentId, Model model) {
        Parent parent = parentRepository.findById(parentId)
                .orElseThrow(() -> new RuntimeException("Parent not found"));

        //get all children of the parent
        List<Student> children = parent.getChildrenAsParent1();
        children.addAll(parent.getChildrenAsParent2());

        //hold attendance records for each child
        Map<Long, List<Attendance>> attendanceMap = new HashMap<>();

        //get attendance for each child
        for (Student child : children) {
            List<Attendance> attendanceRecords = attendanceRepository.findByStudent(child);
            attendanceMap.put(child.getId(), attendanceRecords); // Store attendance by child ID
        }

        //add data to model
        model.addAttribute("parent", parent);
        model.addAttribute("children", children);
        model.addAttribute("attendanceMap", attendanceMap); //pass attendance map to model
        return "parent_children_attendance"; //return parent children attendance view
    }   
}