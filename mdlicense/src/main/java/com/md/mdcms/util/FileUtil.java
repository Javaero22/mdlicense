package com.md.mdcms.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.as400.access.AS400SecurityException;
import com.ibm.as400.access.IFSFile;
import com.ibm.as400.access.IFSFileInputStream;
import com.md.mdcms.backingbean.MimePropertiesBean;
import com.md.mdcms.backingbean.StartConfigurationBean;
import com.md.mdcms.base.ApplicationHelper;
import com.md.mdcms.base.IConstants;

public class FileUtil implements IConstants {

	private static final Log LOG = LogFactory.getLog(FileUtil.class);

	public static boolean createInitialApplProps() {
		try {
			File propertiesDir = new File(StartConfigurationBean.getInstance().getXmlfilepath());
			propertiesDir.mkdirs();
			File propertiesFile = new File(
					StartConfigurationBean.getInstance().getXmlfilepath() + MD_WORKFLOW_PROPERTIES);

			LOG.info(propertiesFile + " created on: " + propertiesFile.getAbsolutePath());

			propertiesFile.createNewFile();
			FileWriter fw = new FileWriter(propertiesFile);

			fw.write("#" + MD_WORKFLOW_PROPERTIES + " - read by mdWorkflowClient at user login");
			fw.write(System.getProperty("line.separator"));
			fw.write(
					"#false= do not show the time information; all=show the time information to all users, ren,mmorgan=show the time information to these users only");
			fw.write(System.getProperty("line.separator"));
			fw.write("showtime=false");
			fw.write(System.getProperty("line.separator"));
			fw.write("#example: xmlkeep_ren=222; keep 222 last xml's for this user");
			fw.write(System.getProperty("line.separator"));
			fw.write("xmlkeep_userid=20");
			fw.write(System.getProperty("line.separator"));
			fw.write(
					"#example: xmllog=REN,MMORGAN => log xml's for these users; xmllog=all => log xml's for all users");
			fw.write(System.getProperty("line.separator"));
			fw.write("xmllog= ");
			fw.write(System.getProperty("line.separator"));
			fw.write("xmllogddl= ");

			fw.flush();
			fw.close();
			return true;
		} catch (IOException e) {
			LOG.warn("unable to create " + MD_WORKFLOW_PROPERTIES + " file ", e);
			return false;
		}
	}

	// public static String uploadFileToAS400(UploadedFile localFile, IFSFile
	// ifsFile) {
	// try {
	// InputStream is = localFile.getInputStream();
	// IFSFileOutputStream ifsfos = new IFSFileOutputStream(ifsFile);
	//
	// byte[] buffer = new byte[4096];
	// int bytesRead;
	//
	// while ((bytesRead = is.read(buffer)) != -1) {
	// ifsfos.write(buffer, 0, bytesRead);
	// }
	//
	// is.close();
	// ifsfos.flush();
	// ifsfos.close();
	//
	// } catch (Exception e) {
	// return e.getLocalizedMessage();
	// }
	// return null;
	// }

	// public static String uploadFile(UploadedFile localFile,
	// String remoteFilePath) {
	//
	// try {
	// File remoteFile = new File(remoteFilePath);
	// if (!remoteFile.exists()) {
	// remoteFile.createNewFile();
	// }
	//
	// InputStream is = localFile.getInputStream();
	// FileOutputStream fos = new FileOutputStream(remoteFile);
	//
	// byte[] buffer = new byte[4096];
	// int bytesRead;
	//
	// while ((bytesRead = is.read(buffer)) != -1) {
	// fos.write(buffer, 0, bytesRead);
	// }
	//
	// is.close();
	// fos.flush();
	// fos.close();
	//
	// } catch (Exception e) {
	// return e.getLocalizedMessage();
	// }
	//
	// return null;
	// }

	public static String uploadFile(String fileToUpload, String remoteFilePath) {
		try {
			File localFile = new File(fileToUpload);
			File remoteFile = new File(remoteFilePath);
			if (!remoteFile.exists()) {
				remoteFile.createNewFile();
			}

			FileInputStream is = new FileInputStream(localFile);
			FileOutputStream fos = new FileOutputStream(remoteFile);

			byte[] buffer = new byte[4096];
			int bytesRead;

			while ((bytesRead = is.read(buffer)) != -1) {
				fos.write(buffer, 0, bytesRead);
			}

			is.close();
			fos.flush();
			fos.close();

		} catch (Exception e) {
			return e.getLocalizedMessage();
		}

		return null;
	}

