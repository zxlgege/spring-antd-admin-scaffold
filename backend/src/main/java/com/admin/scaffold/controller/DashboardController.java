package com.admin.scaffold.controller;

import com.admin.scaffold.common.Result;
import com.admin.scaffold.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
@Tag(name = "Dashboard", description = "Dashboard statistics APIs")
public class DashboardController {

    private final UserService userService;

    @GetMapping("/stats")
    @Operation(summary = "Get dashboard statistics")
    public Result<Map<String, Object>> stats() {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("totalUsers",   userService.countUsers());
        data.put("activeUsers",  userService.countUsersByStatus(1));
        data.put("disabledUsers",userService.countUsersByStatus(0));
        // Simulated trend data for the last 7 days
        data.put("userTrend", List.of(
            Map.of("date", "06-18", "count", 1),
            Map.of("date", "06-19", "count", 1),
            Map.of("date", "06-20", "count", 0),
            Map.of("date", "06-21", "count", 1),
            Map.of("date", "06-22", "count", 0),
            Map.of("date", "06-23", "count", 0),
            Map.of("date", "06-24", "count", 3)
        ));
        return Result.success(data);
    }
}
