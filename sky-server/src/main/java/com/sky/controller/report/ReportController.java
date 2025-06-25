package com.sky.controller.report;


import com.sky.mapper.UserMapper;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.service.UserService;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/admin/report")
@Api(tags = "数据统计接口")
@Slf4j
public class ReportController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @GetMapping("/turnoverStatistics")
    @ApiOperation("营业额统计")
    public Result turnoverStatistics(LocalDate begin, LocalDate end) {
        log.info("营业额统计: begin {}, end {}", begin, end);
        TurnoverReportVO turnoverReportVO = orderService.turnoverStatistics(begin, end);
        return Result.success(turnoverReportVO);
    }

    @GetMapping("/userStatistics")
    @ApiOperation("用户统计")
    public Result userStatistics(LocalDate begin, LocalDate end) {
        log.info("用户统计: begin {}, end {}", begin, end);
        UserReportVO userReportVO = userService.countByMap(begin, end);
        return Result.success(userReportVO);
    }
}
