package com.project.foodjet.ui;

import com.project.foodjet.entity.Customer;
import com.project.foodjet.service.CustomerService;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import jakarta.annotation.security.RolesAllowed;
import org.vaadin.reports.PrintPreviewReport;

@Route("report")
@RolesAllowed("ADMIN")
public class ReportView extends VerticalLayout {
    public ReportView(CustomerService customerService) {
        var report = new PrintPreviewReport<>(Customer.class, "id", "name", "email", "phone", "address");
        report.getReportBuilder().setTitle("Customers");
        report.setItems(customerService.findAll());

        StreamResource pdf = report.getStreamResource("customers.pdf", customerService::findAll, PrintPreviewReport.Format.PDF);
        StreamResource csv =report.getStreamResource("customers.csv", customerService::findAll, PrintPreviewReport.Format.CSV);
        add(
                new HorizontalLayout(
                        new Anchor(pdf, "PDF"),
                        new Anchor(csv, "CSV")
                ),
                report
        );
    }
}
