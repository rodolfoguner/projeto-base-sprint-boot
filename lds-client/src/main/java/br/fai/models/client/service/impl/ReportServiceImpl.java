package br.fai.models.client.service.impl;

import br.fai.models.client.service.ReportService;
import br.fai.models.client.service.UserService;
import br.fai.models.entities.UserModel;
import net.sf.jasperreports.engine.JasperReport;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.List;

public class ReportServiceImpl implements ReportService {

    @Autowired
    private UserService<UserModel> userService;

    @Override
    public String generateAndGetPdfFilePath() {

        String home = System.getProperty("user.home");
        String basePath = home
                + File.separator
                + "Documents"
                + File.separator
                + "fai"
                + File.separator
                + "reports"
                + File.separator;

        List<UserModel> users = userService.find();

        JasperReport jasperReport;

        return null;
    }

    @Override
    public String generateFilePath() {
        return null;
    }
}
