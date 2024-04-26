package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ExamenHTML {

	private final String fileContent;
	private final String pageTemplate;
	private StringBuilder examenTemplate;
	private final String examenTemplateEnd;

	public ExamenHTML(String templatePath) throws IOException {
		fileContent = Files.readString(Paths.get(templatePath));
		pageTemplate = getCopyFromTemplate();
		examenTemplateEnd = getEndTemplate();
		examenTemplate = removeCopyAndEndOfTemplate();
	}

	private String getCopyFromTemplate() {
		StringBuilder examenTemplate = new StringBuilder();
		int indent = 0;
		int start = fileContent.indexOf("<div class=\"page\">");
		if (start == -1) {
			return null;
		}
		for (int i = start; i < fileContent.length(); i++) {
			if (fileContent.charAt(i) == '<') {
				if (fileContent.charAt(i + 1) == '/') {
					indent--;
				} else {
					indent++;
				}
			}
			examenTemplate.append(fileContent.charAt(i));
			if (indent == 0) {
				examenTemplate.append("/div>");
				break;
			}
		}
		return examenTemplate.toString();
	}

	private StringBuilder removeCopyAndEndOfTemplate() {
		StringBuilder examenTemplate = new StringBuilder(fileContent);
		int start = fileContent.indexOf("<div class=\"page\">");
		if (start == -1) {
			return null;
		}
		examenTemplate.delete(start, examenTemplate.length());
		return examenTemplate;
	}

	private String getEndTemplate() {
		int start = fileContent.indexOf("<div class=\"page\">");
		int end = pageTemplate.length();
		return fileContent.substring(start + end);
	}

	private String getPageForStudent(String anoCode, int placeNumber) {
		return new String(pageTemplate).replace("{{anocode}}", anoCode).replace("{{placeNumber}}",
				String.valueOf(placeNumber));
	}

	public String getExamenTemplate() {
		return examenTemplate.append(examenTemplateEnd).toString();
	}

	public void addStudentPage(String anoCode, int placeNumber) {
		examenTemplate.append(getPageForStudent(anoCode, placeNumber));
	}

	public void saveExamToHtml(String string) {
		try {
			Files.write(Paths.get(string), getExamenTemplate().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