	public static String copyIFSFileToFile(String jobNumber, String ifsFilePath, String filePath) {
		IFSFile ifsFile = new IFSFile(ApplicationHelper.getUserBean().getUserSessions().get(jobNumber).getAs400(),
				ifsFilePath);
		File file = new File(filePath);
		try {
			LOG.info("ifsFile length: " + ifsFile.length());
		} catch (IOException e1) {
			return e1.getLocalizedMessage();
		}

		IFSFileInputStream ifsfis;
		try {
			ifsfis = new IFSFileInputStream(ifsFile);
			FileOutputStream fos = new FileOutputStream(file);

			byte[] buffer = new byte[4096];
			int bytesRead;

			while ((bytesRead = ifsfis.read(buffer)) != -1) {
				fos.write(buffer, 0, bytesRead);
			}

			ifsfis.close();

			fos.flush();
			fos.close();
		} catch (AS400SecurityException e) {
			return e.getLocalizedMessage();
		} catch (IOException e) {
			return e.getLocalizedMessage();
		}

		return null;
	}

	public static String streamIFSFileToSOS(String jobNumber, String ifsFolderPath, String fileNameToOpen,
			HttpServletResponse response) {

		long startTime = System.currentTimeMillis();
		long lapTime = startTime;

		IFSFile ifsFile = new IFSFile(ApplicationHelper.getUserBean().getUserSessions().get(jobNumber).getAs400(),
				ifsFolderPath, fileNameToOpen);

		response.reset();
		try {
			response.setContentLength((int) ifsFile.length());
			response.setContentType(
					MimePropertiesBean.getInstance().getMimeType(FilenameUtils.getExtension(fileNameToOpen)));

			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileNameToOpen + "\"");
			response.setHeader("Content-Length", String.valueOf(ifsFile.length()));

			IFSFileInputStream ifsfis;
			ifsfis = new IFSFileInputStream(ifsFile);
			ServletOutputStream sos = response.getOutputStream();

			byte[] buffer = new byte[4096];
			int bytesRead;
			long totalBytesRead = 0;
			long fileSize = ifsFile.length();
			int step = 125;

			if (LOG.isInfoEnabled()) {
				// System.out.println("Download started: " + fileNameToOpen
				// + " size: " + fileSize);
				LOG.info("Download started: " + fileNameToOpen + " size: " + fileSize);
			}

			while ((bytesRead = ifsfis.read(buffer)) != -1) {
				totalBytesRead += bytesRead;
				sos.write(buffer, 0, bytesRead);

				if (step == 0 && LOG.isInfoEnabled()) {
					long currentTime = System.currentTimeMillis();
					// System.out.println("Bytes uploaded: " + totalBytesRead
					// + " of " + fileSize + " in "
					// + (currentTime - lapTime) + " milliseconds.");
					LOG.info("Bytes uploaded: " + totalBytesRead + " of " + fileSize + " in " + (currentTime - lapTime)
							+ " milliseconds.");
					lapTime = currentTime;
					step = 126;
				}
				step--;
			}

			long endTime = System.currentTimeMillis();
			if (LOG.isInfoEnabled()) {
				long timeUsed = endTime - startTime;
				LOG.info("Time used to download file: " + ifsFile.getName() + " => " + timeUsed / 1000 + " seconds.");
				LOG.info("Download ended: " + fileNameToOpen);
				// System.out.println("Download ended: " + fileNameToOpen);
			}

			ifsfis.close();
			sos.flush();
			sos.close();
		} catch (AS400SecurityException e) {
			return e.getLocalizedMessage();
		} catch (IOException e) {
			return e.getLocalizedMessage();
		}

		return null;
	}

	public static String createUploadFileName(String originalAttachmentFileName) {
		if (originalAttachmentFileName.length() <= maxAttachmentFileLength) {
			return originalAttachmentFileName;
		}

		String fileExtension = "." + FilenameUtils.getExtension(originalAttachmentFileName);
		int extensionLength = fileExtension.length();

		String filename = null;
		// has extension ".log"
		if (extensionLength > 1) {
			filename = originalAttachmentFileName.substring(0, 80 - extensionLength) + fileExtension;
		} else {
			filename = originalAttachmentFileName.substring(0, 80);
		}

		return filename;
	}
}
