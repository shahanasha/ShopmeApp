package com.shopme.admin.report;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.shopme.admin.report.export.ReportExcelExporter;
import com.shopme.admin.setting.SettingService;
import com.shopme.admin.user.export.UserExcelExporter;
import com.shopme.common.entity.User;
import com.shopme.common.entity.setting.Setting;

@Controller
public class ReportController {
	
	@Autowired private SettingService settingService;
	@Autowired private OrderDetailReportService reportService;
	//@Autowired private MasterOrderReportService masterRepo;
	
	@GetMapping("/reports")
	public String viewSalesReportHome(HttpServletRequest request) {
		loadCurrencySetting(request);
		return "reports/reports";
	}
	
	private void loadCurrencySetting(HttpServletRequest request) {
		List<Setting> currencySettings = settingService.getCurrencySettings();
		
		for (Setting setting : currencySettings) {
			request.setAttribute(setting.getKey(), setting.getValue());
		}	
	}	
	

	@GetMapping("/reports/export/excel")
	public void exportToExcel(HttpServletResponse response) throws IOException {
		
		
		List<ReportItem> listreport =reportService.getReportDataLast28Days(ReportType.PRODUCT );
		
		ReportExcelExporter exporter = new ReportExcelExporter();
		exporter.export(listreport, response);
	}
}
