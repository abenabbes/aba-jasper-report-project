/**
 * 
 */
package aba.perso.project.jasper.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;

import aba.perso.project.jasper.commons.ProjectResourcesLoader;
import aba.perso.project.jasper.vo.PersonVo;

/**
 * @author ali
 *
 */
public class TestGenerationRapport {

	public static JasperDesign jasperDesign;
    public static JasperPrint jasperPrint;
    public static JasperReport jasperReport;
    public static String reportTemplateUrl = "jrxml/person-template.jrxml";
    
	/**
	 * @param args
	 * @throws JRException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		try {			
			
			//Application Context Spring
			ApplicationContext appContext =
			    	   new ClassPathXmlApplicationContext(new String[] {"spring/beansContext.xml"});
			
			//Rechargement de bean
			ProjectResourcesLoader loadBean = (ProjectResourcesLoader) appContext.getBean("projectResourcesLoader");
			
			//Rechargement de resource 
			Resource resource = loadBean.getResource(reportTemplateUrl);
			
			 InputStream is = resource.getInputStream();
			 
			// Recuperation des resources des fichiers
//			InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(reportTemplateUrl);
			// Rechargement des resources
			jasperDesign = JRXmlLoader.load(is);
			// Compilation des resources
			jasperReport = JasperCompileManager.compileReport(jasperDesign);
			// Emplacement des fichier PDF générer
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			OutputStream outputfile = new FileOutputStream(new File("c:/temp/person.pdf"));
	        // fill the ready report with data and parameter
			jasperPrint = JasperFillManager.fillReport(jasperReport, getParameters(), new JRBeanCollectionDataSource(findReportData()));
	        
			//coding for PDF
	        JRPdfExporter exporterPdf = new JRPdfExporter();
	        exporterPdf.setParameter(JRPdfExporterParameter.JASPER_PRINT, jasperPrint);
	        exporterPdf.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, output);
	        exporterPdf.exportReport();
	        outputfile.write(output.toByteArray());
        
		} catch (JRException e) {
	        e.printStackTrace();
	    }
        
	}
	
    private static Collection<PersonVo> findReportData() {
        //declare a list of object
        List<PersonVo> data = new LinkedList<PersonVo>();
        PersonVo p1 = new PersonVo();
        p1.setFirstName("John");
        p1.setSurname("Smith");
        p1.setAge(Integer.valueOf(5));
        data.add(p1);
        return data;
    }
    
    private static Map<String, Object> getParameters() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("footerText", "Just to demonstrate how to pass parameters to report");
        return params;
    } 
    

}
